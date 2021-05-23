package com.hew.basicframework.extension;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 18:51
 */
@Data
public abstract class AbstractExtension {
    private Class<?> extensionType;
    private AbstractExtensionComponent extensionComponent;

    private Map<String, AbstractExtensionComponent> ExtensionComponentMap = Maps.newHashMap();

    public void put(String key, AbstractExtensionComponent extensionComponent) {
        ExtensionComponentMap.putIfAbsent(key, extensionComponent);
    }
    public AbstractExtensionComponent get(String key) {
        AbstractExtensionComponent extensionComponent = ExtensionComponentMap.get(key);
        if(Objects.isNull(extensionComponent)) {
            return null;
        }
        return extensionComponent;
    }

//    /**
//     * 获取目标对象
//     * @param key 扩展点唯一key
//     * @param <T> 目标对象
//     * @return 目标对象
//     */
//    public <T> T getTarget(String key) {
//        AbstractExtensionComponent extensionComponent = ExtensionComponentMap.get(key);
//        if(Objects.isNull(extensionComponent)) {
//            return null;
//        }
//        return extensionComponent.getTarget();
//    }
//
//    /**
//     * 获取目标对象List
//     * @param <T> 目标对象接口
//     * @return 目标对象List
//     */
//    public <T> List<T> getTargetList() {
//        List<T> targetList = Lists.newArrayList();
//        if(CollectionUtils.isEmpty(ExtensionComponentMap)) {
//            return targetList;
//        }
//        targetList = (List<T>) ExtensionComponentMap.values().stream().sorted(Comparator.comparing(AbstractExtensionComponent::getOrder)).map(AbstractExtensionComponent::getTarget).collect(Collectors.toList());
//        return targetList;
//    }
}
