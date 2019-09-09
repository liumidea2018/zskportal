package com.haohe.zskportal.common.authentication;

import com.haohe.zskportal.common.domain.ZskConstant;
import org.apache.shiro.cas.CasRealm;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * 安全数据源
 */
public class MyShiroCasRealm extends CasRealm{

    @PostConstruct
    public void initProperty(){
        setCasServerUrlPrefix(ZskConstant.casServerUrlPrefix);
        setCasService(ZskConstant.shiroServerUrlPrefix + ZskConstant.casFilterUrlPattern);
    }


}