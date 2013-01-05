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
	
	public ApiErrorException(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
        return this.msg;
    }
}
