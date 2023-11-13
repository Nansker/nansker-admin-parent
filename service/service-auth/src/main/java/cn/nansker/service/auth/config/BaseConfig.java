package cn.nansker.service.auth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nansker
 * @date 2023/9/19 0:33
 * @description 基本配置类
 */
@ComponentScan("cn.nansker.service")
@ComponentScan("cn.nansker.security")
@Configuration
public class BaseConfig {
}
