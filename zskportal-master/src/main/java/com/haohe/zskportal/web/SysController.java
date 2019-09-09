package com.haohe.zskportal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haohe.zskportal.common.annotation.Limit;
import com.haohe.zskportal.common.authentication.JWTToken;
import com.haohe.zskportal.common.authentication.JWTUtil;
import com.haohe.zskportal.common.domain.ActiveUser;
import com.haohe.zskportal.common.domain.ZskConstant;
import com.haohe.zskportal.common.domain.ZskResponse;
import com.haohe.zskportal.common.exception.ZskException;
import com.haohe.zskportal.common.properties.ZskProperties;
import com.haohe.zskportal.common.service.RedisService;
import com.haohe.zskportal.sys.manager.UserManager;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.service.UserService;
import com.haohe.zskportal.sys.vo.UserVO;
import com.haohe.zskportal.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jms.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 微笑の掩饰
 * @date 2019/7/3 10:56
 * @description 登陆 注册
 */
@Slf4j
@Validated
@RestController
public class SysController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ZskProperties properties;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;


    @GetMapping("hello")
    public void GetMapping1(HttpSession session, ModelMap map, HttpServletRequest request){
        System.out.println(request.getUserPrincipal().getName());
        System.out.println(SecurityUtils.getSubject().getPrincipal());

        System.out.println("hello--------------------------------------------------------------------->>>");
    }

    //Limit 自定义接口限流
    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public ZskResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password, HttpServletRequest request) throws Exception {
        username = StringUtils.lowerCase(username);
        password = MD5Util.encrypt(username, password);

        final String errorMessage = "用户名或密码错误";
        User user = userManager.getUser(username);
        UserVO userVo = new UserVO(user);

        if (userVo == null) {
            throw new ZskException(errorMessage);
        }
        if (!StringUtils.equals(userVo.getPassword(), password))
            throw new ZskException(errorMessage);
        if (User.STATUS_LOCK.equals(userVo.getIsLocked()))
            throw new ZskException("账号已被锁定,请联系管理员！");

        String token = ZskUtil.encryptToken(JWTUtil.sign(username, password));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String activeId = saveTokenToRedis(userVo, jwtToken, request);
        userVo.setActiveId(activeId);

        // 更新用户登录时间
        userService.updateLoginTime(userVo);

        Map<String, Object> userInfo = generateUserInfo(jwtToken, userVo);
        return new ZskResponse().message("认证成功").data(userInfo);
    }

    //根据token检查用户是否曾经登陆过
    @GetMapping("/checkLogin")
    public ZskResponse checkLogin(@NotBlank(message = "{required}") @RequestParam(defaultValue = "") String token,HttpServletRequest request){

        HttpSession session = request.getSession();
        Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        if (assertion != null) {
            //获取登录用户名
            String username = assertion.getPrincipal().getName();
            System.out.println("user ---------> " + username);
//            User temp = userService.findByUsername(username);
//            System.out.println("TEMP user ---------> " + (temp.getUsername()));
//            if (temp != null) {
//                session.setAttribute(WebSecurityConfig.SESSION_LOGIN, temp);
                // 跳转到前端
//                response.sendRedirect("http://172.16.67.228:8000”);
//            }
        }


        String ip = IPUtil.getIpAddr(request);
        String encryptTokenInRedis = "";
        try {
            encryptTokenInRedis = redisService.get(ZskConstant.TOKEN_CACHE_PREFIX + token + "." + ip);
        } catch (Exception ignore) {
        }
        // 如果找不到，说明已经失效
        if (StringUtils.isBlank(encryptTokenInRedis))
            return new ZskResponse().message("token已经过期");
        //解密
        JWTToken jwtToken = new JWTToken(ZskUtil.decryptToken(token));
        String username = JWTUtil.getUsername(jwtToken.getToken());

        if (StringUtils.isBlank(username))
            return new ZskResponse().message("token无效");

        // 通过用户名查询用户信息
        User user = userManager.getUser(username);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token);
        user.setPassword("it's a secret");
        userInfo.put("user", user);

        return new ZskResponse().data(userInfo);
    }

    @GetMapping("/index/{username}")
    public ZskResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        User user = userManager.getUser(username);
        return new ZskResponse().data(user);
    }

    @RequiresPermissions("user:online")
    @GetMapping("/online")
    public ZskResponse userOnline(String username) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(ZskConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        List<ActiveUser> activeUsers = new ArrayList<>();
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            activeUser.setToken(null);
            if (StringUtils.isNotBlank(username)) {
                if (StringUtils.equalsIgnoreCase(username, activeUser.getUsername()))
                    activeUsers.add(activeUser);
            } else {
                activeUsers.add(activeUser);
            }
        }
        return new ZskResponse().data(activeUsers);
    }

    @DeleteMapping("/kickout/{id}")
    @RequiresPermissions("user:kickout")
    public void kickout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(ZskConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        ActiveUser kickoutUser = null;
        String kickoutUserString = "";
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            if (StringUtils.equals(activeUser.getId(), id)) {
                kickoutUser = activeUser;
                kickoutUserString = userOnlineString;
            }
        }
        if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
            // 删除 zset中的记录
            redisService.zrem(ZskConstant.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
            // 删除对应的 token缓存
            redisService.del(ZskConstant.TOKEN_CACHE_PREFIX + kickoutUser.getToken() + "." + kickoutUser.getIp());
        }
    }

    /**
     * 退出
     * @param id
     * @throws Exception
     */
    @GetMapping("/logout/{id}")
    public void logout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        kickout(id);
    }

    /**
     * 发送验证码
     * @param mobile
     * @throws Exception
     */
    @GetMapping("/verifyCode/{mobile}")
    public void verifyCode(
            @NotBlank(message = "{required}") @PathVariable String mobile) throws Exception {

        Destination destination = new ActiveMQQueue("sanji_sms");
        //验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("template_code", "SMS_162736532");
        map.put("sign_name", "将何数据");
        map.put("param", "{\"code\":\""+verifyCode+"\"}");

        jmsMessagingTemplate.convertAndSend(destination,map);

        redisService.del(mobile);
        //验证码存入redis，有效期60s
        long expire = 62*1000L;
        redisService.set(mobile,verifyCode,expire);
    }

    /**
     * 注册用户
     * @param username
     * @param password
     * @throws Exception
     */
    @PostMapping("/regist")
    public ZskResponse regist(
            @NotBlank(message = "{required}") String username,@NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String mobile,@NotBlank(message = "{required}") String verifyCode) throws Exception {
        ZskResponse resp = new ZskResponse();
        String code = redisService.get(mobile);
        if (StringUtils.isNotEmpty(verifyCode)){
            if (verifyCode.equals(code)){
                userService.regist(username, password);
                resp.put("status",0);
                resp.message("注册成功");
            }else {
                resp.put("status",1);
                resp.message("验证码错误，请重新获取验证码");
            }
        }else {
            resp.put("status",2);
            resp.message("验证码已失效，请重新获取");
        }

        return resp;
    }

    private String saveTokenToRedis(User user, JWTToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);

        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset 存储登录用户，score 为过期时间戳
        redisService.zadd(ZskConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        redisService.set(ZskConstant.TOKEN_CACHE_PREFIX + token.getToken() + StringPool.DOT + ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }

    /**
     * 生成前端需要的用户信息，包括：token
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, User user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());
        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }
}
