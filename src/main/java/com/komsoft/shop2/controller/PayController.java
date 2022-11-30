package com.komsoft.shop2.controller;

import com.komsoft.shop2.util.Header;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String url;
        url = Header.PAGE_ROOT + Header.PAY_PAGE;
        request.getSession().setAttribute(Header.PAY_MESSAGE, "You can pay your items on this page");
        dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

}
