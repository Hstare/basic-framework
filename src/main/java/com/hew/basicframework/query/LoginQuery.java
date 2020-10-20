package com.hew.basicframework.query;

import lombok.Data;
import lombok.extern.java.Log;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:56
 */
@Data
public class LoginQuery {
    private String username;
    private String password;
}
