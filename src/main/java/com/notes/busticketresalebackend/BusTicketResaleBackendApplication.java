package com.notes.busticketresalebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BusTicketResaleBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                BusTicketResaleBackendApplication.class,
                args
        );
    }
}