package com.purcotton.promotion.mq.message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},scanBasePackages = "com.purcotton.promotion")
public class DroolsMqConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroolsMqConsumerApplication.class, args);
    }
}

