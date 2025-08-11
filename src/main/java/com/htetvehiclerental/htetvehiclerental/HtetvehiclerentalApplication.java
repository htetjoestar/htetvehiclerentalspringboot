package com.htetvehiclerental.htetvehiclerental;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtetvehiclerentalApplication {


    public static void main(String[] args) {
        System.setProperty("spring.datasource.url", System.getenv("SPRING_DATASOURCE_URL"));
        System.setProperty("spring.datasource.username", System.getenv("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("spring.datasource.password", System.getenv("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("spring.mail.username", System.getenv("SPRING_MAIL_USERNAME"));
        System.setProperty("spring.mail.password", System.getenv("SPRING_MAIL_PASSWORD"));

        SpringApplication.run(HtetvehiclerentalApplication.class, args);
    }

}