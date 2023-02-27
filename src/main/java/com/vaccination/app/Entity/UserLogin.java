package com.vaccination.app.Entity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

public class UserLogin {

	@Pattern(regexp = "^[0-9]{10}", message = "Mobile number length must be 10 digits [0-9]")
	private String mobile;

	@NotNull(message = "dob cannot be null.")
	@Past(message = "DoB should be in past.")
	private LocalDate dob;

	public UserLogin() {
		super();
	}

	public UserLogin(
			@Pattern(regexp = "^[0-9]{10}", message = "Mobile number length must be 10 digits [0-9]") String mobile,
			@NotNull(message = "dob cannot be null.") @Past(message = "DoB should be in past.") LocalDate dob) {
		super();
		this.mobile = mobile;
		this.dob = dob;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "ApplicantLogin [mobile=" + mobile + ", dob=" + dob + "]";
	}
	

}
