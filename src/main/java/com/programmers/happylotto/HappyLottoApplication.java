package com.programmers.happylotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HappyLottoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyLottoApplication.class, args);
    }

}
