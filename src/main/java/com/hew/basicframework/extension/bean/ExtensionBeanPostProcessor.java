package com.hew.basicframework.extension.bean;

import com.google.common.collect.Lists;
import com.hew.basicframework.annotation.Activation;
import com.hew.basicframework.annotation.SPIExtension;
import com.hew.basicframework.extension.AbstractExtension;
import com.hew.basicframework.extension.ExtensionContext;
import com.hew.basicframework.extension.impl.DefaultExtensionComponentImpl;
import com.hew.basicframework.extension.impl.DefaultExtensionImpl;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 19:54
 */
@Component
public class ExtensionBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private ExtensionContext extensionContext;

    public ExtensionBeanPostProcessor(ExtensionContext extensionContext) {
        this.extensionContext = extensionContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Activation.class)) {
            Class<?> beanClass = bean.getClass();
            if(AopUtils.isAopProxy(bean)) {
                beanClass = AopUtils.getTargetClass(bean);
            }
            registerClassExtension(beanClass);
        }
        return bean;
    }

    /**
     * 注册抽象类以及一般的SPI
     * @param bean
     */
    private void registerClassExtension(Class<?> bean) {
        Class topInterface = getTopInterface(bean);
        if(Objects.isNull(topInterface)) {
            return;
        }

        if(extensionContext.nonExitExtension(topInterface)) {
            DefaultExtensionImpl defaultExtension = DefaultExtensionImpl.builder().build();
            defaultExtension.setExtensionType(topInterface);
            extensionContext.registerExtension(defaultExtension);
        }
        registerExtensionComponent(bean, extensionContext.getExtension(topInterface));
    }

    /**
     * 获取SPIExtension注解的类（一般放在接口上）
     * @param beanClass SPIExtension注解的类的子类
     * @return SPIExtension注解的类
     */
    private Class getTopInterface(Class beanClass) {
        Class[] interfaces = beanClass.getInterfaces();
        if(interfaces.length == 0) {
            Class superclass = beanClass.getSuperclass();
            if(Objects.nonNull(superclass)) {
                getTopInterface(superclass);
            }
        }
        for (Class clazz : interfaces) {
            if(clazz.isAnnotationPresent(SPIExtension.class)) {
                return clazz;
            }
            Class[] childInterfaces = clazz.getInterfaces();
            if(childInterfaces != null) {
                for (Class childClazz : childInterfaces) {
                    return getTopInterface(childClazz);
                }
            }
        }
        return null;
    }

    private void registerExtensionComponent(Class clazz, AbstractExtension extension) {
        List<DefaultExtensionComponentImpl> extensionComponentList = Lists.newArrayList();
        DefaultExtensionComponentImpl component = DefaultExtensionComponentImpl.builder().build();
        component.setName(clazz.getSimpleName());
        component.setKey(clazz.getSimpleName());
        component.setOrder(0);
        component.setType(clazz);
        component.setApplicationContext(applicationContext);
        if(clazz.isAnnotationPresent(Activation.class)) {
            Activation activation = (Activation) clazz.getAnnotation(Activation.class);
            component.setOrder(activation.order());
            component.setDefaultComponent(activation.defaultValue());
            String[] values = activation.value();
            if(values.length > 0) {
                for (String value : values) {
                    component.setKey(value);
                    extensionComponentList.add(component);
                }
            }else {
                extensionComponentList.add(component);
            }
            for (DefaultExtensionComponentImpl extensionComponent : extensionComponentList) {
                if(extensionComponent.getDefaultComponent()) {
                    extension.setExtensionComponent(extensionComponent);
                }
                extension.put(extensionComponent.getKey(), extensionComponent);
            }
        }
    }
}
