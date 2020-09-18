package com.transacao.api.exceptionhandler;

import java.util.List;


public class Problema {
private String erro;
private List<Campo> campos;
 
public static class Campo{
	private String nome;
	private String mensagem;
	
	
	public Campo(String nome, String mensagem) {
		super();
		this.nome = nome;
		this.mensagem = mensagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	}


public String getErro() {
	return erro;
}
public void setErro(String erro) {
	this.erro = erro;
}
public List<Campo> getCampos() {
	return campos;
}
public void setCampos(List<Campo> campos) {
	this.campos = campos;
}




}
