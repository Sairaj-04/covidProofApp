package com.vaccination.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccination.app.Entity.Vaccine;
import com.vaccination.app.exception.VaccineException;
import com.vaccination.app.repository.VaccineRepository;

@Service
public class VaccineServiceImpl implements VaccineService{
	@Autowired
	private VaccineRepository vaccineRepository;

	//Add Vaccine Details
	@Override
	public Vaccine addVaccine(Vaccine vaccine) throws VaccineException {
		Vaccine v=vaccineRepository.save(vaccine);
		if(v==null) throw new VaccineException("Vaccine not found");
		return v;
	}
	//Update Vaccine Details
	@Override
	public Vaccine updateVaccine(Vaccine vaccine) throws VaccineException {
	    Optional<Vaccine> opt=vaccineRepository.findById(vaccine.getVaccineId());
	    
	    if(opt.isPresent()) {
	    	Vaccine v=vaccineRepository.save(vaccine);
	    	return v;
	    }
	    else throw new VaccineException("Vaccine not found");
	    
	}

	//Delete Vaccine Details
	@Override
	public Vaccine deleteVaccine(Integer vaccineId) throws VaccineException {
		Optional<Vaccine> opt = vaccineRepository.findById(vaccineId);

		if (opt.isPresent()) {

			Vaccine existingVaccine = opt.get();

			vaccineRepository.delete(existingVaccine);

			return existingVaccine;

		} 
			else throw new VaccineException("Vaccine not found with vaccine id :" + vaccineId);
	}
	//Get Vaccine Details by vaccine id
	@Override
	public Vaccine getVaccineById(Integer vaccineId) throws VaccineException {
		Optional<Vaccine> opt = vaccineRepository.findById(vaccineId);

		if (opt.isPresent()) {

			Vaccine existingVaccine = opt.get();
			return existingVaccine;

		} 
			else throw new VaccineException("Vaccine not found with vaccine id :" + vaccineId);
	}

	//Get all Vaccine Details
	@Override
	public List<Vaccine> getAllVaccine() throws VaccineException {
		List<Vaccine> vaccList= vaccineRepository.findAll();
		if(vaccList.isEmpty()) throw new VaccineException("Vaccines not found Please Add Vaccine Details");
		else return vaccList;
	}

	//get Vaccine code(id) by Name
	@Override
	public Integer getIdByName(String name) throws VaccineException {
		int id = vaccineRepository.getIdByName(name);
		if(id!=0) {
			return id;
		}
		else {
			throw new VaccineException("No vaccine exist with name:"+name);
		}
	}

}
