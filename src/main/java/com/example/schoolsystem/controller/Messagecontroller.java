package com.example.schoolsystem.controller;


import com.example.schoolsystem.repository.Studentrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class Messagecontroller {
    private final Studentrepo studentrepo;

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/send")
    public ResponseEntity sendSMS(List<Long> studentId){
        String url = "http://100.84.198.43:8080/send-sms";

        Map<String,Object> body = new HashMap<>();

        body.put("phone","916260905418");
        body.put("message","you fees is pending");
        restTemplate.postForEntity(url,body,String.class);
        return ResponseEntity.ok("successfully send message");
    }

}
