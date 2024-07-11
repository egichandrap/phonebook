package com.example.phonebook.infrastructure.configuration;

import com.example.phonebook.infrastructure.util.Util;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;

import javax.sql.DataSource;

public class ConnectionDb {
    @Setter
    private static ConnectionDb instance;
    private DataSource dataSourceMySql;
    private ConnectionDb(){
        Util.isEmptyOrNull(dataSourceMySql);
        Util.debugLogger.debug("create datasource");
        HikariConfig hikariConfigPes = new HikariConfig();
        hikariConfigPes.setJdbcUrl(ConstantConfig.getInstance().getMySqlUrl());
        hikariConfigPes.setUsername(ConstantConfig.getInstance().getMySqlUserName());
        hikariConfigPes.setPassword(ConstantConfig.getInstance().getMySqlPass());
        hikariConfigPes.setConnectionTestQuery("SELECT 1");
        hikariConfigPes.setMaximumPoolSize(ConstantConfig.getInstance().getMySqlMaxPool());
        hikariConfigPes.setMinimumIdle(ConstantConfig.getInstance().getMySqlMinimumIdle());
        hikariConfigPes.setMaxLifetime(ConstantConfig.getInstance().getMySqlMaxLifeTime());
        hikariConfigPes.setIdleTimeout(ConstantConfig.getInstance().getMySqlIdleTimeout());
        hikariConfigPes.setAllowPoolSuspension(true);
        hikariConfigPes.setDriverClassName(ConstantConfig.getInstance().getMySqlDriver());
        hikariConfigPes.addDataSourceProperty("autoReconnect", "true");
        hikariConfigPes.addDataSourceProperty("cachePrepStmts", ConstantConfig.getInstance().getMySqlCachePrepStmt());
        hikariConfigPes.addDataSourceProperty("prepStmtCacheSize", ConstantConfig.getInstance().getMySqlPrepStmtCacheSize());
        hikariConfigPes.addDataSourceProperty("prepStmtCacheSqlLimit", ConstantConfig.getInstance().getMySqlPrepStmtCacheSqlLimit());
        dataSourceMySql =new  HikariDataSource(hikariConfigPes);
    }

    public DataSource getDataSourceMySql() {
        return dataSourceMySql;
    }

    public static synchronized ConnectionDb getInstance(){
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }



}
