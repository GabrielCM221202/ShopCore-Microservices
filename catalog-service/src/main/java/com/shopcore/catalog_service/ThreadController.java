package com.shopcore.catalog_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadController {

    private static final Logger log = LoggerFactory.getLogger(ThreadController.class);

    @GetMapping("/api/thread-info")
    public String getThreadInfo() {
        String threadInfo = Thread.currentThread().toString();
        log.info("Petición atendida por: {}", threadInfo);
        return "Petición atendida por: " + threadInfo;
    }
}