package com.vaccination.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccination.app.Entity.Admin;
import com.vaccination.app.Entity.AdminLoginDTO;
import com.vaccination.app.Entity.AdminPasswordDTO;
import com.vaccination.app.Entity.User;
import com.vaccination.app.Entity.Vaccine;
import com.vaccination.app.Entity.VaccineCenter;
import com.vaccination.app.exception.AdminException;
import com.vaccination.app.exception.UserException;
import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.exception.VaccineException;
import com.vaccination.app.service.AdminService;
import com.vaccination.app.service.UserService;
import com.vaccination.app.service.VaccineCenterService;
import com.vaccination.app.service.VaccineService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VaccineCenterService vaccineCenterService;

	@Autowired
	private UserService userService;
	
	// Admin login
	@PostMapping("/login")
	public Admin loginAdmin(@Valid @RequestBody AdminLoginDTO adminLoginDTO) throws AdminException {
		return adminService.loginAdmin(adminLoginDTO);
	}
	
	// Admin logout
	@PostMapping("/logout/{mob}")
	public Admin logoutAdmin(@Valid @PathVariable("mob") String mobile) throws AdminException {
		return adminService.logoutAdmin(adminService.adminSession(mobile).getUuid());
	}
	
	// Admin register
	@PostMapping("/register")
	public Admin registerAdmin(@Valid @RequestBody Admin a) throws AdminException {
		return adminService.registerAdmin(a);
	}
	
	// Update admin
	@PutMapping("/update")
	public Admin updatePassword(@Valid @RequestBody AdminPasswordDTO aPassDto) throws AdminException {
		return adminService.updatePassword(aPassDto);
	}
	
	// get all vaccine details
	@GetMapping("/vaccine")
	public List<Vaccine> getAllVaccine() throws VaccineException {
		return vaccineService.getAllVaccine();
	}
	
	// get all vaccine center details
	@GetMapping("/vaccinecenter")
	public List<VaccineCenter> getAllVaccineCenter() throws VaccineCenterException {
		return vaccineCenterService.getAllVaccineCenter();
	}
	
	// add vaccine
	@PostMapping("/vaccine/{mob}")
	public Vaccine addVaccine(@Valid @RequestBody Vaccine v, @PathVariable("mob") String mobile) throws AdminException, VaccineException {
//		//      adminService.adminSession(mobile);
		return vaccineService.addVaccine(v);
	}

	// add vaccine center
	@PostMapping("/vaccinecenter/{mob}")
	public VaccineCenter addVaccineCenter(@Valid @RequestBody VaccineCenter vc, @PathVariable("mob") String mobile) throws AdminException, VaccineCenterException {
//		//      adminService.adminSession(mobile);
		return vaccineCenterService.addVaccineCenter(vc);
	}
	
	// delete vaccine
	@DeleteMapping("/vaccine/{mob}/{id}")
	public Vaccine deleteVaccine(@Valid @PathVariable("mob") String mobile, @PathVariable("id") Integer id) throws AdminException, VaccineException {
		//      adminService.adminSession(mobile);
		return vaccineService.deleteVaccine(id);
	}

	// delete vaccine center
	@DeleteMapping("/vaccinecenter/{mob}/{id}")
	public VaccineCenter deleteVaccineCenter(@Valid @PathVariable("mob") String mobile, @PathVariable("id") Integer id) throws AdminException, VaccineCenterException {
		//      adminService.adminSession(mobile);
		return vaccineCenterService.deleteVaccineCenter(id);
	}
	
	// get all Applicant details
	@GetMapping("/idcards")
	public List<User> getAllCards() throws UserException {
		return userService.getAllIdCards();
	}

	// delete applicant details
	@DeleteMapping("/idcard/{mob}/{id}")
	public Boolean deleteCard(@Valid @PathVariable("mob") String mobile, @PathVariable("id") Integer id) throws AdminException, UserException {
		//      adminService.adminSession(mobile);
		return userService.deleteUser(id);
	}

}
