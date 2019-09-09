package com.haohe.zskportal.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 11:37
 * @description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zsk")
public class ZskProperties {
    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;
}
