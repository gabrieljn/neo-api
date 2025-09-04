package com.neo.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getFieldErrors().forEach(error -> {

			String field = error.getField();
			errors.put("erro", "Campo '" + field + "' não foi informado.");

		});

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException e) {
		Map<String, String> error = new HashMap<>();
		error.put("erro", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Map<String, String>> handleNoSuchElement(NoSuchElementException e) {
		Map<String, String> error = new HashMap<>();
		error.put("erro", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Map<String, String>> handleInvalidPasswordException(InvalidPasswordException e) {
		Map<String, String> error = new HashMap<>();
		error.put("erro", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
		Map<String, String> error = new HashMap<>();
		error.put("erro", "Erro interno no servidor: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
