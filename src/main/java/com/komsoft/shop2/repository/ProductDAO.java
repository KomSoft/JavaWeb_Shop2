package com.komsoft.shop2.repository;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import java.util.List;

public interface ProductDAO {

    List<ProductDto> getAllProduct(String category) throws DataBaseException, ValidationException;
    ProductDto getProductById(String id) throws DataBaseException, ValidationException;

}
