package com.employee.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
	
	@Id
	private String uuid;
	private String firstname;
	private String lastname;
	private String email;
	private String contactNumber;
	private int age;
	private String address;
	private boolean active;
	
}
