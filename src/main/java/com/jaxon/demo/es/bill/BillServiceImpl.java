package com.jaxon.demo.es.bill;

import com.alibaba.fastjson.JSON;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BillServiceImpl implements BillService{


    @Resource
    private RestClient restClient;

    private Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);


    @Override
    public void addBill(BillInfo billInfo) {
        Request request = new Request(HttpMethod.POST.name(),new StringBuilder("/bill/_doc/").append(billInfo.getId()).toString());

        request.addParameter("pretty", "true");

        request.setEntity(new NStringEntity(JSON.toJSONString(billInfo), ContentType.APPLICATION_JSON));

        try{
            Response response = restClient.performRequest(request);

            String responseBody = EntityUtils.toString(response.getEntity());
            logger.info("结果："+responseBody);
        }catch(Exception e){}

    }
}
