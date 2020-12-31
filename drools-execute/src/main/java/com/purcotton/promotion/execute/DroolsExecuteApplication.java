package com.purcotton.promotion.execute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},scanBasePackages = "com.purcotton.promotion.core")
public class DroolsExecuteApplication {
    public static void main(String[] args) {
        SpringApplication.run(DroolsExecuteApplication.class, args);
    }
}

