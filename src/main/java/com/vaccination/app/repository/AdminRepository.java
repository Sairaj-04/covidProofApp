package com.vaccination.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vaccination.app.Entity.Admin;
//Admin can add,Delete,update,view,viewAll  Vaccine,VaccineCenter
//Admin can view,update IdCard Details
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	//Checking Admin details for login
	@Query("select a from Admin a where a.mobile=?1 and a.password=?2 ")
	public Admin loginAdmin(String mobile,String password);
	public Admin findByMobile(String mobile);
}
