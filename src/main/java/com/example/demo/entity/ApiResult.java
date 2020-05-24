package com.example.demo.entity;

public class ApiResult<T> {

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResult success(Object data){
        ApiResult apiResult = new ApiResult();
        apiResult.code = 1;
        apiResult.msg = "请求成功";
        apiResult.data = data;
        return apiResult;
    }

    public static ApiResult fail(int code,String msg){
        ApiResult apiResult = new ApiResult<>();
        apiResult.code = code;
        apiResult.msg = msg;
        return apiResult;
    }
}
