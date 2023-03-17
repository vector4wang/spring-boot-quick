package com.shiro.quick.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactory implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazs) {
        return beanFactory.getBean(clazs);
    }

    public static <T> T getBean(String beanName, Class<T> clazs) {
        return clazs.cast(getBean(beanName));
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringBeanFactory.beanFactory = beanFactory;
    }
}
