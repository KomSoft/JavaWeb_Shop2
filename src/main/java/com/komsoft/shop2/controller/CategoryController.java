package com.komsoft.shop2.controller;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.form.Header;
import com.komsoft.shop2.model.Category;
import com.komsoft.shop2.repository.CategoryRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryController extends HttpServlet {
    CategoryRepository categoryRepository;

    Logger logger = Logger.getLogger(CategoryController.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryRepository.getAllCategory();
        } catch (DataBaseException e) {
//      TODO - поки так
            logger.log(Level.INFO, String.format("[CategoryController] %s", e.getMessage()));
        } finally {
            if (categories.size() == 0) {
                categories.add(new Category().setId(0L).setName("error"));
            }
        }
        request.setAttribute(Header.CATEGORIES, categories);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        super.init();
        categoryRepository = new CategoryRepository();
    }
}
