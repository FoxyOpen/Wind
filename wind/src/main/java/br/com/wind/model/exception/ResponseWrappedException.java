package br.com.wind.model.exception;

import javax.ws.rs.core.Response;

public class ResponseWrappedException extends RuntimeException {

	private static final long serialVersionUID = 740245825588725601L;

	private final Response response;
	
	public ResponseWrappedException(Response response) {
		super();
		this.response = response;
	}
	
	public ResponseWrappedException(Response response, String message) {
		super(message);
		this.response = response;
	}
	
	public ResponseWrappedException(Response response, Throwable cause) {
		super(cause);
		this.response = response;
	}
	
	public ResponseWrappedException(Response response, String message, Throwable cause) {
		super(message, cause);
		this.response = response;
	}
	
	public Response getResponse() {
		return response;
	}
}
