package com.komsoft.shop2.repository;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.model.AuthorizedUser;
import com.komsoft.shop2.model.UserRegisteringData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final String GET_FULLNAME_BY_LOGIN_PASSWORD = "SELECT full_name FROM users WHERE login = ? AND password = ?";
    private final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final String INSERT_USER = "INSERT INTO users (login, password, full_name, region, gender, comment) VALUES (?, ?, ?, ?, ?, ?)";
    private Connection connection;

    public UserRepository() {
    }

    public void getConnection() throws DataBaseException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = HikariCPDataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new DataBaseException("[ProductRepository] Error getting new connection. " + e.getMessage());
        }
    }

    public void closeConnection() throws DataBaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] Error closing connection. " + e.getMessage());
        }
    }

    public String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException {
        String fullName = null;
        if (login == null || password == null || login.trim().length() == 0 || password.trim().length() == 0) {
            throw new DataBaseException("[UserRepository] Login or password is null or blank");
        }
        try {
            getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fullName = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();
            return fullName;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public String getFullNameByLogin(String login) throws DataBaseException {
        UserRegisteringData user = getUserByLogin(login);
        return user == null ? null : user.getFullName();
    }

    public UserRegisteringData getUserByLogin(String login) throws DataBaseException {
        UserRegisteringData user = null;
        if (login == null || login.trim().length() == 0) {
            throw new DataBaseException("[UserRepository] Login is null or empty");
        }
        try {
            getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
//                String login = resultSet.getString("login");
                String fullName = resultSet.getString("full_name");
                String region = resultSet.getString("region");
                String gender = resultSet.getString("gender");
                String comment = resultSet.getString("comment");
                user = new UserRegisteringData(login, fullName, region, gender, comment);
                String savedPassword = resultSet.getString("password");
                user.setSavedPassword(savedPassword);
            }
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException, ValidationException {
        if (AuthorizedUser.isUserRegisteringDataCorrect(user)) {
//        check userRegisteringData
            try {
                String fullName = this.getFullNameByLogin(user.getLogin());
                if (fullName != null) {
                    throw new ValidationException(String.format("[UserRepository] User %s already exists", user.getLogin()));
                }
                getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getEncryptedPassword());
                statement.setString(3, user.getFullName());
                statement.setString(4, user.getRegion());
                statement.setString(5, user.getGender());
                statement.setString(6, user.getComment());
                int id = statement.executeUpdate();
                statement.close();
                if (id == 1) {
                    return user;    // TODO we should return new user from DataBase
                } else {
                    throw new DataBaseException("[UserRepository] Unknown reason. User not saved");
                }
            } catch (SQLException e) {
                throw new DataBaseException("[UserRepository] Can't save user. SQLException " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return null;
    }

}
