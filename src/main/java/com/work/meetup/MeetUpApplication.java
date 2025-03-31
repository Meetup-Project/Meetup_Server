package com.work.meetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MeetUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetUpApplication.class, args);
    }

}
