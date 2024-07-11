package com.example.phonebook.infrastructure.configuration;

import com.example.phonebook.infrastructure.util.Util;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

@Setter
@Configuration
@PropertySource("classpath:application.properties")
public class ConstantConfig {
    private static ConstantConfig instance = null;

    @Autowired
    private Environment env;

    private DataSource mySqlDataSource;
    private String mySqlUrl;
    private String mySqlUserName;
    private String mySqlPass;
    private String mySqlDriver;
    private String mySqlMaxLifeTime;
    private String mySqlIdleTimeout;
    private String mySqlMaxPool;
    private String mySqlMinimumIdle;
    private String mySqlCachePrepStmt;
    private String mySqlPrepStmtCacheSize;
    private String mySqlPrepStmtCacheSqlLimit;

    @Bean
    public static ConstantConfig getInstance() {
        if (Util.isEmptyOrNull(instance)) {
            instance = new ConstantConfig();
        }

        return instance;
    }

    public String getMySqlUrl() {
        return env.getProperty("spring.datasource.db.url");
    }

    public String getMySqlUserName() {
        return env.getProperty("spring.datasource.db.username");
    }

    public String getMySqlPass() {
        return env.getProperty("spring.datasource.db.password");
    }

    public Integer getMySqlMaxPool() {return Integer.valueOf(Objects.requireNonNull(env.getProperty("spring.datasource.db.hikari.maximum-pool-size")));}

    public Integer getMySqlIdleTimeout() {return Integer.valueOf(Objects.requireNonNull(env.getProperty("spring.datasource.db.hikari.idleTimeout")));}

    public String getMySqlDriver() {
        return env.getProperty("spring.datasource.db.driver-class-name");
    }

    public String getMySqlCachePrepStmt() {return env.getProperty("spring.datasource.db.hikari.data-source-properties.cachePrepStmts");}

    public String getMySqlPrepStmtCacheSize() {return env.getProperty("spring.datasource.db.hikari.data-source-properties.prepStmtCacheSize");}

    public String getMySqlPrepStmtCacheSqlLimit() {return env.getProperty("spring.datasource.db.hikari.data-source-properties.prepStmtCacheSqlLimit");}

    public Integer getMySqlMinimumIdle() {return Integer.valueOf(Objects.requireNonNull(env.getProperty("spring.datasource.db.hikari.minimumIdle")));}

    public Integer getMySqlMaxLifeTime() {return Integer.valueOf(Objects.requireNonNull(env.getProperty("spring.datasource.db.hikari.maxLifetime")));}
}
