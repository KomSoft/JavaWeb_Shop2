package com.komsoft.shop2.factory;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.repository.*;
import com.komsoft.shop2.util.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPPostgreSQLDAOFactory extends DAOFactory {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource hikariDataSources;
    private static HikariCPPostgreSQLDAOFactory instance;

    public Connection getConnection() throws DataBaseException {
        return getConnect();
    }

    public static Connection getConnect() throws DataBaseException {
        try {
            return hikariDataSources.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
    }

    private HikariCPPostgreSQLDAOFactory() throws DataBaseException {
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

    public static HikariCPPostgreSQLDAOFactory getInstance() {
        if (instance == null) {
            try {
                instance = new HikariCPPostgreSQLDAOFactory();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOPsqlRepositoryImpl(this);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAOPsqlRepositoryImpl(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOPsqlRepositoryImpl(this);
    }
}
