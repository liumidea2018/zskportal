package com.haohe.zskportal.common.aspect;

import com.haohe.zskportal.common.authentication.JWTUtil;
import com.haohe.zskportal.common.properties.ZskProperties;
import com.haohe.zskportal.utils.HttpContextUtil;
import com.haohe.zskportal.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 11:36
 * @description AOP 记录用户操作日志
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private ZskProperties zskProperties;

    @Pointcut("@annotation(com.haohe.zskportal.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (zskProperties.isOpenAopLog()) {
            // 保存日志
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String username = "";
            if (StringUtils.isNotBlank(token)) {
                username = JWTUtil.getUsername(token);
            }
        }
        return result;
    }
}
