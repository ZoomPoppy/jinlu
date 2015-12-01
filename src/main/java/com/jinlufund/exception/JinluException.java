package com.jinlufund.exception;

import java.util.HashMap;
import java.util.Map;

public class JinluException extends RuntimeException {

	private static final long serialVersionUID = 8498214780310414353L;

	private String errorCode;

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public JinluException() {
		super();
	}
	
	public JinluException(Throwable cause) {
		super(cause);
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("errorCode: ").append(errorCode).append(", ");
		sb.append("parameters: ").append(parameters);
		return sb.toString();
	}

}
