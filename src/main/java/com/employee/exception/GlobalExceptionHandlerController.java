package com.employee.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandlerController {
	
	
	@Bean
	  public ErrorAttributes errorAttributes() {
	   
//		removing exception from response field
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
				errorAttributes.remove("trace");
				return errorAttributes;
			}
		};
	  }

	@ExceptionHandler(CustomException.class)
	  public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
	    res.sendError(ex.getHttpStatus().value(), ex.getMessage());
	  }
}
