package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Data
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private long userId;
    private String username;
    private String password;
    private String telNum;
    private String email;
    private long sex;
    private String realName;
    private java.sql.Timestamp creationTime;
    private java.sql.Timestamp updateTime;

}
