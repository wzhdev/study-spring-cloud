package com.sf.demo.book.store.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Data
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ApolloStaticValue {

    //apollo的namespace
    @Value("${sentinel-apollo.namespace}")
    private String namespace;
    //apollo的动态配置的key
    private String apolloRuleKey;
    public String getApolloRuleKey() {
        return appName+"-flow-rules";
    }

    @Value("${spring.application.name}")
    private String appName;










}
