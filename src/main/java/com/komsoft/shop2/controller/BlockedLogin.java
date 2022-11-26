package com.komsoft.shop2.controller;

import com.komsoft.shop2.exception.DataBaseException;
import com.komsoft.shop2.form.Header;
import com.komsoft.shop2.model.UserRegisteringData;
import com.komsoft.shop2.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class BlockedLogin extends HttpServlet {
    //    @Serial
    private static final long serialVersionUID = -6198900590472649299L;
    final int TIME_OUT = 10;
    final int MAX_TRY = 3;
    private int count = 0;
    private LocalDateTime endTime = null;
    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.UK);
    RequestDispatcher dispatcher = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
        String url = "";
        if (endTime != null) {
            long waitSecond = TIME_OUT - Duration.between(endTime, LocalDateTime.now()).getSeconds();
            if (waitSecond <= 0) {
                count = -1;
                endTime = null;
            } else {
                url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                request.setAttribute(Header.MESSAGE, String.format(bundle.getString("waitForTimeout"), waitSecond));
            }
        }
        if (count < MAX_TRY) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login != null && login.trim().length() != 0) {      // isBlank() is not supported
                try {
                    UserRepository userRepository = new UserRepository();
                    String fullName = userRepository.getFullNameByLoginAndPassword(login, UserRegisteringData.encryptPassword(password));
                    userRepository.closeConnection();
                    if (fullName != null) {
                        request.setAttribute(Header.MESSAGE, String.format(bundle.getString("accessGranted"), fullName));
                        request.getSession().setAttribute(Header.AUTHENTICATED_USER_KEY, fullName);
//  TODO I don't know why in this way categories don't show. Therefore set Header.CATEGORIES to null
//      next line doesn't work too... May be it send request to another page -> LOGIN_SUCCESS_PAGE
                        request.setAttribute(Header.CATEGORIES, null);
                        url = Header.PAGE_ROOT + Header.LOGIN_SUCCESS_PAGE;
                    } else {
                        count++;
                        if (count < MAX_TRY) {
                            if (count > 0) {
                                request.setAttribute(Header.LOGIN_MESSAGE, String.format(bundle.getString("accessDenied"), (MAX_TRY - count)));
                            }
                            url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
                        } else {
                            endTime = LocalDateTime.now();
                            request.setAttribute(Header.MESSAGE, String.format(bundle.getString("blockedForTimeout"), TIME_OUT));
                            url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                        }
                    }
                } catch (DataBaseException e) {
                    request.setAttribute(Header.MESSAGE, String.format(bundle.getString("dataBaseError"), e.getMessage()));
                    url = Header.PAGE_ROOT + Header.ERROR_PAGE;
                }
            }
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
//        response.setContentType("text/html");
        HttpSession session = request.getSession();
//      If exists Logout_Key parameter - close session (logout) and start new
        if (request.getParameter(Header.LOGOUT_KEY) != null) {
            session.invalidate();
            session = request.getSession(true);
        }
        String url = Header.PAGE_ROOT + Header.LOGIN_PAGE;
//      if user is authenticated - show Welcome else show login form
        if (session.getAttribute(Header.AUTHENTICATED_USER_KEY) != null) {
//          AUTHENTICATED_USER_KEY = fullName from doPost()
            url = Header.PAGE_ROOT + Header.LOGIN_SUCCESS_PAGE;
        }
        dispatcher = request.getRequestDispatcher(url);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.getWriter().write(e.getMessage());
        }
    }

}
