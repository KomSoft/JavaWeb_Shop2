package com.komsoft.shop2.repository;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.model.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> getAllCategory() throws DataBaseException;
    Category getCategory(String category) throws DataBaseException, ValidationException;

}
