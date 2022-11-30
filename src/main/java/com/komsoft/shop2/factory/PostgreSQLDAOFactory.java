package com.komsoft.shop2.factory;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.repository.*;
import com.komsoft.shop2.util.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLDAOFactory extends DAOFactory {
    private final String url;
    final String user;
    final String password;
    final String driverName;
    private Connection connection = null;

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOPsqlRepositoryImpl(this);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new ProductDAOPsqlRepositoryImpl(this);
    }

    public PostgreSQLDAOFactory() throws DataBaseException {
        DBProperties prop = new DBProperties("postgresql");
        if (prop.isReady()) {
            driverName = prop.getDriverName();
            url = prop.getUrl();
            user = prop.getUserName();
            password = prop.getPassword();
        } else {
            url = null;
            driverName = null;
            user = null;
            password = null;
            throw new DataBaseException("Can't read DB properties. DB isn't connected");
        }
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOPsqlRepositoryImpl(this);
    }

    public Connection getConnection() throws DataBaseException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driverName);
                this.connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new DataBaseException(String.format("Error connecting to DataBase (%s). No DB connected", e.getMessage()));
        }
        return this.connection;
    }

    public void closeConnection() {
        try {
           if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
