package com.ronaldocarvalho.cursomc.services.exceptions;

public class DataIntegrityExpetion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataIntegrityExpetion(String msg) {
		super(msg);

	}

	public DataIntegrityExpetion(String msg, Throwable cause) {
		super(msg, cause);
	}

}
