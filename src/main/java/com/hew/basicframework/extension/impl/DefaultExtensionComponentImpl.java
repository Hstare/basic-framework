package com.hew.basicframework.extension.impl;

import com.hew.basicframework.annotation.Activation;
import com.hew.basicframework.extension.AbstractExtensionComponent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 22:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultExtensionComponentImpl extends AbstractExtensionComponent {
    private ApplicationContext applicationContext;

    @Override
    public <T> T getTarget() {
        String[] beanNames = applicationContext.getBeanNamesForType(getType());
        if(beanNames.length > 1) {
            Optional<String> beanNameOptional = Arrays.stream(beanNames).filter(beanName -> {
                Class<?> target = applicationContext.getType(beanName);
                if (AopUtils.isAopProxy(target)) {
                    target = AopUtils.getTargetClass(target);
                }

                if (target.isAnnotationPresent(Activation.class)) {
                    Activation activation = target.getAnnotation(Activation.class);
                    if (activation.value().length > 0) {
                       return Stream.of(activation.value()).filter(value -> Objects.equals(value, getKey())).count() > 0L;
                    }
                }
                return target.getSimpleName().equals(getName());
            }).findFirst();
            if(beanNameOptional.isPresent()) {
                return (T) applicationContext.getBean(beanNameOptional.get());
            }else {
                throw new RuntimeException("Can Not Find Target Type" + getType().getSimpleName());
            }
        }
        return (T) applicationContext.getBean(getType());
    }
}
