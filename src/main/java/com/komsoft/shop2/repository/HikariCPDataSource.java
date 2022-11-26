package com.komsoft.shop2.repository;

import com.komsoft.shop2.exception.DataBaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource hikariDataSources;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/testDB");
        config.setUsername("postgres");
        config.setPassword("psql");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariDataSources = new HikariDataSource(config);
    }

    public static Connection getConnection() throws DataBaseException {
        try {
            return hikariDataSources.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    private HikariCPDataSource(){}

}
