package com.vaccination.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccination.app.Entity.AadharCard;
import com.vaccination.app.Entity.Appointment;
import com.vaccination.app.Entity.CurrentSession;
import com.vaccination.app.Entity.Dose;
import com.vaccination.app.Entity.Status;
import com.vaccination.app.Entity.User;
import com.vaccination.app.Entity.UserLogin;
import com.vaccination.app.Entity.Vaccine;
import com.vaccination.app.Entity.VaccineCenter;
import com.vaccination.app.exception.AadharException;
import com.vaccination.app.exception.DoseException;
import com.vaccination.app.exception.UserException;
import com.vaccination.app.exception.VaccineCenterException;
import com.vaccination.app.exception.VaccineException;
import com.vaccination.app.repository.AadharCardRepository;
import com.vaccination.app.repository.AppointmentRepository;
import com.vaccination.app.repository.CurrentSessionRepository;
import com.vaccination.app.repository.DoseRepository;
import com.vaccination.app.repository.UserRepository;
import com.vaccination.app.repository.VaccineCenterRepository;
import com.vaccination.app.repository.VaccineRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AadharCardRepository aadharCardRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private VaccineRepository vaccineRepository;

	@Autowired
	private VaccineCenterRepository vaccineCenterRepository;

	@Autowired
	private DoseRepository doseRepository;

	@Autowired
	private CurrentSessionRepository currentSessionRepository;

	// Register user Details
	@Override
	public User registerAnUser(User user, Long aadharNo) throws AadharException, UserException {
		Optional<AadharCard> op = aadharCardRepository.findById(aadharNo);
		User op1 = userRepository.findByMobile(user.getMobile());
		if (op.isPresent() || op1 != null) {
			throw new AadharException("AadharCard or Moblie is Already Registered!!");
		}
		AadharCard aadharCard = new AadharCard();
		aadharCard.setAadharNo(aadharNo);
		aadharCard.setMobile(user.getMobile());
		int age = Period.between(user.getDob(), LocalDate.now()).getYears();
		if (age >= 18)
			user.setAge(age);
		else
			throw new UserException("Applicant age must be at least 18 years");

		user.setAadharcard(aadharCard);
		aadharCardRepository.save(aadharCard);
		User registeredApplicant = userRepository.save(user);
		if (registeredApplicant != null) {
			return registeredApplicant;
		} else {
			throw new UserException("Registration failed! Please try again with valid credentials. :)");
		}
	}
	
	//Login user
	@Override
	public User loginUser(UserLogin userLogin) throws UserException {

		User user = userRepository.findByMobile(userLogin.getMobile());
		if (user == null) {
			throw new UserException("Please Enter a valid Mobile Number.");
		}

		Optional<CurrentSession> userSession = currentSessionRepository.findById(user.getId());

		if (userSession.isPresent()) {
			throw new UserException("User already Logged In with this number");
		}

		if (user.getDob().equals(userLogin.getDob())) {
			String key = RandomString.make(6);

			CurrentSession currentUserSession = new CurrentSession(user.getId(), key, LocalDateTime.now());

			currentSessionRepository.save(currentUserSession);

		} else
			throw new UserException("Please Enter a valid password");

		return user;
	}
	
	// Logout user
    @Override
	public User logoutUser(String mobile) throws UserException {

		CurrentSession userSession = currentSessionRepository.findByUuid(mobile);
		if (userSession == null) {
			throw new UserException("User Not Logged In with this number");
		}
		Optional<User> user = userRepository.findById(userSession.getUserId());
		currentSessionRepository.delete(userSession);
		if (!user.isPresent())
			throw new UserException("Register Admin Not found...please Resister");
		return user.get();
	}

	// get all user Details
	@Override
	public List<User> getAllIdCards() throws UserException {
		// TODO Auto-generated method stub
		List<User> list = userRepository.findAll();
		if (list.size() == 0) {
			throw new UserException("No Applicant Details");
		}
		return list;
	}

	// User Login
//	@Override
//	public User loginApplicant(String mobile, LocalDate dob) throws UserException {
//		User user = userRepository.findByMobAndDob(mobile, dob);
//		if (user != null) {
//			return user;
//		} else {
//			throw new UserException("Login failed. Incorrect mobile number or incorrect dob or both.");
//		}
//	}

	

	// delete user Details
	@Override
	public Boolean deleteUser(Integer id) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> opt = userRepository.findById(id);
		if (opt.isEmpty()) {
			throw new UserException("Applicant Id is not Correct");
		}

		User card = opt.get();
		userRepository.delete(card);
		return true;
	}

	// Get applicant by id
	@Override
	public User getUserById(Integer id) throws UserException {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			User existingUser = opt.get();
			return existingUser;
		} else {
			throw new UserException("No applicant found with this Id");
		}
	}

	// Update applicant details
	@Override
	public User updateUserDetails(User user) throws UserException {
		User updatedUserDetails = userRepository.save(user);
		if (updatedUserDetails != null) {
			return updatedUserDetails;
		} else {
			throw new UserException("No such applicant found." + user);
		}
	}

	// Apply for vaccination
	@Override
	public User applyForVaccination(Integer id, Integer vid, Integer vcid, Integer dose, Appointment appointment)
			throws DoseException, UserException, VaccineException, VaccineCenterException {
		if (dose == 0 || dose > 2) {
			throw new DoseException("Dose can be 1 or 2 !!!");
		}
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isEmpty()) {
			throw new UserException("Applicant Id is Not Correct");
		}
		User user = userOptional.get();
		if (user.getAge() < 18) {
			throw new UserException("Applicant Age is Less Than 18");
		}
		List<Dose> doses = user.getDoses();
		if (doses.size() >= 2) {
			throw new DoseException("Both the Doses Already Taken");
		} else if (doses.size() == 1) {

			if (dose == 1) {
				throw new DoseException("First Dose Already Taken");
			}
			Dose tempDose = doses.get(0);
			if (tempDose.getDoseStatus().equals(Status.PENDING.toString())) {
				throw new DoseException("First Dose is PENDING!! You cant apply for Second");
			}
		} else if (dose == 2 && doses.size() == 0) {
			throw new DoseException("Dose 1 not taken!! you cant apply for dose 2...");
		}

		Optional<Vaccine> vaccOptional = vaccineRepository.findById(vid);
		if (vaccOptional.isEmpty()) {
			throw new VaccineException("Vaccine is Not Available");
		}
		Vaccine vaccine = vaccOptional.get();

		Optional<VaccineCenter> vsOptional = vaccineCenterRepository.findById(vcid);
		if (vsOptional.isEmpty()) {
			throw new VaccineCenterException("VaccineCenter is Not Available");
		}
		VaccineCenter vaccineCenter = vsOptional.get();

		Dose doseObj = new Dose();
		doseObj.setAppointment(appointment);
		doseObj.setCenter(vaccineCenter);
		doseObj.setDoseCount(dose);
		doseObj.setVaccine(vaccine);
//		doseObj.setIdCard(idCard);
		doseObj.setDoseStatus(Status.PENDING.toString());

		doses.add(doseObj);
		appointment.setBookingStatus(Status.COMPLETED.toString());

		// Validation of slot availability
		List<Dose> dosesOfCenter = doseRepository.findByCenter(vaccineCenter);
		for (Dose d : dosesOfCenter) {
			Appointment app = d.getAppointment();
			String status = d.getDoseStatus();
			LocalDate date = app.getDate();
			String slot = app.getSlot();
			if (date.toString().equals(appointment.getDate().toString())) {
				if (status.equals(Status.PENDING.toString())) {
					if (slot.equals(appointment.getSlot())) {
						throw new DoseException("Slot Already Booked!!");
					}
				}
			}
		}
		userRepository.save(user);

		return user;
	}

	// Checking vaccination status
	@Override
	public List<String> getVaccinationStatus(String mobile) throws UserException {

		// Written this method assuming only 2 doses will be provided to user
		// if we consider total 3 doses then this method should be changed a little.

		User existingUser = userRepository.findByMobile(mobile);
		if (existingUser != null) {
			String name = existingUser.getName();
			List<Dose> doses = existingUser.getDoses();

			Dose[] dosesArr = doses.toArray(new Dose[doses.size()]);

			List<String> list = new ArrayList<>();

			for (int i = 0; i < doses.size(); i++) {
				int doseCount = dosesArr[i].getDoseCount();
				String doseStatus = dosesArr[i].getDoseStatus();

				String vaccineName = dosesArr[i].getVaccine().getName();

				String str = "UserName: " + name + ", DoseCount: " + doseCount + ", DoseStatus: " + doseStatus
						+ ", VaccineName: " + vaccineName;

				list.add(str);
			}

			return list;
		} else {
			throw new UserException("No user found with this moble number: " + mobile);
		}
	}

	// Canceling appointment
	@Override
	public String cancelAppointment(Integer id) throws UserException, DoseException {
		Optional<Dose> opt = doseRepository.findById(id);
		if (opt.isEmpty()) {
			throw new DoseException("Dose id is Invalid");
		}
		Dose dose = opt.get();
		Appointment ap = dose.getAppointment();
		doseRepository.delete(dose);
		appointmentRepository.delete(ap);
		return "Appointment Cancel Success full";
	}

	// If user wants to know about all kinds of available vaccines
	@Override
	public List<Vaccine> getAllVaccineDetails() throws VaccineException {
		List<Vaccine> vaccineList = vaccineRepository.findAll();
		if (vaccineList.isEmpty()) {
			throw new VaccineException("No vaccine details available for now.");
		} else {
			return vaccineList;
		}
	}

	// Change slot for appointment
	@Override
	public Appointment changeSlot(Appointment appointment) throws UserException {
		Optional<Appointment> opt = appointmentRepository.findById(appointment.getBookingid());
		if (opt.isEmpty()) {
			throw new UserException("Booking ID of Appointment is Not Correct");
		}
		return appointmentRepository.save(appointment);
	}

	@Override
	public CurrentSession userSession(String mobile) throws UserException {
		User user = userRepository.findByMobile(mobile);
		if (user == null)
			throw new UserException("Please Enter a valid Mobile Number, this mobile is not registered");

		Optional<CurrentSession> userSession = currentSessionRepository.findById(user.getId());
		if (!userSession.isPresent())
			throw new UserException("Please Login First");
		return userSession.get();
	}
}
