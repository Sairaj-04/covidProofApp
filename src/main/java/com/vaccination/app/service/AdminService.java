package com.vaccination.app.service;

import com.vaccination.app.Entity.Admin;
import com.vaccination.app.Entity.AdminLoginDTO;
import com.vaccination.app.Entity.AdminPasswordDTO;
import com.vaccination.app.Entity.CurrentSession;
import com.vaccination.app.exception.AdminException;

public interface AdminService {
	// Register Admin
	public Admin registerAdmin(Admin admin) throws AdminException;

	// Login Admin
	public Admin loginAdmin(AdminLoginDTO adminLoginDTO) throws AdminException;

	// LogOut Admin
	public Admin logoutAdmin(String mobile) throws AdminException;

	// Updated Admin Password
	public Admin updatePassword(AdminPasswordDTO adminPasswordDTO) throws AdminException;

	// Admin Current Session
	public CurrentSession adminSession(String mobile) throws AdminException;

}
