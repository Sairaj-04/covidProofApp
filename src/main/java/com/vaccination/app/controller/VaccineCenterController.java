package com.vaccination.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.service.VaccineCenterService;

@RestController
public class VaccineCenterController {
	@Autowired
	private VaccineCenterService vaccineCenterService;
	
	//get vaccine center id by vaccine center name
	@GetMapping("/getVaccCentId/{vcname}")
	public Integer getIdByName(@PathVariable("vcname") String name) throws VaccineCenterException {

		return vaccineCenterService.getVCIdByName(name);
	}
}
