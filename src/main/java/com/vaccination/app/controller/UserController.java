package com.vaccination.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaccination.app.Entity.Appointment;
import com.vaccination.app.Entity.User;
import com.vaccination.app.Entity.UserLogin;
import com.vaccination.app.exception.AadharException;
import com.vaccination.app.exception.DoseException;
import com.vaccination.app.exception.UserException;
import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.exception.VaccineException;
import com.vaccination.app.repository.DoseRepository;
import com.vaccination.app.service.AadharcardService;
import com.vaccination.app.service.UserService;
import com.vaccination.app.service.VaccineCenterService;
import com.vaccination.app.service.VaccineService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private VaccineService vaccineService;

	@Autowired
	private VaccineCenterService vaccineCenterService;

	@Autowired
	private AadharcardService aadharcardService;

	@Autowired
	private DoseRepository doseRepository;

	// Register applicant
	@PostMapping("/register/{adno}")
	public User registerApplicant(@Valid @RequestBody User user, @PathVariable("adno") Long adno)throws AadharException, UserException {
		return userService.registerAnUser(user, adno);
	}

	// Applicant login
	@PostMapping("/login")
	public User loginApplicant(@Valid @RequestBody UserLogin userLogin) throws UserException {
		return userService.loginUser(userLogin);
	}

	// User logout
//	@PostMapping("/logout/{mob}")
//	public User logoutUser(@Valid @PathVariable("mob") String mobile) throws UserException {
//		return userService.logoutUser(userService.userSession(mobile).getUuid());
//	}

	// book appointment
	@PostMapping("/vaccination/{id}/{vid}/{vcid}/{dose}")
	public User applyForVaccination(@Valid @RequestBody Appointment appointment, @PathVariable("id") Integer id,
			@PathVariable("vid") Integer vid, @PathVariable("vcid") Integer vcid, @PathVariable("dose") Integer dose)
			throws UserException, DoseException, VaccineException, VaccineCenterException {
//		userService.userSession(appointment.getMobile());
		return userService.applyForVaccination(id, vid, vcid, dose, appointment);
	}

	// Cancel appointment
	@DeleteMapping("/vaccination/{doseID}")
	public String cancelAppointment(@Valid @PathVariable("doseID") Integer id) throws UserException, DoseException {
//		userService.userSession();
//		Dose dose = doseRepository.getById(id);
//		userService.userSession(dose.getAppointment().getMobile());
		return userService.cancelAppointment(id);
	}

	// update slot for appointment
	@PutMapping("/vaccination")
	public Appointment updateSlot(@Valid @RequestBody Appointment appointment) throws UserException {
//		userService.userSession(appointment.getMobile());
		return userService.changeSlot(appointment);
	}


}
