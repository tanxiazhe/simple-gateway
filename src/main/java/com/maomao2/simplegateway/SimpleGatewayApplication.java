package com.maomao2.simplegateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan

public class SimpleGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SimpleGatewayApplication.class).properties("server.port=8080").run(args);
    }
}
