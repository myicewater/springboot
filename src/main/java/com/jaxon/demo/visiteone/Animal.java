package com.jaxon.demo.visiteone;

public class Animal {

    /**
     * 对于访问修饰符总结如下：
     * 1.远亲不如近邻， default是给住在一个家里(包)用的，不管是不是子孙
     * 2.protected是给子孙用的，不管走多远
     * 3.private是给自己用的，public是给所有人用的
     *
     */

    private String privateStr = "privateStr";
    String defaultStr = "defaultStr";
    protected String protectStr = "protectStr";
    public String publicStr = "publicStr";

    private String privateMeth(){
        return "privateMeth";
    }
    void defaultMeth(){}

    protected void protectMeth(){}

    public void publicMeth(){}

}
