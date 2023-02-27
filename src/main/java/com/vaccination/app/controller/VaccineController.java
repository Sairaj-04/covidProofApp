package com.vaccination.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vaccination.app.exception.VaccineException;
import com.vaccination.app.service.VaccineService;

@RestController
public class VaccineController {
	@Autowired
	private VaccineService vaccineService;
	
	//get vaccine id by vaccine center name
	@GetMapping("/getVaccId/{vname}")
	public Integer getIdByName(@PathVariable("vname") String name) throws VaccineException{
		
		return vaccineService.getIdByName(name);
	}
}
