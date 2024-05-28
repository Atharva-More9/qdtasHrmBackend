package com.qdtas.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EmailAlreadyRegisteredException.class)
	public ResponseEntity<ErrorDetails> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception,WebRequest webrequest){
		ErrorDetails errordetails=new ErrorDetails(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errordetails,HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest webrequest){
		ErrorDetails errordetails=new ErrorDetails(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errordetails,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException exception,WebRequest webrequest){
         ErrorDetails errordetails=new ErrorDetails(new Date(),exception.getMessage(),webrequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errordetails,HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> mp=new HashMap<>();
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		
		errors.forEach((error)->{
			String fieldName=((FieldError) error).getField();
			String message=error.getDefaultMessage();
			mp.put(fieldName, message);
		 						}			  
					  );
		return new ResponseEntity<Object>(mp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,WebRequest webRequest){
		ErrorDetails er= new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.FORBIDDEN);
	}

}