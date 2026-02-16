package com.nscott.countrypathdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.JacksonRepositoryPopulatorFactoryBean;

@Configuration
public class CountryPathConfig {

//    @Bean
//    public JacksonRepositoryPopulatorFactoryBean getRespositoryPopulator() {
//        JacksonRepositoryPopulatorFactoryBean factory = new JacksonRepositoryPopulatorFactoryBean();
//        factory.setResources(new Resource[]{new ClassPathResource("countries.json")});
//        return factory;
//    }
}
