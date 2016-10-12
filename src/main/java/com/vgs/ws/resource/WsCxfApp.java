package com.vgs.ws.resource;

import com.vgs.ws.resource.tokens.TokenResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class WsCxfApp extends Application {

    /**
     * @return
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(TokenResource.class);
        return set;
    }

}
