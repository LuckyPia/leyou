package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user")
@Data
public class Note {

    @Id
    @KeySql(useGeneratedKeys = true)
    private long noteId;
    private String title;
    private String content;
    private java.sql.Timestamp noteDate;
    private long fromUser;
    private java.sql.Timestamp creationTime;
    private java.sql.Timestamp updateTime;

}
