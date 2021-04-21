package com.jaxon.demo.netty;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.client.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MsgPro {

    private String msgId;

    private String msgTime;//yyyyMMddHHmmss

    /**
     * 消息类型
     * 0：原消息
     * 1：回复消息
     *
     */
    private Integer msgType = 0;

    /**
     * 操作编码
     * DEVICE_AUTH - 终端授权
     *
     * GET_NEW_ADD_CONFIG - 获取新广告配置
     */
    private String operCode;

    //消息内容
    /**
     * shopEntityId
     * mac
     * token
     */
    private Map data;

    public MsgPro(){
        this.msgId = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        this.msgTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public static void main(String[] args) {

    }

}

