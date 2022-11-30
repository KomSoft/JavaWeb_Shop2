package com.komsoft.shop2.repository;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.factory.DAOFactory;
import com.komsoft.shop2.model.Category;
import com.komsoft.shop2.model.Product;
import com.komsoft.shop2.util.ProductConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsqlRepositoryImpl implements ProductDAO {
    private static final String GET_ALL_PRODUCT = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id";
    private static final String GET_ALL_PRODUCT_BY_CATEGORY = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id WHERE category.id=?";
    private static final String GET_PRODUCT_BY_ID = "SELECT product.id AS p_id, product.name AS p_name, product.description, product.price, category.id AS c_id, category.name AS c_name FROM product JOIN category ON product.category_id=category.id WHERE product.id=?";
    private DAOFactory daoFactory;

    public ProductDAOPsqlRepositoryImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<ProductDto> getAllProduct(String category) throws DataBaseException, ValidationException {
//    public List<Product> getAllProduct(String category) throws DataBaseException, ValidationException {
        List<ProductDto> result;
        String request;
        try {
            request = category == null || category.isEmpty() ? GET_ALL_PRODUCT : GET_ALL_PRODUCT_BY_CATEGORY;
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(request);
            if (request.equalsIgnoreCase(GET_ALL_PRODUCT_BY_CATEGORY)) {
                int categoryIndex = Integer.parseInt(category);
                statement.setInt(1, categoryIndex);
            }
            ResultSet products = statement.executeQuery();
            result = new ArrayList<>();
            while (products.next()) {
                result.add(new ProductConverter().convertProductToDto(new Product()
                        .setId(products.getLong("p_id"))
                        .setName(products.getString("p_name"))
                        .setDescription(products.getString("description"))
                        .setPrice(products.getDouble("price"))
                        .setCategory(new Category().setId(products.getLong("c_id"))
                                                .setName(products.getString("c_name")))));
            }
            products.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid category: %s, try another", category));
        } finally {
            daoFactory.closeConnection();
        }
        return result;
    }

    public ProductDto getProductById(String id) throws DataBaseException, ValidationException {
        ProductDto productDto = null;
        String request;
        if (id == null) {
            throw new ValidationException("Oooops! <br>Product id no present");
        }
        try {
            long productId = Long.parseLong(id);
            Connection connection = daoFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_ID);
            statement.setLong(1, productId);
            ResultSet products = statement.executeQuery();
            if (products.next()) {
                productDto = new ProductConverter().convertProductToDto(new Product()
                        .setId(products.getLong("p_id"))
                        .setName(products.getString("p_name"))
                        .setDescription(products.getString("description"))
                        .setPrice(products.getDouble("price"))
                        .setCategory(new Category().setId(products.getLong("c_id"))
                                                .setName(products.getString("c_name"))));
            }
            products.close();
            statement.close();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Oooops! <br>Invalid product id: %s, try another", id));
        } finally {
            daoFactory.closeConnection();
        }
        return productDto;
    }

}
