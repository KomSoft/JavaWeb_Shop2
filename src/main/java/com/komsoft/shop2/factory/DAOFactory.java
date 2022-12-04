package com.komsoft.shop2.factory;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.repository.CategoryDAO;
import com.komsoft.shop2.repository.ProductDAO;
import com.komsoft.shop2.repository.UserDAO;

import java.sql.Connection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class DAOFactory {
    public static final int POSTGRESQL = 1;
    public static final int HIKARI_INTERNAL = 2;
    public static final int HIKARI_EXTERNAL = 3;
    public static final int HIKARI_SINGLETON = 4;
    public abstract CategoryDAO getCategoryDAO();
    public abstract ProductDAO getProductDAO();
    public abstract UserDAO getUserDAO();
    public abstract Connection getConnection() throws DataBaseException;
    public abstract void closeConnection();

    public static DAOFactory getInstance(int whichFactory) {
        try {
            switch (whichFactory) {
                case POSTGRESQL: return new PostgreSQLDAOFactory();
                case HIKARI_EXTERNAL: return new HikariExternalDAOFactory();
                case HIKARI_INTERNAL: return new HikariInternalDAOFactory();
                case HIKARI_SINGLETON: return HikariSingletonDAOFactory.getInstance();
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DAOFactory getInstance() {
        int whichFactory = -1;
        String datasource;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            datasource = bundle.getString( "datasource");
            if (datasource.equalsIgnoreCase("postgresql")) {
                whichFactory = DAOFactory.POSTGRESQL;
            }
            if (datasource.equalsIgnoreCase("hikari_internal")) {
                whichFactory = DAOFactory.HIKARI_INTERNAL;
            }
            if (datasource.equalsIgnoreCase("hikari_external")) {
                whichFactory = DAOFactory.HIKARI_EXTERNAL;
            }
            if (datasource.equalsIgnoreCase("hikari_singleton")) {
                whichFactory = DAOFactory.HIKARI_SINGLETON;
            }
        } catch (NullPointerException | MissingResourceException | ClassCastException e) {
            e.printStackTrace();
        }
        return getInstance(whichFactory);
    }
}
