package com.alan.forum.exception;

public class NotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1537879253736399336L;

	public NotFoundException(String message) {
		super(message);
	}

}
