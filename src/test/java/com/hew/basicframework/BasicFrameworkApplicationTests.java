package com.hew.basicframework;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hew.basicframework.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicFrameworkApplicationTests {

    @Test
    void contextLoads() {
        JSONObject jo = new JSONObject();
        jo.put("name","张三");
        jo.put("age",15);
        RedisUtils.set("test",jo);
        Object test = RedisUtils.get("test");
        System.out.println(test.toString());
    }

}
