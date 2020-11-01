package com.cherniak.geek.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeekMarketApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeekMarketApplication.class, args);
    // Домашнее задание:
    // 1. Личный кабинет пользователя с отображением профиля (имя, фамилия, телефон, email, год рождения, пол, город проживания)
    // 2. Дать возможность изменять профиль (с подтверждением паролем)
  }
}
