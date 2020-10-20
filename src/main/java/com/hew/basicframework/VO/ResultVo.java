package com.hew.basicframework.VO;

import com.alibaba.fastjson.JSON;
import com.hew.basicframework.enums.CodeMessageEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

/**
 * 返回结果实体类
 * @author pai
 * @date 2020/8/10
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "返回结果的封装")
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public String success(T data){
        return success(CodeMessageEnum.SUCCESS.getMsg(), data);
    }

    public String success(String msg, T data){
        ResultVo<T> resultVo = new ResultVo<>(CodeMessageEnum.SUCCESS.getCode(), msg, data);
        return JSON.toJSONString(resultVo);
    }

    public String fail(String msg) {
        return fail(CodeMessageEnum.FAIL.getCode(),msg);
    }

    public String fail(Integer code, String msg) {
        if(StringUtils.isEmpty(msg)) {
            msg = CodeMessageEnum.FAIL.getMsg();
        }
        ResultVo<T> resultVo = new ResultVo<>(code, msg, null);
        return JSON.toJSONString(resultVo);
    }
}
