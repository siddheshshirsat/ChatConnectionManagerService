package com.chat.connectionmanager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServiceStarter {

    public static void main(String[] args) {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("server.port", "9000");
        
        SpringApplication app = new SpringApplication(ServiceStarter.class);
        app.setDefaultProperties(propertiesMap);
        app.run(args);
    }
}
