package com.leyou.user.pojo;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "role")
@Data
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    private long id;

    private String name;

    private String nameZh;
}
