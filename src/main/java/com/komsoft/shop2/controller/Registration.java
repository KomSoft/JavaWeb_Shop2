package com.komsoft.shop2.controller;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.exception.ValidationException;
import com.komsoft.shop2.factory.DAOFactory;
import com.komsoft.shop2.util.Header;
import com.komsoft.shop2.model.AuthorizedUser;
import com.komsoft.shop2.repository.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Registration extends HttpServlet {
    //    @Serial
    private static final long serialVersionUID = -1210613704116541742L;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
    RequestDispatcher dispatcher = null;
    DAOFactory daoFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthorizedUser checkedUser = new AuthorizedUser(request);
        String url;
        if (checkedUser.isCorrect()) {
//            write to DB, check errors and get result
            try {
                UserDAO userDAO = daoFactory.getUserDAO();
                userDAO.saveUser(checkedUser.getUserRegisteringData());
                url = Header.PAGE_ROOT + Header.INFO_PAGE;
                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("registerCompleted"), checkedUser.getUserRegisteringData().getFullName()));
//                can redirect to \login there but then we won't see a result
//          Register completed
            } catch (DataBaseException e) {
                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
            } catch (ValidationException e) {
                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("userIncorrectData"), e.getMessage()));
            }
        } else {
            url = Header.PAGE_ROOT + Header.REGISTRATION_PAGE;
            request.getSession().setAttribute("errors", checkedUser.getErrors());
        }
        dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String url;
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName
            url = Header.PAGE_ROOT + Header.LOGIN_WELCOME_PAGE;
        } else {
            url = Header.PAGE_ROOT + Header.REGISTRATION_PAGE;
        }
        dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.getWriter().write(e.getMessage());
        }
    }

}
