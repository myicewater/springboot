package com.jaxon.demo.http;



import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil
{
    //httpclient连接池
    private static PoolingHttpClientConnectionManager cm;
    //连接池最大连接数
    private static int MAX_TOTAL = 800;
    //路由最大连接数
    private static int DEFAULT_MAX_PER_ROUTE = 800;
    //字符集编码
    private static String UTF_8 = "UTF-8";

    private static int TIMEOUT = 2 * 1000;

    private static SSLContextBuilder builder = null;

    private static SSLConnectionSocketFactory sslsf = null;

    private static Registry<ConnectionSocketFactory> registry = null;

    static {
        builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private static void init()
    {
        if (cm == null)
        {
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(MAX_TOTAL);// 整个连接池最大连接数
            cm.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);// 每路由最大连接数，默认值是2
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient()
    {
    	init();
        ConnectionKeepAliveStrategy connectionKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                Args.notNull(response, "HTTP response");
                final HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    final HeaderElement he = it.nextElement();
                    final String param = he.getName();
                    final String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                return 1;
            }
        };
        return HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(connectionKeepAliveStrategy).build();
    }

    /**
     * http get请求
     * @param url 请求地址
     * @param userName 用户名
     * @param passWord 密码
     * @param params 请求参数  map
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpGetRequest(String url, String userName, String passWord, Object... params) throws URISyntaxException, UnsupportedEncodingException
    {
        StringBuffer paramsStringBuffer = new StringBuffer();
        for (Object o : params)
        {
            paramsStringBuffer.append(o.toString()).append("/");
        }
        URIBuilder ub = new URIBuilder();
        ub.setPath(url + "/" + paramsStringBuffer.deleteCharAt(paramsStringBuffer.length() - 1));
        HttpGet httpGet = new HttpGet(ub.build());
        httpGet.addHeader("Accept", "application/json");
        //使用base64进行加密  
        byte[] tokenByte = Base64.encodeBase64((userName + ":" + passWord).getBytes(UTF_8));
        //将加密的信息转换为string  
        String token = new String(tokenByte);
        //把认证信息发到header中  
        httpGet.addHeader("Authorization", "Basic " + token);
        return getResult(httpGet);
    }
    /**
     * http get请求，密码不用base编码,新银座
     * @param url 请求地址
     * @param passWord 密码
     * @param params 请求参数  map
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpGetRequestByNewYZ(String url,String userName, String passWord, Object... params) throws URISyntaxException, UnsupportedEncodingException
    {
        StringBuffer paramsStringBuffer = new StringBuffer();
        for (Object o : params)
        {
            paramsStringBuffer.append(o.toString()).append("/");
        }
        URIBuilder ub = new URIBuilder();
        ub.setPath(url + "/" + paramsStringBuffer.deleteCharAt(paramsStringBuffer.length() - 1));
        HttpGet httpGet = new HttpGet(ub.build());
        httpGet.addHeader("Accept", "application/json");
        //将加密的信息转换为string
        String token = new String(passWord);
        //把认证信息发到header中
        httpGet.addHeader("Authorization", token);
        httpGet.addHeader("tenantId", userName);
        return getResult(httpGet);
    }
    /**
     * http post请求
     * @param url 请求地址
     * @param userName 用户名
     * @param passWord 密码
     * @param params 请求参数  json格式
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, String userName, String passWord, String params) throws URISyntaxException, UnsupportedEncodingException
    {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Accept", "application/json");
        //使用base64进行加密  
        byte[] tokenByte = Base64.encodeBase64((userName + ":" + passWord).getBytes(UTF_8));
        //将加密的信息转换为string  
        String token = new String(tokenByte);
        //把认证信息发到header中  
        httpPost.addHeader("Authorization", "Basic " + token);
        StringEntity entity = new StringEntity(params, UTF_8);//解决中文乱码问题
        entity.setContentEncoding(UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }
    /**
     * 密码不用base编码,新银座
     * http post请求
     * @param url 请求地址
     * @param passWord 密码
     * @param params 请求参数  json格式
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpPostRequestByNewYZ(String url,String userName, String passWord, String params) throws URISyntaxException, UnsupportedEncodingException
    {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Accept", "application/json");
        //将加密的信息转换为string
        String token = new String(passWord);
        //把认证信息发到header中
        httpPost.addHeader("Authorization", token);
        httpPost.addHeader("tenantId", userName);
        StringEntity entity = new StringEntity(params, UTF_8);//解决中文乱码问题
        entity.setContentEncoding(UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }
    /**
     * 处理Http请求
     *
     * @param request
     * @return
     * @throws IOException 
     */
    private static String getResult1(HttpRequestBase request) throws Exception
    {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
		try
        {
			CloseableHttpResponse response = httpClient.execute(request);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity, "UTF-8");
                response.close();
                // httpClient.close();
                return result;
            }
        }
        catch (Exception e)
        {
//            GooagooLog.error(serial,"处理Http请求异常",e);
            throw e;
        }       
        return "";
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request)
    {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try
        {
            CloseableHttpResponse response = httpClient.execute(request);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
        return "";
    }

    /**
     * http get请求
     * @param url 请求地址
     * @param params 请求参数  map
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpGetRequest(String url, Object... params) throws URISyntaxException, UnsupportedEncodingException
    {
        StringBuffer paramsStringBuffer = new StringBuffer();
        for (Object o : params)
        {
            paramsStringBuffer.append(o.toString()).append("/");
        }
        URIBuilder ub = new URIBuilder();
        ub.setPath(url + "/" + paramsStringBuffer.deleteCharAt(paramsStringBuffer.length() - 1));
        HttpGet httpGet = new HttpGet(ub.build());
        httpGet.addHeader("Accept", "application/json");
        return getResult(httpGet);
    }

    /**
     * http get请求
     * @param url 请求地址
     * @param params 请求参数  map
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Object... params) throws URISyntaxException, UnsupportedEncodingException
    {
        StringBuffer paramsStringBuffer = new StringBuffer();
        for (Object o : params)
        {
            paramsStringBuffer.append(o.toString()).append("/");
        }
        URIBuilder ub = new URIBuilder();
        ub.setPath(url + "/" + paramsStringBuffer.deleteCharAt(paramsStringBuffer.length() - 1));
        HttpPost httpPost = new HttpPost(ub.build());
        httpPost.addHeader("Accept", "application/json");
        return getResult(httpPost);
    }

    /**
     * http post请求
     * @param url 请求地址
     * @param params 请求参数  json格式
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, String params) throws URISyntaxException, UnsupportedEncodingException
    {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Accept", "application/json");
        StringEntity entity = new StringEntity(params, UTF_8);//解决中文乱码问题
        entity.setContentEncoding(UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }

    /**
     * http post请求
     * @param url 请求地址
     * @param params 请求参数  json格式
     * @return string
     * @throws Exception 
     */
    public static String httpPostRequest1(String url, String params) throws Exception
    {
//        int TIMEOUT = 5 * 100;
        //int TIMEOUT = 8 * 100; //2020-02-11暂时取消时间设置
    	int index = 1;
        while(true){
    		try {
    			HttpPost httpPost = new HttpPost(url);
    			httpPost.addHeader("Accept", "application/json");
    			StringEntity entity = new StringEntity(params, UTF_8);//解决中文乱码问题
    			entity.setContentEncoding(UTF_8);
    			entity.setContentType("application/json");
    			httpPost.setEntity(entity);
    			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIMEOUT)// 连接主机服务超时时间
    					.setConnectionRequestTimeout(TIMEOUT)// 请求超时时间
    					.setSocketTimeout(TIMEOUT)// 数据读取超时时间
    					.build();
    			httpPost.setConfig(requestConfig);
    			return getResult1(httpPost);
    		} catch (HttpHostConnectException e) {
    			if (index > 2) {
    				throw e;
    			}
    			index++;
    			continue;
    		}catch (NoHttpResponseException e){
				index++;
    			continue;
    		}catch (Exception e) {
    			throw e; 
    		}
    	}
    }


    /**
     * http post请求
     * @param url 请求地址
     * @param params 请求参数  json格式
     * @return string
     * @throws URISyntaxException,UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException, UnsupportedEncodingException
    {
        HttpPost httpPost = new HttpPost(url);
        if (!CollectionUtils.isEmpty(headers))
        {
            for (Map.Entry<String, String> m : headers.entrySet())
            {
                httpPost.addHeader(m.getKey(), m.getValue());
            }
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (!CollectionUtils.isEmpty(params))
        {
            for (Map.Entry<String, String> m : params.entrySet())
            {
                nvps.add(new BasicNameValuePair(m.getKey(), m.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        return getResult(httpPost);
    }
    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json)
    {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try
        {
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = res.getEntity();
                System.out.println(entity.getContentEncoding());
                String result = EntityUtils.toString(res.getEntity(), "UTF-8");// 返回json格式：
                //response = JSONObject.parse(result);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return response;
    }


    public static void main(String[] args) throws Exception {



//        String url  ="https://www.facebook.com/";
//        String url  ="https://www.face456789bo87654ok.com/";
        String url = "http://192.168.101.13:8080/acceptBill";
        String msg = "";
        try {
            httpPostRequest1(url,"");//httpclient 发起请求
            msg = "业务处理信息";
        } catch (UnknownHostException e){
            msg = e.getMessage();
            msg += " 未知域名，解析失败";
            e.printStackTrace();
        }catch( ConnectTimeoutException e){
            msg = e.getMessage();
            msg += " 建立连接超时";
            e.printStackTrace();
        }catch (SocketTimeoutException e) {
            msg = e.getMessage();
            if(msg != null){
                if("connect timed out".equals(msg)){//httpclient 不抛出这个异常，抛出ConnectTimeoutException
                    msg+= " 建立连接超时";
                }else if("Read timed out".equals(msg)){
                    msg+= " 接口响应超时";
                }
            }
            e.printStackTrace();
        }catch (Exception e){
            msg = e.getMessage();
            e.printStackTrace();
        }finally{
            System.out.println("请求返回结果:"+msg);
            //处理返回结果
        }
    }

}
