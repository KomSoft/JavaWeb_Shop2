package com.komsoft.shop2.controller;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.form.Header;
import com.komsoft.shop2.model.Product;
import com.komsoft.shop2.repository.PostgreSQLJDBC;
import com.komsoft.shop2.repository.ProductRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class ProductController extends HttpServlet {
    ProductRepository productRepository;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String url;
        String category = request.getParameter("category");
        String productId = request.getParameter("product");
        try {
            if (productId != null) {
                ProductDto productDto = productRepository.getProductById(productId);
                request.setAttribute("productInfo", productDto);
                url = Header.PAGE_ROOT + "productinfo.jsp";
            } else {
                List<ProductDto> products = productRepository.getAllProduct(category);
                request.setAttribute("products", products);
                url = Header.PAGE_ROOT + "products.jsp";
            }
        } catch (DataBaseException | ValidationException e) {
            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
            request.getSession().setAttribute(Header.MESSAGE, e.getMessage());
        }
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        super.init();
        productRepository = new ProductRepository();
    }

}
