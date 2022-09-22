package com.daniloalalmeida.estudosjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EstudosjavaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EstudosjavaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {}

}
