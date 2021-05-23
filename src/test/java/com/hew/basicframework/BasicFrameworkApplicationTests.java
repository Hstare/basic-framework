package com.hew.basicframework;

import com.hew.basicframework.DO.Users;
import com.hew.basicframework.mapper.UsersMapper;
import com.hew.basicframework.utils.ExcelUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
class BasicFrameworkApplicationTests {
    @Autowired
    UsersMapper usersMapper;

    @Test
    void contextLoads() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\测试.xlsx"));
        ExcelUtils.read(fileInputStream, Users.class, usersMapper);
    }

    @Test
    void testWriteExcel() {

    }

    @Test
    public void testDB() {
        List<Users> users = usersMapper.selectAll();
        users.size();
        System.out.println("111");
    }

}
