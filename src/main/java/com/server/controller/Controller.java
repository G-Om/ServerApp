package com.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/server")
public class Controller {

    @GetMapping("/records")
    public HashMap getRecords(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Om", "student");
        map.put("surya", "employee");
        map.put("PS", "faculty");
        return map;
    }
}
