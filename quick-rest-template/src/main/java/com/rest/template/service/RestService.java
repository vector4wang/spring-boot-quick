package com.rest.template.service;

import com.alibaba.fastjson.JSON;
import com.rest.template.model.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vector
 * @date: 2019/3/15 0015 9:26
 */
@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    private static String HOST = "http://localhost:8080";

    private static String GET_URL = "/testGet";
    private static String POST_URL = "/testPost";
    private static String POST_PARAM_URL = "/testPostParam";
    private static String PUT_URL = "/testPut";
    private static String DEL_URL = "/testDel";


    public void get() throws URISyntaxException {
        ResponseEntity<TestDTO> responseEntity = this.restTemplate.getForEntity(HOST + GET_URL, TestDTO.class);
        System.out.println("getForEntity: " + responseEntity.getBody());

        TestDTO forObject = this.restTemplate.getForObject(HOST + GET_URL, TestDTO.class);
        System.out.println("getForObject: " + forObject);

        RequestEntity<Void> requestEntity = RequestEntity.get(new URI(HOST + GET_URL)).build();
        ResponseEntity<TestDTO> exchange = this.restTemplate.exchange(requestEntity, TestDTO.class);
        System.out.println("exchange: " + exchange.getBody());
    }

    public void post() throws URISyntaxException {

        TestDTO td = new TestDTO();
        td.setId(1);
        td.setName("post");

        String url = HOST + POST_URL;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TestDTO> httpEntity = new HttpEntity<>(td, headers);


        ResponseEntity<TestDTO> responseEntity = this.restTemplate.postForEntity(url, httpEntity, TestDTO.class);
        System.out.println("postForEntity: " + responseEntity.getBody());

        TestDTO testDTO = this.restTemplate.postForObject(url, httpEntity, TestDTO.class);
        System.out.println("postForObject: " + testDTO);

        ResponseEntity<TestDTO> exchange = this.restTemplate.exchange(url, HttpMethod.POST, httpEntity, TestDTO.class);
        System.out.println("exchange: " + exchange.getBody());
    }

    public void post4Form() {
        String url = HOST + POST_PARAM_URL;
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", "100");
        map.add("name", "post4Form");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);


        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, request, String.class);
        System.out.println("postForEntity: " + responseEntity.getBody());
    }
}
