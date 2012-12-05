package ReadRESTAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.lang.String;


public class htmlreader {

	private URL url;
	private InputStream is = null;
	private DataInputStream dis;
	public String line;

	public String getToken(String user, String password){
		try{
			
			System.out.println("User: "+user+" Passwort: "+password);
			
			url = new URL("http://134.106.13.85:81/restapi/JinengoRestful?operation=login&username="+user+"&password="+password);
		    is = url.openStream();  // throws an IOException
		    dis = new DataInputStream(new BufferedInputStream(is));
	
		    while ((line = dis.readLine()) != null) {
		    	//System.out.println(line);
		    	return line;
		    }
		} 
		catch (MalformedURLException mue) {
		     mue.printStackTrace();

		} 
		catch (IOException ioe) {
		     ioe.printStackTrace();
		} 
		finally {
		    try {
		        is.close();
		    } 
		    catch (IOException ioe) {
	        	ioe.printStackTrace();
	    	}
		}
		
		return line;
	}
	
	public String getRouteInfo(String token, String start, String destination){
		try {
			System.out.println(token);
			
		    url = new URL("http://134.106.13.85:81/restapi/JinengoRestful?operation=lookupcustomerroute&token="+token+"&startname="+start+"&endname="+destination+"&starttime=0&duetime=0");
		    is = url.openStream();  // throws an IOException
		    dis = new DataInputStream(new BufferedInputStream(is));
	
		    while ((line = dis.readLine()) != null) {
		    	//System.out.println(line);
		    	return line;
		    }
		} 
		catch (MalformedURLException mue) {
		     mue.printStackTrace();

		} 
		catch (IOException ioe) {
		     ioe.printStackTrace();
		} 
		finally {
		    try {
		        is.close();
		    } 
		    catch (IOException ioe) {
	        	ioe.printStackTrace();
	    	}
		}
		return line;
	}
		
}