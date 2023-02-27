package com.vaccination.app.service;

import java.time.LocalDate;
import java.util.List;

import com.vaccination.app.Entity.Appointment;
import com.vaccination.app.Entity.CurrentSession;
import com.vaccination.app.Entity.User;
import com.vaccination.app.Entity.UserLogin;
import com.vaccination.app.Entity.Vaccine;
import com.vaccination.app.exception.AadharException;
import com.vaccination.app.exception.AdminException;
import com.vaccination.app.exception.DoseException;
import com.vaccination.app.exception.UserException;
import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.exception.VaccineException;

public interface UserService {

	// Registering an Applicant
	// Parameter --> IdCard object
	// Return --> Registered Applicant
	public User registerAnUser(User user,Long adno) throws AadharException, UserException;
	
	//get all Applicant Details
	public List<User> getAllIdCards() throws UserException;
		
	// Applicant Login
//	public User loginApplicant(String mobile, LocalDate dob) throws UserException;
	
	//delete Applicant Details
	public Boolean deleteUser(Integer id) throws UserException;
	
    // Get applicant by id
	public User getUserById(Integer id) throws UserException;
	
	// Update applicant details
	public User updateUserDetails(User user) throws UserException;
	
	//Apply for vaccination
		public User applyForVaccination(Integer id,Integer vid,Integer vcid,Integer dose,Appointment appointment) throws DoseException, UserException, VaccineException, VaccineCenterException;
	
	// Checking vaccination status
	public List<String> getVaccinationStatus(String mobile) throws UserException;
	
	// Canceling appointment
	public String cancelAppointment(Integer id) throws UserException, DoseException;
	
	// Change slot for appointment
	public Appointment changeSlot(Appointment appointment) throws UserException;
	
	// If applicant wants to know about all kinds of available vaccines
	public List<Vaccine> getAllVaccineDetails() throws VaccineException;

	public CurrentSession userSession(String mobile) throws UserException;

	// Applicant Login
	public User loginUser(UserLogin userLogin) throws UserException;

	public User logoutUser(String mobile) throws UserException;
	
	
}
