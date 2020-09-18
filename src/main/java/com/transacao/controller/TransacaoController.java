package com.transacao.controller;

import com.transacao.model.Transacao;
import com.transacao.service.TransacaoService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@CrossOrigin("*")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;
	
	//Metodo para cadastrar uma transaçao
	@PostMapping
	public synchronized ResponseEntity<Transacao> criarTransacao(@Valid @RequestBody Transacao transacao) {
		transacaoService.cadastrar(transacao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//Metodo para buscar as transaçoes
	@GetMapping
	public synchronized ResponseEntity<List<Transacao>> listarTransacao() {
		return transacaoService.listaTransacao();
	}
	
	//Metodo para deletar a transaçao
	@DeleteMapping
	public synchronized void apagarTransacao() {
		transacaoService.delete();
	}

}
