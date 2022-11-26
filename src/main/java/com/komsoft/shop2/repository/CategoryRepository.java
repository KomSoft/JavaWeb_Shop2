package com.komsoft.shop2.repository;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static final String GET_ALL_CATEGORY = "SELECT * FROM category ORDER BY id ASC";
    private static final String GET_ALL_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?";
    private Connection connection;

    public CategoryRepository() {
    }

    public void getConnection() throws DataBaseException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = HikariCPDataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new DataBaseException("[CategoryRepository] Error getting new connection. " + e.getMessage());
        }
    }

    public void closeConnection() throws DataBaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new DataBaseException("[CategoryRepository] Error closing connection. " + e.getMessage());
        }
    }

    public List<Category> getAllCategory() throws DataBaseException {
        List<Category> result = new ArrayList<>();
        try {
            getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORY);
            ResultSet categories = statement.executeQuery();
            while (categories.next()) {
                result.add(new Category()
                        .setId(categories.getLong("id"))
                        .setName(categories.getString("name")));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            closeConnection();
        }
        return result;
    }

    public Category getCategory(String category) throws DataBaseException, ValidationException {
        Category result = null;
        try {
            if (category == null) {
                throw new ValidationException("Oooops! <br>Category is not defined");
            }
            getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORY_BY_ID);
            long categoryIndex = Long.parseLong(category);
            statement.setLong(1, categoryIndex);
            ResultSet categories = statement.executeQuery();
            if (categories.next()) {
                result = new Category()
                        .setId(categories.getLong("id"))
                        .setName(categories.getString("name"));
            } else {
                throw new DataBaseException(String.format("Oooops! <br>Category: %s not exists", category));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid category: %s, try another", category));
        } finally {
            closeConnection();
        }
        return result;
    }

}
