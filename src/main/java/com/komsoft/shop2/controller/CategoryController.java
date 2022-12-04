package com.komsoft.shop2.controller;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.factory.DAOFactory;
import com.komsoft.shop2.util.Header;
import com.komsoft.shop2.model.Category;
import com.komsoft.shop2.repository.CategoryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class CategoryController extends HttpServlet {
    DAOFactory daoFactory;

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = null;
        try {
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            categories = categoryDAO.getAllCategory();
        } catch (DataBaseException e) {
//      TODO - поки так
            logger.error("[CategoryController] {}", e.getMessage());
        }
        request.getSession().setAttribute(Header.CATEGORIES, categories);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
