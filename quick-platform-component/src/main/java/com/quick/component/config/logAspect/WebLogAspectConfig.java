package com.quick.component.config.logAspect;

import com.quick.component.enables.QsEnableAroundLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.function.Supplier;

/**
 * 动态设置切点表达式
 */
@Slf4j
public class WebLogAspectConfig implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(QsEnableAroundLog.class.getName()));
        String value = annoAttrs.getString("value");

        log.info("init log aspect: {}", value);
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(AspectJExpressionPointcutAdvisor.class, new Supplier<AspectJExpressionPointcutAdvisor>() {
            @Override
            public AspectJExpressionPointcutAdvisor get() {
                AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
                advisor.setExpression(value);
                advisor.setAdvice(new LogAdvice());
                return advisor;
            }
        });

        registry.registerBeanDefinition("aspectJExpressionPointcutAdvisor", beanDefinition.getBeanDefinition());

    }
}
