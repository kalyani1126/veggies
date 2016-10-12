package com.vgs.ws.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class RequestHttpFilter implements Filter {
    public RequestHttpFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(new WrappedRequest((HttpServletRequest)request), response);
        } catch (ServletException var5) {
            ((HttpServletResponse)response).sendError(400);
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
