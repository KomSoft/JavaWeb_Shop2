package com.komsoft.shop2.controller;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.factory.DAOFactory;
import com.komsoft.shop2.util.Header;
import com.komsoft.shop2.model.Category;
import com.komsoft.shop2.repository.CategoryDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryController extends HttpServlet {
    DAOFactory daoFactory;

    Logger logger = Logger.getLogger(CategoryController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = new ArrayList<>();
        try {
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            categories = categoryDAO.getAllCategory();
        } catch (DataBaseException e) {
//      TODO - поки так
            logger.log(Level.INFO, String.format("[CategoryController] %s", e.getMessage()));
        } finally {
            if (categories.size() == 0) {
                categories.add(new Category().setId(0L).setName("DB Error"));
            }
        }
        request.getSession().setAttribute(Header.CATEGORIES, categories);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
