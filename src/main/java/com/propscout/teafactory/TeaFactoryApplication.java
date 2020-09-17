package com.propscout.teafactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeaFactoryApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TeaFactoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Command Line Run Called");
    }
}
