package com.hew.basicframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hew.basicframework.DO.Users;
import com.hew.basicframework.utils.ExcelUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
import java.util.List;

/**
 * @author HeXiaoWei
 * @date 2020/11/3 10:11
 */
@Repository
public interface UsersMapper extends ExcelMapper<Users> {
    public List<Users> selectAll();
}
