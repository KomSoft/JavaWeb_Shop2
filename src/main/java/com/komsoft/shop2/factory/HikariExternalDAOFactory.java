package com.komsoft.shop2.factory;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.repository.*;

import java.sql.Connection;

//  Not work
//  because getConnection throws exception - HikariCP isn't initialize
//  Cannot invoke "com.zaxxer.hikari.HikariDataSource.getConnection()"
//          because "com.komsoft.shop2.factory.HikariCP.hikariDataSources" is null
public class HikariExternalDAOFactory extends DAOFactory {

    public HikariExternalDAOFactory() {
System.out.println("Constructor HikariExternalDAOFactory() called");
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOPsqlRepositoryImpl(this);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOPsqlRepositoryImpl(this);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAOPsqlRepositoryImpl(this);
    }

    public Connection getConnection() throws DataBaseException {
        return HikariCP.getConnection();
    }

    public void closeConnection() {
    }

}
