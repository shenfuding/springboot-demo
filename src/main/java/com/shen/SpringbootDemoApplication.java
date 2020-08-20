package com.shen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author shenfuding
 */
@ComponentScan(basePackages = {"com.shen.*"})
@MapperScan("com.shen.*.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemoApplication.class, args);
	}

}
