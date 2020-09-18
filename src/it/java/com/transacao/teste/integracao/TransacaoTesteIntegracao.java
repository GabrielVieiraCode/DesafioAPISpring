package com.transacao.teste.integracao;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.transacao.model.Transacao;
import com.transacao.service.TransacaoService;


@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransacaoTesteIntegracao {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Test
	@Order(1)
	public void testePostTransacao1() {
	Transacao transacao1 = new Transacao();
	transacao1.setValor(new BigDecimal (25.55));
	transacao1.setDataHora((LocalDateTime.parse("2020-09-07T21:07:40.603")));
	transacao1.setDataHoraEnvio(LocalDateTime.now());

	transacaoService.cadastrar(transacao1);
	
	ResponseEntity<String> responseEntity = this.restTemplate
			.postForEntity("http://localhost:" + port + "/transacao", transacao1, String.class);
	
	assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(2)
	public void testePostTransacao2() {
	Transacao transacao2 = new Transacao();
	transacao2.setValor(new BigDecimal (25.55));
	transacao2.setDataHora((LocalDateTime.parse("2020-09-07T21:07:40.603")));
	transacao2.setDataHoraEnvio(LocalDateTime.now());

	transacaoService.cadastrar(transacao2);
	
	ResponseEntity<String> responseEntity = this.restTemplate
			.postForEntity("http://localhost:" + port + "/transacao", transacao2, String.class);
	
	assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(3)
	public void testeGetAllTransacao() {
		ResponseEntity<String> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/transacao", String.class);
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	
	@Test
	@Order(4)
	public void testeEstatisticas() {
		ResponseEntity<String> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/estatistica", String.class);
		
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	

}
