package com.transacao.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

public class Transacao {
	
	@NotNull
	@PositiveOrZero
	private BigDecimal valor;

	@NotNull
	@Past
	private LocalDateTime dataHora;
	
	private LocalDateTime dataHoraEnvio = LocalDateTime.now();

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public LocalDateTime getDataHoraEnvio() {
		return dataHoraEnvio;
	}

	public void setDataHoraEnvio(LocalDateTime dataHoraTransacao) {
		this.dataHoraEnvio = dataHoraTransacao;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
