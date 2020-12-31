package com.purcotton.promotion.mq;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan("com.purcotton.*")
/*@MapperScan("com.purcotton.*.mapper")*/
@EnableTransactionManagement
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest
{

}
