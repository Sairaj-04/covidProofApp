package com.vaccination.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccination.app.Entity.CurrentSession;

@Repository
public interface CurrentSessionRepository extends JpaRepository<CurrentSession,Integer> {
	//Get Current Session of user by user unique id
	public CurrentSession findByUuid(String uuid);
}