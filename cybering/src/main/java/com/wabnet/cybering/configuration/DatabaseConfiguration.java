package com.wabnet.cybering.configuration;


import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


import static java.util.Collections.singletonList;

@Configuration
public class DatabaseConfiguration extends AbstractMongoClientConfiguration {

    @Override
    public String getDatabaseName() {
        return "cybering";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {

        builder
                .credential(MongoCredential.createCredential("cyber", "cybering", "cyberpass".toCharArray()))
                .applyToClusterSettings(settings  -> {
                    settings.hosts(singletonList(new ServerAddress("127.0.0.1", 27017)));
                });
    }
}