package com.libraryapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public String getDatabaseName() {
        return "book";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.libraryapi");
    }
}
