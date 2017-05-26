package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {
    String url = "http://localhost:777/api/client";

    @Autowired
    RestTemplate restTemplate;

    public void checkRest() {
        //Map paramerters = new HashMap();
        //paramerters.put("id", 2);
        //restTemplate.delete(url + "/{id}", paramerters);
        Client c1 = new Client();
        c1.setName("Ivan Ivanov");
        c1.setPhoneNumber("+111111");
        c1.setBirthday("444444");
        c1.setPassport(4444444);
        c1.setOldPassport(7777);
        restTemplate.postForEntity(url, c1, Client.class);


    }
}
