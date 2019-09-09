package com.haohe.zskportal.common.authentication;

import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.crazycake.shiro.RedisCacheManager;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 12:32
 * @description Shiro 配置类
 */
//@Configuration
public class ShiroConfig {


    // cas server地址
    @Value("${cas.casServerUrlPrefix}")
    private String casServerUrlPrefix;

    // Cas登录页面地址
    @Value("${cas.casLoginUrl}")
    private String casLoginUrl;

    // Cas登出页面地址
    @Value("${cas.casLogoutUrl}")
    private String casLogoutUrl;

    // 当前工程对外提供的服务地址
    @Value("${cas.shiroServerUrlPrefix}")
    private String shiroServerUrlPrefix;

    // casFilter UrlPattern
    @Value("${cas.casFilterUrlPattern}")
    private String casFilterUrlPattern;

    // 登录地址
    @Value("${cas.loginUrl}")
    private String loginUrl;

    // 登出地址
    @Value("${cas.loginSuccessUrl}")
    private String loginSuccessUrl;

    // 权限认证失败跳转地址
    @Value("${cas.unauthorizedUrl}")
    private String unauthorizedUrl;



//    @Value("${cas.casServerLoginUrl}")
    private String casServerLoginUrl;

//    @Value("${cas.casServerLogoutUrl}")
    private String casServerLogoutUrl;

//    @Value("${cas.localServerUrlPrefix}")
    private String localServerUrlPrefix;


//    @Value("${cas.localServerLoginUrl}")
    private String localServerLoginUrl;  //本地服务器登录地址

//    @Value("${spring.redis.host}")
    private String host;

//    @Value("${spring.redis.port}")
    private int port;

//    @Value("${spring.redis.password}")
    private String password;

//    @Value("${spring.redis.timeout}")
    private int timeout;

//    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

//    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

//    @Value("${spring.redis.database}")
    private int database;

    private static final String CAS_FILTER = "casFilter";


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, CasFilter casFilter){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl(localServerLoginUrl);
        factoryBean.setSuccessUrl("/user");
        factoryBean.setUnauthorizedUrl("/403");
        // 添加casFilter到shiroFilter中
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put(CAS_FILTER, casFilter);
        factoryBean.setFilters(filterMap);

        loadShiroFilterChain(factoryBean);
        return factoryBean;
    }

    /**
     * 加载ShiroFilter权限控制规则
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
        /**下面这些规则配置最好配置到配置文件中*/
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        filterChainMap.put("/", CAS_FILTER);//shiro集成cas后，首先添加该规则

        filterChainMap.put("/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
    }
    @Bean(name = "shiroCasRealm")
    public MyShiroCasRealm shiroCasRealm(){
        MyShiroCasRealm realm = new MyShiroCasRealm();
        return realm;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(MyShiroCasRealm shiroCasRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroCasRealm);
        // 指定SubjectFactory
        securityManager.setSubjectFactory(new CasSubjectFactory());
        securityManager.setSessionManager(this.sessionManager());
        securityManager.setCacheManager(this.redisCacheManager());
        return securityManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(3600);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }



    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * CAS过滤器
     */
    @Bean(name = "casFilter")
    public CasFilter casFilter(){
        CasFilter casFilter = new CasFilter();
        casFilter.setName(CAS_FILTER);
        casFilter.setEnabled(true);
        casFilter.setFailureUrl(localServerLoginUrl);
        return casFilter;
    }

}
