package com.example.demo.jdbcscraw;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ScrawPorojectInfo {

    public static  void getProjectId() throws SQLException {


        Connection con = JdbcUtil.getConnection();
        String sql ="insert into project_url_flag values (?,?)";
        con.prepareStatement(sql);


        Map map = new HashMap();

        map.put("orderBy",1);
        map.put("pageSize",60);

        for(int i=1;i<456;i++){
            map.put("pageNum",i);
            RestTemplate restTemplate  = new RestTemplate();
            restTemplate.getForEntity("http://www.winshangdata.com/wsapi/project/list3_4",String.class,map);
            //String s = doPostJson(, JSONObject.toJSONString(map));
        }

    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>();

        map.put("orderBy",1);
        map.put("pageSize",60);
        RestTemplate restTemplate  = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<Map<String,Object>>(map,httpHeaders);


//        ResponseEntity forEntity =restTemplate.exchange("http://www.winshangdata.com/wsapi/project/list3_4", HttpMethod.POST,httpEntity,String.class);
//        ResponseEntity forEntity = restTemplate.getForEntity("http://www.winshangdata.com/wsapi/project/list3_4", String.class, map);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://www.winshangdata.com/wsapi/project/list3_4", httpEntity, String.class);
        System.out.println(stringResponseEntity.getBody());
    }
}
