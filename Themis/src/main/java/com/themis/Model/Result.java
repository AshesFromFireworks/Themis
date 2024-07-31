package com.themis.Model;

import java.util.HashMap;

public class Result<T> {

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T>Result<T> success(String code, String msg, T data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result<HashMap<String,String>> success(String code, String msg, HashMap<String,String> data){
        Result<HashMap<String,String>> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return  result;
    }

    public static Result<HashMap<String, String>> error(String code, String msg){
        Result<HashMap<String, String>> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(new HashMap<>());
        return result;
    }

    public static <T>Result<T> error(String code, String msg, T data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


}
