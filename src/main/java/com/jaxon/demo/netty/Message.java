package com.jaxon.demo.netty;

public class Message {

    private String cmd;//命令

    private String content;

    public Message(String cmd, String content) {
        this.cmd = cmd;
        this.content = content;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
