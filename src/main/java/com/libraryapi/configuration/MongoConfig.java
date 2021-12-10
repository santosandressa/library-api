package com.libraryapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig  extends AbstractMongoClientConfiguration {

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    @Override
    public String getDatabaseName() {
        return "book";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.libraryapi");
    }

}
