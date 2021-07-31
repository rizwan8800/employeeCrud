package com.file.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.employee.dto.EmployeeDto;
import com.employee.exception.CustomException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;


@Component
public class Csvutil {
	
	
	public EmployeeDto readCsvToEmployeeDto( MultipartFile file) {
		Reader reader = null;
		try {
			reader = new InputStreamReader(file.getInputStream());
		} catch (IOException e1) {
			throw new CustomException("invalid file", HttpStatus.BAD_REQUEST);
		}
		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("firstname", "firstname");
		mapping.put("lastname", "lastname");
		mapping.put("email", "email");
		mapping.put("contactNumber", "contactNumber");
		mapping.put("age", "age");
		mapping.put("address", "address");
		
		HeaderColumnNameTranslateMappingStrategy<EmployeeDto> strategy =
	             new HeaderColumnNameTranslateMappingStrategy<EmployeeDto>();
	        strategy.setType(EmployeeDto.class);
	        strategy.setColumnMapping(mapping);
		
	        CsvToBean<EmployeeDto> csvToBean;
	    try {
	    	csvToBean =   new CsvToBeanBuilder<EmployeeDto>(reader)
		            .withType(EmployeeDto.class)
		            .withSkipLines(1)
		            .withIgnoreLeadingWhiteSpace(true)
		            .build();
		} catch (Exception e) {
			throw new CustomException("invalid data in csv file", HttpStatus.BAD_REQUEST);
		}  
		List<EmployeeDto> list = csvToBean.parse(strategy, csvReader);
		
		if(list.size() == 0) {
			throw new CustomException("no data in file", HttpStatus.BAD_REQUEST);
		}
		
		EmployeeDto employeeDto = list.get(0);
		return employeeDto;
	}
	

}
