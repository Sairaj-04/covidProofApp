package com.vaccination.app.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CurrentSession {
	// foreign key for login users
	@Id
	private Integer userId;

	// User Unique Id
	private String uuid;

	// Current session login time
	private LocalDateTime localDateTime;

	public CurrentSession() {
		super();
	}

	public CurrentSession(Integer userId, String uuid, LocalDateTime localDateTime) {
		super();
		this.userId = userId;
		this.uuid = uuid;
		this.localDateTime = localDateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public String toString() {
		return "CurrentSession [userId=" + userId + ", uuid=" + uuid + ", localDateTime=" + localDateTime + "]";
	}

}
