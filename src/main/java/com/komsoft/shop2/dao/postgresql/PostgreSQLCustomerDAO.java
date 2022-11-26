package com.komsoft.shop2.dao.postgresql;

import com.komsoft.shop2.dao.model.Customer;
import com.komsoft.shop2.dao.repository.CustomerDAO;
import com.komsoft.shop2.exception.DataBaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgreSQLCustomerDAO implements CustomerDAO {
    private final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE id = ?";
    private PostgreSQLDAOFactory postgreSQLDAOFactory;

    public PostgreSQLCustomerDAO(PostgreSQLDAOFactory postgreSQLDAOFactory) {
        this.postgreSQLDAOFactory = postgreSQLDAOFactory;
    }

    @Override
    public Customer findCustomerById(int id) {
        Customer user = null;
        try {
            try {
                postgreSQLDAOFactory.establishConnection();
            } catch (DataBaseException e) {
                throw new RuntimeException(e);
            }
            Connection connection = postgreSQLDAOFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new Customer();
                String fullName = resultSet.getString("full_name");
                String region = resultSet.getString("region");
                user.setName(fullName);
                user.setRegion(region);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
//            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        } finally {
            try {
                postgreSQLDAOFactory.closeConnection();
            } catch (DataBaseException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

}
