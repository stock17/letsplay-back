package com.yurima.letsplay.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String DB_NAME;

    @Value("${spring.data.mongodb.uri}")
    private String URI;


    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(URI);
    }

}
