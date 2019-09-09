package com.haohe.zskportal.common.domain;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 12:10
 * @description zsk常量
 */
public class ZskConstant {

    // user缓存前缀
    public static final String USER_CACHE_PREFIX = "zsk.cache.user.";
    // user角色缓存前缀
    public static final String USER_ROLE_CACHE_PREFIX = "zsk.cache.user.role.";
    // user权限缓存前缀
    public static final String USER_PERMISSION_CACHE_PREFIX = "zsk.cache.user.permission.";
    // user个性化配置前缀
    public static final String USER_CONFIG_CACHE_PREFIX = "zsk.cache.user.config.";
    // token缓存前缀
    public static final String TOKEN_CACHE_PREFIX = "zsk.cache.token.";

    // 存储在线用户的 zset前缀
    public static final String ACTIVE_USERS_ZSET_PREFIX = "zsk.user.active";

    // 按钮
    public static final String TYPE_BUTTON = "1";
    // 菜单
    public static final String TYPE_MENU = "0";

    // cas server地址
    public static final String casServerUrlPrefix = "http://192.168.1.169:9100/cas";
    // Cas登录页面地址
    public static final String casLoginUrl = casServerUrlPrefix + "/login";
    // Cas登出页面地址
    public static final String casLogoutUrl = casServerUrlPrefix + "/logout";
    // 当前工程对外提供的服务地址
    public static final String shiroServerUrlPrefix = "http://192.168.1.192:8084";
    // casFilter UrlPattern
    public static final String casFilterUrlPattern = "/cas";
    // 登录地址
    public static final String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
    // 登出地址
    public static final String logoutUrl = casLogoutUrl+"?service="+shiroServerUrlPrefix;
    // 登录成功地址
    public static final String loginSuccessUrl = "/hello";
    // 权限认证失败跳转地址
    public static final String unauthorizedUrl = "/error/403.html";

}
