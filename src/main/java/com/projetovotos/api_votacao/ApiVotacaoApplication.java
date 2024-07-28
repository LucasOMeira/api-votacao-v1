package com.projetovotos.api_votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.projetovotos.api_votacao"})
@EnableCaching
public class ApiVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}

}
