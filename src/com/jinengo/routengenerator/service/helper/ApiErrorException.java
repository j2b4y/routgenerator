package com.jinengo.routengenerator.service.helper;

/**
 * Default Jinengo API Exception
 * 
 * @author lars
 *
 */
public class ApiErrorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String msg = "";
	
	/**
	 * Exception for Jinengo API Error
	 * 
	 * @param msg - exception message
	 */
	public ApiErrorException(String msg) {
		this.msg = msg;
	}
	
	/**
	 * Default toString Function
	 */
	public String toString(){
        return this.msg;
    }
}
