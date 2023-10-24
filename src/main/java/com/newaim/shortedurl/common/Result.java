package com.newaim.shortedurl.common;

import lombok.Data;

/**
 * @author Joseph.Liu
 */
@Data
public class Result<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <K> Result<K> success(int code, String message, K data){
        Result<K> result = new Result<>();
        result.setSuccess(true);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <K> Result<K> failed(int code, String message, K data){
        Result<K> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
