package com.hew.basicframework.DO;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author HeXiaoWei
 * @date 2020/11/3 10:12
 */
@Data
public class Users {
    private Integer id;

    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
