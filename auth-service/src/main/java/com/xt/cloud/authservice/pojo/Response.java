package com.xt.cloud.authservice.pojo;

import java.util.Date;

/**
 * Created by shawn on 2017/11/20.
 */
public class Response {
    private int code;
    private String msg;
    private String data;
    private String timestamp;

    public Response(String msg){
        this.code = 1;
        this.msg = msg;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public Response(int code, String msg){
        this.code = code;
        this.msg = msg;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public Response(int code, String msg, String data){
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
