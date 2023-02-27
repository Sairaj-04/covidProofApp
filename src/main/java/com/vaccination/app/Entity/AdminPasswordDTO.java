package com.vaccination.app.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AdminPasswordDTO {

	@NotNull(message = "Mobile no should not be null")
	@Pattern(regexp = "^[0-9]{10}", message = "Mobile number length must consist 10 digits")
	private String mobile;

	@NotNull(message = "Old Password cannot be null.")
//	@Pattern(regexp = "^[a-zA-Z0-9]{8}",message="Old Password length must be 8 character")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,15}$", message = "password length must contain atleast 6 characters of atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String oldPassword;

	@NotNull(message = "New Password cannot be null.")
//	@Pattern(regexp = "^[a-zA-Z0-9]{8}",message="New Password length must be 8 character")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,15}$", message = "password length must contain atleast 6 characters of atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
	private String newPassword;

	public AdminPasswordDTO() {
		super();
	}

	public AdminPasswordDTO(
			@NotNull(message = "Mobile no should not be null") @Pattern(regexp = "^[0-9]{10}", message = "Mobile number length must consist 10 digits") String mobile,
			@NotNull(message = "Old Password cannot be null.") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,15}$", message = "password length must contain atleast 6 characters of atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ") String oldPassword,
			@NotNull(message = "New Password cannot be null.") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,15}$", message = "password length must contain atleast 6 characters of atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ") String newPassword) {
		super();
		this.mobile = mobile;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "AdminPasswordDTO [mobile=" + mobile + ", old_pass=" + oldPassword + ", new_pass=" + newPassword + "]";
	}


}
