package com.transacao.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transacao.model.Estatistica;

@Service
public class EstatisticaService {

	@Autowired
	private TransacaoService transacaoService;

	public Estatistica estatistica = new Estatistica();

	private static final int segundos = 60;

	public synchronized ResponseEntity<Estatistica> estatisticas() {

		LocalDateTime horaAtual = LocalDateTime.now().minusSeconds(segundos);

		estatistica.setCount(BigDecimal.valueOf(transacaoService.transacaoList.stream()
				.filter(transacao -> transacao.getDataHoraEnvio().isAfter(horaAtual)).count()));

		estatistica.setSum(BigDecimal
				.valueOf(transacaoService.transacaoList.stream()
						.filter(transacao -> transacao.getDataHoraEnvio().isAfter(horaAtual))
						.mapToDouble(transacao -> transacao.getValor().doubleValue()).sum())
				.setScale(2, RoundingMode.HALF_EVEN));

		estatistica.setAvg(BigDecimal
				.valueOf(transacaoService.transacaoList.stream()
						.filter(transacao -> transacao.getDataHoraEnvio().isAfter(horaAtual))
						.mapToDouble(transacao -> transacao.getValor().doubleValue()).average().orElse(0.0))
				.setScale(2, RoundingMode.HALF_EVEN));

		estatistica.setMin(BigDecimal
				.valueOf(transacaoService.transacaoList.stream()
						.filter(transacao -> transacao.getDataHoraEnvio().isAfter(horaAtual))
						.mapToDouble(transacao -> transacao.getValor().doubleValue()).min().orElse(0.0))
				.setScale(2, RoundingMode.HALF_EVEN));

		estatistica.setMax(BigDecimal
				.valueOf(transacaoService.transacaoList.stream()
						.filter(transacao -> transacao.getDataHoraEnvio().isAfter(horaAtual))
						.mapToDouble(transacao -> transacao.getValor().doubleValue()).max().orElse(0.0))
				.setScale(2, RoundingMode.HALF_EVEN));

		return ResponseEntity.ok(estatistica);
	}
}
