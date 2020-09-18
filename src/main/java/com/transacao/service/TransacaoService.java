package com.transacao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.transacao.model.Transacao;

@Service
public class TransacaoService {

	public List<Transacao> transacaoList = new ArrayList<Transacao>();

	public synchronized void cadastrar(Transacao transacao) {
		transacaoList.add(transacao);
	}

	public synchronized void delete() {
		transacaoList.clear();
	}

	public synchronized ResponseEntity<List<Transacao>> listaTransacao() {
		return ResponseEntity.ok(transacaoList);
	}

}
