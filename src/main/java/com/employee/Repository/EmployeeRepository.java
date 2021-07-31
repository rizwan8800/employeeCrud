package com.employee.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	Optional<Employee> findByUuid(String uuid);
	
	Optional<Employee> findByUuidAndActiveTrue(String uuid);
	
	boolean existsByEmail(String email);
	
	boolean existsByContactNumber(String contactNumber);

	List<Employee> findAllByActiveTrue();

	
}
