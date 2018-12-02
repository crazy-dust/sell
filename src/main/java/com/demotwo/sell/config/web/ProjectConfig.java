package com.demotwo.sell.config.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 20:51
 * @Desc:
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private String sell;

}