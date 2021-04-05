package com.maat.user;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Custom Application Context to get beans, created by Spring, on demand.
 */
public class UsersAppContext implements ApplicationContextAware {
    private static ApplicationContext APP_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APP_CONTEXT = applicationContext;
    }

    public static Object getBean(String beanName) {
        return APP_CONTEXT.getBean(beanName);
    }
}
