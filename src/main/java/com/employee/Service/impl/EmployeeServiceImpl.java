package com.employee.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employee.Repository.EmployeeRepository;
import com.employee.Service.EmployeeService;
import com.employee.dto.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.CustomException;
import com.employee.validations.EmployeeValidation;
import com.file.util.Csvutil;

import lombok.RequiredArgsConstructor;



/**
 * @author moglix
 *
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private final EmployeeRepository employeeRepository;
	@Autowired
	private final EmployeeValidation empValidation;
	@Autowired
	private final ModelMapper mapper;
	@Autowired
	private final Csvutil csvutil;

	
	
	/**
	 *
	 */
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		empValidation.validateEmployee(employeeDto);
		Employee emp = null;
		try {
			Employee mapped = mapper.map(employeeDto, Employee.class);
			System.out.println(mapped);
			mapped.setActive(true);
			mapped.setUuid(UUID.randomUUID().toString());
			emp = employeeRepository.save(mapped);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp == null? null:mapper.map(emp, EmployeeDto.class);
	}

	/**
	 *
	 */
	@Override
	public EmployeeDto getEmployeeById(String uuid) {
		Employee emp = employeeRepository.findByUuidAndActiveTrue(uuid)
				  .orElseThrow(() -> new CustomException("employee not found", HttpStatus.NOT_FOUND));
		return mapper.map(emp, EmployeeDto.class);
	}

	/**
	 *
	 */
	@Override
	public List<EmployeeDto> findAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try {
			employees = employeeRepository.findAllByActiveTrue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees.size() == 0? new ArrayList() :employees.stream().map(emp -> mapper.map(emp, EmployeeDto.class)).collect(Collectors.toList());
		}

	/**
	 *
	 */
	@Override
	public EmployeeDto deleteEmployeeByUuid(String uuid) {
		Employee emp = null;
			 emp = employeeRepository.findByUuidAndActiveTrue(uuid)
					  .orElseThrow(() -> new CustomException("employee not found", HttpStatus.NOT_FOUND));
		
		emp.setActive(false);
		employeeRepository.save(emp);
		return mapper.map(emp, EmployeeDto.class);
	}
//
	
	/**
	 *
	 */
	@Override
	public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
		Employee emp = null;
			emp = employeeRepository.findByUuidAndActiveTrue(employeeDto.getUuid())
					  .orElseThrow(() -> new CustomException("employee not found", HttpStatus.NOT_FOUND));
		
		emp.setFirstname(employeeDto.getFirstname());
		emp.setLastname(employeeDto.getLastname());
		emp.setEmail(emp.getEmail());
		emp.setContactNumber(employeeDto.getContactNumber());
		emp.setAddress(employeeDto.getAddress());
		emp.setAge(employeeDto.getAge());
		
		return mapper.map(employeeRepository.save(emp),EmployeeDto.class);	
	}

	/**
	 *
	 */
	@Override
	public EmployeeDto createEmployeeFromCsvData(MultipartFile file) {
		if(!file.getOriginalFilename().endsWith(".csv")) {
			throw new CustomException("invalid file type", HttpStatus.BAD_REQUEST);
		}
		return createEmployee(csvutil.readCsvToEmployeeDto(file));
		
	}

}
