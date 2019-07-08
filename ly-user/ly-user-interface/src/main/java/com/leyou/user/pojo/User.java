package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "user")
@Data
public class User implements UserDetails {

    @Id
    @KeySql(useGeneratedKeys = true)
    private long userId;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp="^[a-zA-Z0-9_\u4e00-\u9fa5-]{2,10}$",
            message="用户名格式错误，不能使用特殊字符，长度在2-10位之间")
    private String username;

    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$",
            message="密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-16位之间")
    private String password;

    @JsonIgnore
    private String salt;

    @NotBlank(message="手机号码不能为空")
    @Pattern(regexp="^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|1|3|5|6|7|8]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",
            message="手机号码错误或格式不支持")
    private String phone;

    @Email
    private String email;

    private long sex;

    @Column(name = "RealName")
    private String realName;

    @Column(name = "CreationTime")
    private java.sql.Timestamp creationTime;

    @Column(name = "UpdateTime")
    private java.sql.Timestamp updateTime;

    @JsonIgnore
    @Column(name = "Enabled")
    private Boolean enabled1;

    @JsonIgnore
    @Column(name = "Locked")
    private Boolean locked;

    @Transient
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled1;
    }

}
