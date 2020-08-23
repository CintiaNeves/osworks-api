package br.com.osworks.osworksapi.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.osworks.osworksapi.domain.exception.BusinessException;
import br.com.osworks.osworksapi.domain.exception.EntityNotFoundException;
import br.com.osworks.osworksapi.exceptionhandler.Exception.Field;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Field> fields = new ArrayList<Exception.Field>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			String name = ((FieldError) error).getField();
			String msg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new Exception.Field(name, msg));
			
		}
		
		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setTitulo("Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento e tente novamente.");
		exception.setDataHora(OffsetDateTime.now());
		exception.setFields(fields);
		
		return super.handleExceptionInternal(ex, exception, headers, status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handlerNegocio(BusinessException ex, WebRequest request) {
		
		HttpStatus status = BAD_REQUEST;
		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setTitulo(ex.getMessage());
		exception.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handlerEntityNotFound(BusinessException ex, WebRequest request) {
		
		HttpStatus status = NOT_FOUND;
		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setTitulo(ex.getMessage());
		exception.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
	}
}
