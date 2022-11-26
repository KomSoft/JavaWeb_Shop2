package com.komsoft.shop2.dto;

import com.komsoft.shop2.model.Category;

public class ProductDto {
    private static final String IMAGE_NAME_FORMAT = "img-c%03d-%03d.jpg";
    private long id;
    private String name;
    private String description;
    private double price;
    private Category category;
    private String imageName;

    public static String getImageName(long categoryId, long id) {
        if (id > 0 && categoryId > 0) {
            return String.format(IMAGE_NAME_FORMAT, categoryId, id);
        } else {
            return "";
        }
    }

    public ProductDto setId(long id) {
        this.id = id;
        return this;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public ProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductDto setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductDto setCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductDto setImageName() {
        this.imageName = "";
        if (this.category != null) {
           this.imageName = getImageName(this.category.getId(), this.id);
        }
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

     public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }

}
