package com.nexo.app.web.rest.errors;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.nexo.app.service.dto.ErrorDto;


public class NexoNotFoundException extends GlobalAppException {

	public NexoNotFoundException(String code, String message) {
		super(code, HttpStatus.NOT_FOUND.value(), message);
	}

	public NexoNotFoundException(String code, String message, ErrorDto data) {
		super(code, HttpStatus.NOT_FOUND.value(), message, Arrays.asList(data));
	}

	private static final long serialVersionUID = 1L;

}