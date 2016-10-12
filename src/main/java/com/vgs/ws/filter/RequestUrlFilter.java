package com.vgs.ws.filter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @class RequestUrlFilter is for logging the request url info
 *
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class RequestUrlFilter implements Filter {

    private static final Logger logger = Logger.getLogger("com.ws.request.log");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        logger.log(Level.INFO, httpRequest.getRemoteAddr() + "  " + httpRequest.getRequestURL().toString() + "  " + httpRequest.getMethod());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //do nothing
    }
}
