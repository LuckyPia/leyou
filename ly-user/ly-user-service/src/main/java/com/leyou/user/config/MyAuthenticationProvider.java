package com.leyou.user.config;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import com.leyou.user.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("前端传过来的明文密码:" + password);
        User user =(User) userService.loadUserByUsername(username);

        //加密过程在这里体现
        System.out.println("结果UserService后，已经查询出来的数据库存储密码:" + user.getPassword());
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
            throw new DisabledException("密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}