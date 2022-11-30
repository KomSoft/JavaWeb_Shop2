package com.komsoft.shop2.controller;

import com.komsoft.shop2.dto.ProductDto;
import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.factory.DAOFactory;
import com.komsoft.shop2.util.Header;
import com.komsoft.shop2.model.Product;
import com.komsoft.shop2.repository.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartController extends HttpServlet {

    DAOFactory daoFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String url;
        Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) request.getSession().getAttribute(Header.USER_CART);
        if (cart == null || cart.size() == 0) {
            url = Header.PAGE_ROOT + Header.INFO_PAGE;
            request.getSession().setAttribute(Header.MESSAGE, "Your Cart is empty. First put items in.");
        } else {
            url = Header.PAGE_ROOT + Header.CART_PAGE;
        }
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String url;
        if (request.getSession().getAttribute(Header.AUTHENTICATED_USER_KEY) == null) {
//           user not logged in - redirect to login page
            url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
            dispatcher = request.getRequestDispatcher(url);
            request.setAttribute(Header.LOGIN_MESSAGE, "To put items into Cart please login first");
            dispatcher.forward(request, response);
            return;
        }
        String idString = request.getParameter("id");
        String countString = request.getParameter("count");
        boolean isDelete = request.getParameter("delete") != null;
        try {
            int count = countString == null ? 0 : Integer.parseInt(countString);
            HttpSession session = request.getSession();
            if (session.getAttribute(Header.USER_CART) == null) {
                session.setAttribute(Header.USER_CART, new HashMap<Product, Integer>());
            }
            Map<ProductDto, Integer> cart = (Map<ProductDto, Integer>) session.getAttribute(Header.USER_CART);
            ProductDAO productDAO = daoFactory.getProductDAO();
            ProductDto product = productDAO.getProductById(idString);
            if (product != null) {
                if (isDelete) {
                    cart.remove(product);
                } else {
                    int quantity = cart.get(product) == null ? 0 : cart.get(product);
                    quantity += count;
                    cart.put(product, quantity);
                }
            }
            session.setAttribute(Header.USER_CART, cart);
            response.sendRedirect(request.getHeader("Referer"));
        } catch (NumberFormatException ignored) {
//            because  <input type="number"
        } catch (ValidationException | DataBaseException e) {
            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
            request.getSession().setAttribute(Header.MESSAGE, e.getMessage());
            dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

}
