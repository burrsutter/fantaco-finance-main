package com.fantaco.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")

public class TestController {
    int count = 0;
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        count++;
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Fantaco Finance API");
        response.put("count", count);
        response.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
