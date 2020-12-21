package com.assignament.ssms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

@Entity
public class Student {

	@Id
	@GeneratedValue
	private Long id;
	
	@NonNull
	private String firstName;
	
	@NonNull
	private String lastName;
	
	private Date birthDate;
	public Student() {}
	public Student(String firstName, String lastName, Date birthDate) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.birthDate=birthDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
}
