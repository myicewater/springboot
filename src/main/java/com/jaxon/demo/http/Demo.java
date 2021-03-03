package com.jaxon.demo.http;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Demo {

    public static void main(String[] args) throws Exception {

        String url = "http://api.test.goago.cn/oapi/rest";
        String appId = "f39725425a454793a09eeae47d3f1b94";
        String appKey = "2c908a4277d35a450177d35a45860000";
        String key = "D785156549E1DBB09472B36D0D4E2729";

        //业务参数，换成对应接口的业务参数
        Map<String, Object> dataParam = new HashMap<>();
        dataParam.put("squareCode", "1004");
        dataParam.put("channelCode", "XinTian360");

        // 公用参数，换成对应接口的公共参数
        Map<String, String> param = new HashMap<>();
        param.put("method", "gogo.open.auto.routing");
        param.put("timestamp", "20210302152700"); // 时间戳
        param.put("messageFormat", "json");
        param.put("appKey", appKey);
        param.put("appId", appId);
        param.put("v", "1.0");
        param.put("signMethod", "MD5");
        param.put("lowerMethod", "gogo.open.shopEntityRelations");

        param.put("data", new GsonBuilder().disableHtmlEscaping().create().toJson(dataParam));

        // 签名
        String sign = getSign(key, param, param.get("signMethod"));

        List<NameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        paramList.add(new BasicNameValuePair("sign", sign));
        System.out.println(paramList.toString());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(url);

            // 配置请求参数实例
            RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接主机服务超时时间
                .setConnectTimeout(60 * 1000)
                // 设置连接请求超时时间
                .setConnectionRequestTimeout(60 * 1000)
                // 设置读取数据连接超时时间
                .setSocketTimeout(60 * 1000)
                .build();

            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);

            // 设置请求头
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            HttpEntity entity = httpResponse.getEntity();

            // 接口返回结果
            String result = EntityUtils.toString(entity);
            System.out.println(result);

            httpPost.releaseConnection();
        }
    }

    /**
     * 签名算法
     *
     * @param appSecret 签名密钥
     * @param params 请求参数
     * @param signMethod 签名方法
     * @return 签名
     */
    public static String getSign(String appSecret, Map<String, String> params, String signMethod) {

        // 1. 将所有参数排序
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 2. 把所有参数名和参数值串在一起
        StringBuilder signStr = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key);

            // 只取value不为空的
            if (null == value || "".equals(value)) {
                continue;
            }
            signStr.append(key).append("=").append(value).append("&");
        }

        // 3. 拼接签名密钥
        signStr.append("key=").append(appSecret);

        // 4. 签名
        if ("MD5".equals(signMethod)) {
            return encrypt(signStr.toString(), "MD5");
        }

        return encrypt(signStr.toString(), "SHA");

    }

    /**
     * 加密方法
     *
     * @param str 待加密数据
     * @param algorithm 加密类型 MD5，SHA
     * @return 加密后数据
     */
    private static String encrypt(String str, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            StringBuilder hexstr = new StringBuilder();
            String shaHex;
            for (byte b : digest) {
                shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
