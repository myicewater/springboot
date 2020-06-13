package com.jaxon.demo.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodeDecode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("[{\"field\":\"maxUpdateTime\",\"order\":\"DESC中文\"}]","utf-8");
        System.out.println(encode);
        String decode = URLDecoder.decode(encode, "utf-8");
        System.out.println(decode);
    }
}
