package com.employee.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.Service.EmployeeService;
import com.employee.dto.EmployeeDto;
import com.employee.entity.Response;
import com.employee.exception.CustomException;
import com.file.util.Csvutil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;


/**
 * @author moglix
 *
 */
@RestController
@RequestMapping("employee/v1")
@Api(value = "EMPLOYEList<Foo> findByBoo(String boo);E", description = "Employee Operations", tags = {"EMPLOYEE"})
@RequiredArgsConstructor
public class EmployeeController {
	
	@Autowired
	private final EmployeeService employeeService;
	
	
	/**
	 * Api to create employee
	 * 
	 * @param employeeDto
	 * @return
	 */
	@ApiOperation(value = "Returns  employee details on successful creation")
	@PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createEmployee(@RequestBody EmployeeDto employeeDto) {
		EmployeeDto emp = employeeService.createEmployee(employeeDto);
		Response<EmployeeDto> response = null;
		if(emp == null) {
			//response = new Response<EmployeeDto>("Error Occured While creating Employee","500",emp);
			 throw new CustomException("unable to create employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
//			response = new Response<EmployeeDto>("employee created successfully","201",emp);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		
	}
	

	/**
	 * Api to find employee by uuid
	 * 
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Returns  employee if exists by uuid")
	@GetMapping(value = "/employee/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getEmployeeById(@PathVariable("uuid") String uuid){
		EmployeeDto emp = employeeService.getEmployeeById(uuid);
		Response<EmployeeDto> response = new Response<EmployeeDto>("Employee Successfully found","200",emp);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	

	/**
	 * Api to get all employees list
	 * 
	 * @return
	 */
	@ApiOperation(value = "Returns  list of all employees")
	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getEmployees(){
		List<EmployeeDto> employees = employeeService.findAllEmployees();
		Response<List<EmployeeDto>> response = new Response<List<EmployeeDto>>("list of employees","202",employees);	
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	

	/**
	 * Api to delete an employee
	 * 
	 * @param uuid
	 * @return
	 */
	@ApiOperation(value = "Returns  employee details on successful deletion")
	@DeleteMapping(value = "/employee/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteEmployeeByUuid(@PathVariable("uuid") String uuid){
		EmployeeDto emp = employeeService.deleteEmployeeByUuid(uuid);
		Response<EmployeeDto> response = new Response<EmployeeDto>("employee deleted successfully","201",emp);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
	

	/**
	 * Api to update Employee
	 * 
	 * @param employee
	 * @return
	 */
	@ApiOperation(value = "Returns  employee details on successful updation")
	@PutMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public  ResponseEntity<Response> updateEmployee(@RequestBody EmployeeDto employeeDto) {
		
		EmployeeDto emp = employeeService.updateEmployee(employeeDto);
		Response response = new Response<EmployeeDto>("employee updated successfully","204",emp);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Api to create employee from csv file 
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/employee/employee-csv-data")
	public ResponseEntity<Response> createEmployeeFromCsvFile(@RequestBody MultipartFile file){
		EmployeeDto emp = employeeService.createEmployeeFromCsvData(file);
		Response response = new Response<EmployeeDto>("employee created successfully","201",emp);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
