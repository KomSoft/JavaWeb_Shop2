package com.komsoft.shop2.controller;

import com.komsoft.shop2.util.Header;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AuthorizedPage", value = "/AuthorizedPage")
public class AuthorizedPage extends HttpServlet {

    private static final long serialVersionUID = 2402849562381027438L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String url = Header.AUTHORIZED_PAGE_ROOT + Header.SECRET_PAGE;
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

