package com.sf.mip.spring.cloud.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig({"application", "datasource", "BASE.spring-boot-actuator", "BASE.spring-boot-common", "BASE.spring-data-jpa", "BASE.spring-cloud-consul"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
