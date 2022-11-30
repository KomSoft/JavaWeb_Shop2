package com.komsoft.shop2.util;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.model.Product;

public class ProductConverter {

    private boolean validateProduct(Product product) {
        return !(product == null || product.getCategory() == null || product.getId() <= 0
                || product.getName() == null || product.getDescription() == null
                || product.getPrice() <= 0 || product.getCategory().getId() <= 0
                || product.getCategory().getName() == null);
    }

    public ProductDto convertProductToDto(Product product) {
        ProductDto productDto = null;
        if (validateProduct(product)) {
            productDto = new ProductDto().setId(product.getId())
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setPrice(product.getPrice())
                    .setCategory(product.getCategory())
                    .setImageName();
        }
        return productDto;
    }
}
