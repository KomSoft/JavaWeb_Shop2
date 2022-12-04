package com.komsoft.shop2.filter;

import com.komsoft.shop2.util.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class AuthorizationFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("[>>AuthorizationFilter<<] Init Filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        String authenticatedUser = (String) session.getAttribute(Header.AUTHENTICATED_USER_KEY);
        if (authenticatedUser == null) {
            logger.info("[>>AuthorizationFilter<<] Not Authenticated user called");
            String url = Header.PAGE_ROOT + Header.ERROR_PAGE;
            session.setAttribute(Header.MESSAGE, "Error 403. Access forbidden.<br>You aren't authorized for this page(s)");
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(url);
            dispatcher.forward(httpServletRequest, servletResponse);
        } else {
            logger.info(String.format("[>>AuthorizationFilter<<] user: %s called", authenticatedUser));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
