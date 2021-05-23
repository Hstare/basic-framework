package com.hew.basicframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hew.basicframework.DO.SystemLogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 16:03
 */
@Repository
public interface SystemLogMapper extends BaseMapper<SystemLogInfo> {
}
