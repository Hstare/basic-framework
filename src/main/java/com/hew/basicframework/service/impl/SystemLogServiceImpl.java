package com.hew.basicframework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hew.basicframework.DO.SystemLogInfo;
import com.hew.basicframework.mapper.SystemLogMapper;
import com.hew.basicframework.service.SystemLogService;
import org.springframework.stereotype.Service;

/**
 * @author HeXiaoWei
 * @date 2020/10/18 15:28
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLogInfo> implements SystemLogService {
}
