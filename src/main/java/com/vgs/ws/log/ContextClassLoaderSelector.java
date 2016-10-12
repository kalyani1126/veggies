package com.vgs.ws.log;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootCategory;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Santosh.Kumar 
 * Date : Oct 28, 2016
 */
public class ContextClassLoaderSelector implements RepositorySelector {
    /**
     * key: current thread's ContextClassLoader,
     * value: Hierarchy instance
     */
    private final Map<ClassLoader, Hierarchy> hierMap;

    /**
     * public no-args constructor
     */
    public ContextClassLoaderSelector() {
        hierMap = Collections.synchronizedMap(new WeakHashMap<ClassLoader, Hierarchy>());
    }

    /**
     * implemented RepositorySelector interface method. The returned
     * value is guaranteed to be non-null.
     *
     * @return the appropriate classloader-keyed Hierarchy/LoggerRepository
     */
    public LoggerRepository getLoggerRepository() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Hierarchy hierarchy = hierMap.get(cl);

        if (hierarchy == null) {
            hierarchy = createHierarchy(new RootCategory(Level.DEBUG));
            hierMap.put(cl, hierarchy);
        }

        return hierarchy;
    }

    protected Hierarchy createHierarchy(Logger rootLogger) {
        return new Hierarchy(rootLogger);
    }
}
