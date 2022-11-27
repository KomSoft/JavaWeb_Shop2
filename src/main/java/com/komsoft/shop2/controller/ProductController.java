package com.komsoft.shop2.controller;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.form.Header;
import com.komsoft.shop2.repository.ProductRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductController extends HttpServlet {
    ProductRepository productRepository;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String url;
        String category = request.getParameter("category");
        String productId = request.getParameter("product");
        List<ProductDto> products = new ArrayList<>();
        try {
            if (productId != null) {
                ProductDto productDto = productRepository.getProductById(productId);
                if (productDto == null) {
                    throw new DataBaseException(String.format("No product(s) found for id=%s", productId));
                }
                products.add(productDto);
                url = Header.PAGE_ROOT + "productinfo.jsp";
            } else {
                products = productRepository.getAllProduct(category);
                if (products.size() == 0) {
                    throw new DataBaseException(String.format("No product(s) found for category=%s", category));
                }
                url = Header.PAGE_ROOT + "products.jsp";
            }
            request.setAttribute("products", products);
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
