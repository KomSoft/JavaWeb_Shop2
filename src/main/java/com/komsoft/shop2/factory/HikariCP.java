package com.komsoft.shop2.factory;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.util.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCP {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource hikariDataSources;

    public static Connection getConnection() throws DataBaseException {
System.out.println("HikariCP.getConnection() called");
        try {
            return hikariDataSources.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    private HikariCP() throws DataBaseException {
System.out.println("Private Constructor HikariCP() called");
        DBProperties prop = new DBProperties("hikaripostgres");
        if (prop.isReady()) {
            config.setJdbcUrl(prop.getUrl());
            config.setUsername(prop.getUserName());
            config.setPassword(prop.getPassword());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariDataSources = new HikariDataSource(config);
        } else {
            throw new DataBaseException("Can't read DB properties. DB isn't connected");
        }
    }

/*
//    or use
    private static HikariConfig config = new HikariConfig("datasourcepostgres.properties" );

    The properties file should look something like this:
    dataSourceClassName= //TBD
    dataSource.user= //TBD
    other properties name should start with dataSource as shown above
*/

}
