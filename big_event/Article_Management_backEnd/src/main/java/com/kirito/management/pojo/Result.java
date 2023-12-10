package com.kirito.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//定制返回格式类型
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Result<T> {

    private String code;
    private String message;
    private T data;

    public static <E> Result<E> success(E data) {
        return new Result<>("1", "success", data);
    }

    //重载
    public static <E> Result<E> success() {
        return new Result<>("1", "success", null);
    }
    //重载
    public static <E> Result<E> fail(String message) {
        return new Result<>("0", message,null);
    }

}
