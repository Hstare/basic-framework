package com.hew.basicframework.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 14:47
 */
@Data
@Accessors(chain = true)
@TableName("systemLog")
public class SystemLogInfo {
    /**
     * id
     * 根据需要自定义
     */
//    @TableId(type = IdType.ID_WORKER_STR)
//    private String id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 耗时
     */
    private Long costTime;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 请求方法
     */
    private String method;

    /**
     * 操作人用户名称
     */
    private String username;
    /**
     * 操作详细日志
     */
    private String logContent;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 操作类型（0 统计，1查询，2添加，3修改，4删除,5导入，6导出）
     */
    private Integer operateType;
}
