package com.ronaldocarvalho.cursomc.services.exceptions;

public class AutorizathionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutorizathionException(String msg) {
		super(msg);

	}

	public AutorizathionException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
