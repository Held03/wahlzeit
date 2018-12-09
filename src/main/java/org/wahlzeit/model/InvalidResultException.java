/**
 * InvalidResultException.java
 * 
 * Version: Dec 9, 2018
 *
 * Date: Dec 9, 2018
 *
 * Copyright: AGPL-3
 */
package org.wahlzeit.model;

/**
 * @author cryptjar
 *
 */
public class InvalidResultException extends Exception {

	/**
	 * 
	 */
	public InvalidResultException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public InvalidResultException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InvalidResultException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidResultException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidResultException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
