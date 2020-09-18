package com.transacao.api.exceptionhandler;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
 
		Problema problema = new Problema();
		ArrayList<Problema.Campo> campos = new ArrayList<Problema.Campo>();
		
		for(ObjectError error:ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField() ;
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		problema.setErro("Um ou mais campos encontra-se inválido, favor conferir");
		problema.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problema, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
}
