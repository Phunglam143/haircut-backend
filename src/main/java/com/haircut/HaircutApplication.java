package com.haircut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.haircut")
@EntityScan("com.haircut.models")
@EnableJpaRepositories("com.haircut.repository")
public class HaircutApplication {
    public static ConfigurableApplicationContext mainContext() {
        return SpringApplication.run(HaircutApplication.class);
    }

    public static void main(String[] args) {
        mainContext();
    }
}
