package org.psc.workerws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Configuration
public class WorkerWsConfiguration {

    @Resource(name = "customProperties")
    private Map<String, String> customProperties;

    @Bean(name = "customProperties")
    public static PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("custom.properties"));
        return bean;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady(){
        customProperties.forEach((k,v) -> log.info("{} = {}", k, v));
    }

}
