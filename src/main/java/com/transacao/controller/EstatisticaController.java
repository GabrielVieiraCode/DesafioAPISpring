package com.transacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transacao.model.Estatistica;
import com.transacao.service.EstatisticaService;
import com.transacao.service.TransacaoService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/transacao")
public class EstatisticaController {

	@Autowired
	private EstatisticaService estatisticaService;

	@Autowired
	TransacaoService transacaoService;

	@GetMapping("/estatistica")
	public synchronized ResponseEntity<Estatistica> getEstatistica() {
		return estatisticaService.estatisticas();
	}

}
