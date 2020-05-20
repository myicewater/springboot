package com.example.demo.jdbcscraw;



import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public abstract class HttpRequestor {

    private static int TIMEOUT = 60 * 1000;

    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(TIMEOUT)// 请求超时时间
                    .setSocketTimeout(TIMEOUT)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
            System.out.println(url+",结果========"+result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(TIMEOUT)// 设置连接请求超时时间
                .setSocketTimeout(TIMEOUT)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        // 封装post请求参数
        if (null != params && params.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue() == null ? "" : mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();

            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static String doGetA(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            //设置期望服务端返回的编码
            httpget.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();
            httpget.setConfig(config);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            return httpClient.execute(httpget, responseHandler);
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                System.out.println("error when closing httpClient.");
            }
        }
        return null;
    }


    public static String doPost(String url, String params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

            httppost.setConfig(config);

           StringEntity entity=new StringEntity(params, Consts.UTF_8);
           entity.setContentEncoding("UTF-8");
            httppost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                return EntityUtils.toString(response.getEntity(),"GBK");
            } finally {
                response.close();
            }
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
            	System.out.println("error when closing httpClient.");
            }
        }
        return null;
    }

    public static String doPostJsonWx(String url, String params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

            httppost.setConfig(config);
            httppost.addHeader("Content-Type", "application/json");
            StringEntity entity=new StringEntity(params, Consts.UTF_8);
            entity.setContentEncoding("UTF-8");
            httppost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                return EntityUtils.toString(response.getEntity(),"GBK");
            } finally {
                response.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("error when closing httpClient.");
            }
        }
        return null;
    }

    public static String doPostJsonPark(String url, String authorization, String params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

            httppost.setConfig(config);
            httppost.addHeader("Accept","application/json");
            httppost.addHeader("Content-Type", "application/json;charset=utf-8");
            //httppost.addHeader("Content-Length","256");
            httppost.addHeader("Authorization",authorization);

            StringEntity entity=new StringEntity(params, Consts.UTF_8);
            entity.setContentEncoding("UTF-8");
            httppost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                return EntityUtils.toString(response.getEntity(),"GBK");
            } finally {
                response.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("error when closing httpClient.");
            }
        }
        return null;
    }

    public static String doPostJson(String url, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(url+"请求入参===="+json);
        try {
            HttpPost httppost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT).setSocketTimeout(TIMEOUT).build();

            httppost.setConfig(config);

           StringEntity entity=new StringEntity(json, ContentType.APPLICATION_JSON);
           entity.setContentEncoding("UTF-8");
            httppost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine().toString());
                String result = EntityUtils.toString(response.getEntity(), "GBK");
                System.out.println(url+"请求入参===="+json+",结果========"+result);
                return result;
            } finally {
                response.close();
            }
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                System.out.println("error when closing httpClient.");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //String json = "{\"pageNum\":11,\"orderBy\":\"1\",\"pageSize\":60,\"zsxq_yt1\":\"\",\"zsxq_yt2\":\"\",\"qy_p\":\"\",\"qy_c\":\"\",\"qy_a\":\"\",\"xmzt\":\"\",\"key\":\"\",\"wuyelx\":\"\",\"isHaveLink\":\"\",\"ifdporyt\":\"\"}";
        Map map = new HashMap();

        map.put("orderBy",1);
        map.put("pageSize",60);

        for(int i=1;i<456;i++){
            map.put("pageNum",i);
            String s = doPostJson("http://www.winshangdata.com/wsapi/project/list3_4", JSONObject.toJSONString(map));
        }

    }

}