package com.cognixia.jump.furniture_app.exception;

public class ResourceNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resource, int id) {
		super(resource + " with id = " + id + " was not found");
	}
}
