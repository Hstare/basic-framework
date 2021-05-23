package com.hew.basicframework.extension;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author HeXiaoWei
 * @date 2021/5/23 19:17
 */
@Data
@ApiModel("扩展点组件")
public abstract class AbstractExtensionComponent {
    @ApiModelProperty("扩展点名字")
    private String name;
    @ApiModelProperty("扩展点唯一key")
    private String key;
    @ApiModelProperty("扩展点类型")
    private Class<?> type;
    @ApiModelProperty("扩展点排序")
    private Integer order;
    @ApiModelProperty("扩展点是否默认实现")
    private Boolean defaultComponent;

    /**
     * 根据type获取对象
     * @param <T> 对象
     * @return 对象
     */
    public abstract <T> T getTarget();
}
