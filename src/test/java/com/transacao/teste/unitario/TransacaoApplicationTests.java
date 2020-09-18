package com.transacao.teste.unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.transacao.model.Transacao;
import com.transacao.service.EstatisticaService;
import com.transacao.service.TransacaoService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class TransacaoApplicationTests {

	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private EstatisticaService estatisticaService;
	
	private static ValidatorFactory validatorFactory;
    private static Validator validator;
	
	@BeforeAll
	public void setUp() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
		System.out.println("Iniciando os testes");
		
	}
	
	@Test
	@Order(1)
	public void transacaoServiceNotNull(){
		assertNotNull(transacaoService);
	}
	
	@Test
	@Order(2)
	public void estatisticaServiceNotNull() {
		assertNotNull(estatisticaService);
	}
	
	@Test
	@Order(3)
	public void testeTransacaoCriada() throws Exception{
		
		Transacao transacao1 = new Transacao();
		transacao1.setValor(new BigDecimal (25.55));
		transacao1.setDataHora((LocalDateTime.parse("2021-09-07T21:07:40.603")));
		transacao1.setDataHoraEnvio(LocalDateTime.now());

		transacaoService.cadastrar(transacao1);
		
		
		assertNotNull(transacao1);
		assertEquals(new BigDecimal(25.55),transacao1.getValor());
		assertEquals(LocalDateTime.parse("2021-09-07T21:07:40.603"), transacao1.getDataHora());
	}
		
		@Test
		@Order(4)
		public void testeCalculaEstatisticas() throws Exception {
			transacaoService.delete();
			
			Transacao transacao1 = new Transacao();
			transacao1.setValor(new BigDecimal (24.98));
			transacao1.setDataHora((LocalDateTime.parse("2020-09-07T21:07:40.603")));
			transacao1.setDataHoraEnvio(LocalDateTime.now());

			transacaoService.cadastrar(transacao1);
			
			Transacao transacao2 = new Transacao();		
			transacao2.setValor(new BigDecimal (175.44));
			transacao2.setDataHora((LocalDateTime.parse("2020-09-05T17:15:25.431")));
			transacao2.setDataHoraEnvio(LocalDateTime.now());
			
			transacaoService.cadastrar(transacao2);
			
			estatisticaService.estatisticas();
			
			assertNotNull(estatisticaService);
			assertEquals("2", estatisticaService.estatistica.getCount().toString());
			assertEquals("200.42", estatisticaService.estatistica.getSum().toString());
			assertEquals("100.21", estatisticaService.estatistica.getAvg().toString());
			assertEquals("24.98", estatisticaService.estatistica.getMin().toString());
			assertEquals("175.44", estatisticaService.estatistica.getMax().toString());
		}
		
		@Test
		@Order(5)
		public void testeDeletaListaTransacao() throws Exception{
			
			Transacao transacao1 = new Transacao();
			transacao1.setValor(new BigDecimal (25.55));
			transacao1.setDataHora((LocalDateTime.parse("2020-09-07T21:07:40.603")));
			transacao1.setDataHoraEnvio(LocalDateTime.now());

			transacaoService.cadastrar(transacao1);
			
			transacaoService.delete();
			assertEquals("<200 OK OK,[],[]>", transacaoService.listaTransacao().toString());
		}
		
		@Test
		@Order(6)
		public void testeValidacoes() throws Exception{
			
			Transacao transacao1 = new Transacao();
			transacao1.setValor(new BigDecimal (25.55));
			transacao1.setDataHora((LocalDateTime.parse("2020-09-09T22:47:40.603")));
			transacao1.setDataHoraEnvio(LocalDateTime.now());

			transacaoService.cadastrar(transacao1);
			
			Set<ConstraintViolation<Transacao>> violations = validator.validate(transacao1);
		
			  if (violations.isEmpty()) {
			        System.out.println("Transacao Válida, atende todos os parâmetros");
			    } else {
			        for (ConstraintViolation<Transacao> violation : violations) {
			            System.out.println("Transacao Inválida");
			            System.out.println("Erro no campo: "
			                 + violation.getPropertyPath());
			            System.out.println("Dado informado para teste: "
			                 + violation.getInvalidValue());
			            System.out.println("Erro: "
			                 + violation.getMessage());

			        }
			        throw new ConstraintViolationException(violations);
			    }
		}
		
		
	@AfterAll
	public void deletandoListaTransacao() {
		transacaoService.delete();
		validatorFactory.close();
		System.out.println("Testes finalizados");
	}
	


}
