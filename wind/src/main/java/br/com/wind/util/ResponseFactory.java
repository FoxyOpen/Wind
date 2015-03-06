package br.com.wind.util;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseFactory {

	public static Response getOkResponse() {
		return Response.status(OK).build();
	}
	
	public static Response getOkResponse(Object entidade) {
		return Response.status(OK).entity(entidade).build();
	}
	
	public static Response getInternalServerErrorResponse() {
		return Response.status(INTERNAL_SERVER_ERROR).build();
	}
	
	public static Response getInternalServerErrorResponse(Object entidade) {
		return Response.status(INTERNAL_SERVER_ERROR).entity(entidade).build();
	}

	public static Response getUnauthorizedResponse() {
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	public static Response getUnauthorizedResponse(Object entidade) {
		return Response.status(Status.UNAUTHORIZED).entity(entidade).build();
	}
	
	public static Response getNoContentResponse() {
		return Response.status(Status.NO_CONTENT).build();
	}
	
	public static Response getNoContentResponse(Object entidade) {
		return Response.status(Status.NO_CONTENT).entity(entidade).build();
	}
}