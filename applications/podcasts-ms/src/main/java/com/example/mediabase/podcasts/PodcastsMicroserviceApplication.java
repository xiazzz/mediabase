package com.example.mediabase.podcasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PodcastsMicroserviceApplication {
    public static void main(String... args) {
        SpringApplication.run(PodcastsMicroserviceApplication.class, args);
    }
}
