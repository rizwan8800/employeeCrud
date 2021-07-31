package com.employee.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.employee.Repository.EmployeeRepository;
import com.employee.dto.EmployeeDto;
import com.employee.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeValidation {
	
	@Autowired
	private final EmployeeRepository employeeRepository;

	public void validateEmployee(EmployeeDto employeeDto) {
		
		if(employeeDto.getFirstname().equals(null) || employeeDto.getFirstname().equals("")) {
			throw new CustomException("firstname Required", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getLastname().equals(null) || employeeDto.getLastname().equals("")) {
			throw new CustomException("lastname Required", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getAge() < 18 || employeeDto.getAge() > 40 ) {
			throw new CustomException("invalid age required between 17 and 41", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getAddress().equals(null) || employeeDto.getAddress().equals("")) {
			throw new CustomException("address Required", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getEmail().equals(null) || employeeDto.getEmail().equals("")) {
			throw new CustomException("email Required", HttpStatus.BAD_REQUEST);
		}
		if(employeeRepository.existsByEmail(employeeDto.getEmail())) {
			throw new CustomException("email already  registered", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getContactNumber().equals(null) || employeeDto.getContactNumber().equals(""))  {
			throw new CustomException("contact required", HttpStatus.BAD_REQUEST);
		}
		if(employeeDto.getContactNumber().length() != 10) {
			throw new CustomException("invalid contact number length", HttpStatus.BAD_REQUEST);
		}
		if(employeeRepository.existsByContactNumber(employeeDto.getContactNumber())) {
			throw new CustomException("contact already registered", HttpStatus.BAD_REQUEST);
		}
	}
}
