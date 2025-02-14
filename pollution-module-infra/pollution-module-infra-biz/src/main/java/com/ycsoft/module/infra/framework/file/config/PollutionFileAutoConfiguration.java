package com.ycsoft.module.infra.framework.file.config;

import com.ycsoft.module.infra.framework.file.core.client.FileClientFactory;
import com.ycsoft.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 颐川科技
 */
@Configuration(proxyBeanMethods = false)
public class PollutionFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
