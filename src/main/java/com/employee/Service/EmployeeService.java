package com.employee.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.employee.dto.EmployeeDto;

public interface EmployeeService {

	public EmployeeDto createEmployee(EmployeeDto employeeDto);
	
	public EmployeeDto getEmployeeById(String uuid);
	
	public List<EmployeeDto> findAllEmployees();
	
	public EmployeeDto deleteEmployeeByUuid(String uuid);
	
	public EmployeeDto updateEmployee(EmployeeDto employeeDto);

	public EmployeeDto createEmployeeFromCsvData(MultipartFile file);
	
}
