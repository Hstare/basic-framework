package com.hew.basicframework.DO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author HeXiaoWei
 * @date 2020/10/13 17:50
 */
@Data
@Accessors(chain = true)
public class UserInfo {
    private String username;
    private String password;

    /**
     * 和 authorities 值一样
     */
    private Set<String> authorities;
    @Transient
    private List<SimpleGrantedAuthority> grantedAuthorities;
    private boolean accountNonExpired = true;;
    private boolean accountNonLocked = true;;
    private boolean credentialsNonExpired = true;;
    private boolean enabled = true;
}
