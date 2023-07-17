package com.server.controller;

import com.server.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/server")
public class Controller {

    @Autowired
    JwtService service;
    @GetMapping("/records")
    public HashMap getRecords(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Om", "student");
        map.put("surya", "employee");
        map.put("PS", "faculty");
        return map;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello Welcome from secured endpoint.");
    }

    @GetMapping("/generate")
    public String genToken(){
        return service.generateToken();
    }
}
