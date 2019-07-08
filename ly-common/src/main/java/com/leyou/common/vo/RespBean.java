package com.leyou.common.vo;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RespBean<T> implements Serializable {
    private Integer status;
    private String message;
    private T data;

    public static <T> RespBean<T> ok(String message, T data) {
        return new RespBean<>(200, message, data);
    }

    public static <T> RespBean<T> ok(String message) {
        return new RespBean<>(200, message, null);
    }

    public static <T> RespBean<T> error(String message) {
        return new RespBean<>(500, message, null);
    }

    public static <T> RespBean<T> status(Integer status, String message) {
        return new RespBean<>(status, message, null);
    }

    public static <T> RespBean<T> status(Integer status, String message, T data) {
        return new RespBean<>(status, message, data);
    }

    private RespBean(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public RespBean setMsg(String msg) {
        this.message = msg;
        return this;
    }

    public RespBean setData(T data) {
        this.data = data;
        return this;
    }
}
