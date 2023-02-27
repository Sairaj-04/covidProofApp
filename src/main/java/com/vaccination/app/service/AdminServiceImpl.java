package com.vaccination.app.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccination.app.Entity.Admin;
import com.vaccination.app.Entity.AdminLoginDTO;
import com.vaccination.app.Entity.AdminPasswordDTO;
import com.vaccination.app.Entity.CurrentSession;
import com.vaccination.app.exception.AdminException;
import com.vaccination.app.repository.AdminRepository;
import com.vaccination.app.repository.CurrentSessionRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CurrentSessionRepository currentSessionRepository;

	// Admin Registration
	@Override
	public Admin registerAdmin(Admin admin) throws AdminException {
		// TODO Auto-generated method stub
		Admin admin3 = adminRepository.findByMobile(admin.getMobile());
		if (admin3 != null)
			throw new AdminException("Mobile Already Registered");
		Admin admin2 = adminRepository.save(admin);
		if (admin2 == null) {
			throw new AdminException("Enter Valid Moblie Number or Password.");
		}
		return admin2;
	}

	// Admin Login Using Admin DTO for security purposes
	// Admin Session is also checked
	@Override
	public Admin loginAdmin(AdminLoginDTO adminLoginDTO) throws AdminException {

		Admin admin = adminRepository.findByMobile(adminLoginDTO.getMobile());
		if (admin == null) {
			throw new AdminException("Please Enter a valid Mobile Number.");
		}

		Optional<CurrentSession> adminSession = currentSessionRepository.findById(admin.getAdminId());

		if (adminSession.isPresent()) {
			throw new AdminException("Admin already Logged In with this number");
		}
		
		if (admin.getPassword().equals(adminLoginDTO.getPassword())) {
			String key = RandomString.make(6);

			CurrentSession currentUserSession = new CurrentSession(admin.getAdminId(), key, LocalDateTime.now());

			currentSessionRepository.save(currentUserSession);

		} else
			throw new AdminException("Please Enter a valid password");

		return admin;
	}

	// Admin Update Password Using AdminPasswordDTO for security purposes
	@Override
	public Admin updatePassword(AdminPasswordDTO adminPasswordDTO) throws AdminException {

		Admin admin = adminRepository.findByMobile(adminPasswordDTO.getMobile());
		if (admin == null) {
			throw new AdminException("Please Enter a valid Mobile Number");
		}
		Optional<CurrentSession> adminSession = currentSessionRepository.findById(admin.getAdminId());

		if (adminSession.isPresent()) {
			if (admin.getPassword().equals(adminPasswordDTO.getOldPassword())) {
				admin.setPassword(adminPasswordDTO.getNewPassword());
			} else
				throw new AdminException("Please Enter a valid Old Password");
		} else
			throw new AdminException("Please Login First");
		return adminRepository.save(admin);

	}

	// Admin LogOut Using Session key for security purposes
	@Override
	public Admin logoutAdmin(String mobile) throws AdminException {

		CurrentSession adminSession = currentSessionRepository.findByUuid(mobile);
		if (adminSession == null) {
			throw new AdminException("User Not Logged In with this number");
		}
		Optional<Admin> admin = adminRepository.findById(adminSession.getUserId());
		currentSessionRepository.delete(adminSession);
		if (!admin.isPresent())
			throw new AdminException("Register Admin Not found...please Resister");
		return admin.get();
	}

	// Creating Admin's Current Session
	@Override
	public CurrentSession adminSession(String mobile) throws AdminException {
		Admin admin = adminRepository.findByMobile(mobile);
		if (admin == null)
			throw new AdminException("Please Enter a valid Mobile Number/////");

		Optional<CurrentSession> userSession = currentSessionRepository.findById(admin.getAdminId());
		if (!userSession.isPresent())
			throw new AdminException("Please Login First");
		return userSession.get();
	}

}
