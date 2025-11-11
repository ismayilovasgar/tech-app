package com.ismayilov.techapp;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TechAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechAppApplication.class, args);


    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> userService.createSampleUser();
//    }

}
