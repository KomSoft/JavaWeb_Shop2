package com.komsoft.shop2.controller;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.form.Header;
import com.komsoft.shop2.model.Product;
import com.komsoft.shop2.repository.ProductRepository;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartController extends HttpServlet {

    ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        productRepository = new ProductRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String countString = request.getParameter("count");
//        int count = 0;
        try {
/*
            if (countString != null) {
                count = Integer.parseInt(countString);
            }
*/
            int count = countString == null ? 0 : Integer.parseInt(countString);
            HttpSession session = request.getSession();
            if (session.getAttribute(Header.USER_CART) == null) {
                session.setAttribute(Header.USER_CART, new HashMap<Product, Integer>());
            }
            Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
            ProductDto product = productRepository.getProductById(idString);
            int quantity = cart.get(product) == null ? 0 : cart.get(product);
            quantity += count;
            cart.put(product, quantity);
            session.setAttribute(Header.USER_CART, cart);
    System.out.println("Cart has: " + cart);
            response.sendRedirect(request.getHeader("Referer"));

        } catch (NumberFormatException ignored) {

        } catch (ValidationException | DataBaseException e) {
            throw new RuntimeException(e);
        }
    }

}
