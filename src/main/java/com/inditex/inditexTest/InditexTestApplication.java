package com.inditex.inditexTest;

import com.inditex.inditexTest.infraestructure.external.conf.ExternalProductServiceConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableConfigurationProperties(ExternalProductServiceConf.class)
@EnableRedisRepositories
public class InditexTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InditexTestApplication.class, args);
	}

}
