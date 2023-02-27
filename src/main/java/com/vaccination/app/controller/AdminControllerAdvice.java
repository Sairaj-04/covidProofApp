package com.vaccination.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vaccination.app.exception.AdminException;
import com.vaccination.app.exception.UserException;
import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.exception.VaccineException;

@RestControllerAdvice
public class AdminControllerAdvice {
	
	@ExceptionHandler(AdminException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleAdminException(AdminException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(VaccineException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleVaccineException(VaccineException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(VaccineCenterException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleVaccineCenterException(VaccineCenterException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(UserException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleUserException(UserException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}


//@RestControllerAdvice
//public class AppointmentControllerAdvice {
//	@ExceptionHandler(AppointmentException.class)
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public String handleAppointmentException(AppointmentException e) {
//
//		return e.getMessage();
//	}
//	
//	
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//		return errors;
//	}