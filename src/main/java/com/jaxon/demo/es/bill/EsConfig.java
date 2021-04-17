package com.jaxon.demo.es.bill;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    private Logger logger = LoggerFactory.getLogger(EsConfig.class);

    @Bean
    public RestClient getClient(){
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("192.168.10.67", 9200, "http"));
        Header[] defaultHeaders = {new BasicHeader("header","value")};

        clientBuilder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
                logger.error(node.getName() + "==节点失败了");
            }
        });

        clientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                // 连接5秒超时，套接字连接60s超时
                return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000);
            }
        });

        return clientBuilder.build();
    }
}
