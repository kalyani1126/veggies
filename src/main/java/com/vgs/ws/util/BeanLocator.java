package com.vgs.ws.util;

import com.vgs.ws.service.TokensService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Santosh.Kumar
 * Date : Sep 25, 2016
 */
public class BeanLocator implements ApplicationContextAware {

    private static final String TOKENS_SERVICE_BEAN = "tokensService";

    private static final BeanLocator beanInstance = new BeanLocator();

    private ApplicationContext applicationContext;

    /**
     * Get the instance of BeanLocator.
     *
     * @return The BeanLocator instance
     */
    public static BeanLocator getInstance() {
        return beanInstance;
    }

    /**
     * Private constructor.
     */
    private BeanLocator() {
    }

    /**
     * Sets the ApplicationContext that the BeanLocator runs in. This is called to initialize
     * the applicationContex object.
     *
     * @param applicationContext - the ApplicationContext object to be used by the bean locator.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @return The retrieved bean form ApplicationContext
     */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * Retrieves the bean from ApplicationContext.
     *
     * @param name The name of the bean
     * @param type The type of the bean
     *
     * @return The retrieved bean form ApplicationContext
     */
    public Object getBean(String name, Class<?> type) {
        return applicationContext.getBean(name, type);
    }

    /**
     * Retrieves the bean from ApplicationContext.
     *
     * @param name The name of the bean
     *
     * @return The retrieved bean form ApplicationContext
     */
    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * @return TokensService the service class for tokens operation.
     */
    public TokensService getTokensServiceBean() {
        return (TokensService) getBean(TOKENS_SERVICE_BEAN);
    }
}
