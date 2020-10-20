package com.hew.basicframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hew.basicframework.DO.SystemLogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 16:03
 */
@Mapper
public interface SystemLogMapper extends BaseMapper<SystemLogInfo> {
}
