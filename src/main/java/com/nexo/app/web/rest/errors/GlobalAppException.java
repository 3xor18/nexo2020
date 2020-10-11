package com.nexo.app.web.rest.errors;

import java.util.ArrayList;
import java.util.List;

import com.nexo.app.service.dto.ErrorDto;

public class GlobalAppException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String code;

	private final int responseCode;

	private final List<ErrorDto> errorsList = new ArrayList<>();

	public GlobalAppException(String code, int responseCode, String message) {
		super(message);
		this.code = code;
		this.responseCode = responseCode;
	}

	public GlobalAppException(String code, int responseCode, String message, List<ErrorDto> errorsList) {
		super(message);
		this.code = code;
		this.responseCode = responseCode;
		this.errorsList.addAll(errorsList);
	}

	public String getCode() {
		return code;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public List<ErrorDto> getErrorsList() {
		return errorsList;
	}

}