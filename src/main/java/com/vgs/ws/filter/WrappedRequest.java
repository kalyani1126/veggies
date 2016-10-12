package com.vgs.ws.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Santosh.Kumar on 9/26/2016.
 */
public class WrappedRequest extends HttpServletRequestWrapper {

    public static final String HEADER_METHOD_OVERRIDE = "X-HTTP-Method-Override";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    private String requestMethod;
    private boolean userPassRequired = true;

    public WrappedRequest(HttpServletRequest request) throws ServletException {
        super(request);
        this.requestMethod = this.getRequestMethod(request);
    }

    public String getMethod() {
        return this.requestMethod;
    }

    protected String getRequestMethod(HttpServletRequest request) throws ServletException {
        String method = request.getMethod();
        String override = request.getHeader(HEADER_METHOD_OVERRIDE);
        if (override != null) {
            if (METHOD_DELETE.equals(override) && METHOD_GET.equals(method)) {
                method = METHOD_DELETE;
            } else {
                if (!METHOD_PUT.equals(override) || !METHOD_POST.equals(method)) {
                    method = request.getMethod();
                    throw new ServletException();
                }
                method = METHOD_PUT;
            }
        }
        return method;
    }

    public boolean isUserPassRequired() {
        return this.userPassRequired;
    }

    public void setUserPassRequired(boolean userPassRequired) {
        this.userPassRequired = userPassRequired;
    }
}
