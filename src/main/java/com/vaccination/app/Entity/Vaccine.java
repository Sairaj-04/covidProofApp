package com.vaccination.app.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Vaccine {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer vaccineId;

	@NotNull(message = "Vaccine name cannot be null.")
	@NotBlank(message = "Vaccine name cannot be blank.")
	@NotEmpty(message = "Vaccine name cannot be empty.")
	private String name;

	@NotNull(message = "Vaccine description cannot be null.")
	private String description;

	public Vaccine() {
		super();
	}

	public Vaccine(Integer vaccineId,
			@NotNull(message = "Vaccine name cannot be null.") @NotBlank(message = "Vaccine name cannot be blank.") @NotEmpty(message = "Vaccine name cannot be empty.") String name,
			@NotNull(message = "Vaccine description cannot be null.") String description) {
		super();
		this.vaccineId = vaccineId;
		this.name = name;
		this.description = description;
	}

	public Integer getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(Integer vaccineId) {
		this.vaccineId = vaccineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Vaccine [vaccineId=" + vaccineId + ", name=" + name + ", description=" + description + "]";
	}

}
