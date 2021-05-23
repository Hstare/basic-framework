package com.hew.basicframework.extension;


import com.google.common.collect.Maps;
import com.hew.basicframework.extension.impl.DefaultExtensionImpl;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 19:58
 */
@Component
public class ExtensionContext {
    private Map<Class, AbstractExtension> extensionMap = Maps.newHashMap();

    public Boolean exitExtension(Class<?> extensionType) {
        return extensionMap.containsKey(extensionType);
    }

    public Boolean nonExitExtension(Class<?> extensionType) {
        return !exitExtension(extensionType);
    }

    /**
     * 注册扩展
     * @param extension 对象
     */
    public void registerExtension(AbstractExtension extension) {
        extensionMap.putIfAbsent(extension.getExtensionType(), extension);
    }

    /**
     * 获取扩展
     * @param extensionType class类型
     * @return Extension对象
     */
    public AbstractExtension getExtension(Class<?> extensionType) {
        AbstractExtension extension = extensionMap.get(extensionType);
        if(Objects.isNull(extension)) {
            return DefaultExtensionImpl.builder().build();
        }
        return extension;
    }
}
