package com.employee.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Response<T> {

	private final String message;
	private final String statusCode;
	private LocalDateTime timeStamp = LocalDateTime.now();
	private final T data;
}
