package com.example.financeiro.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.financeiro.api.controle.configuracao.propriedade.FinanceiroApiPropriedade;

@SpringBootApplication
@EnableConfigurationProperties(FinanceiroApiPropriedade.class)
public class FinanceiroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApiApplication.class, args);
	}

}