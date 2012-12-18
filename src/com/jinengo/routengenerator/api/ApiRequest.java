package com.jinengo.routengenerator.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ApiRequest {
	
	public Document getXmlRouteDocument(String startString, String destinationString, String starttime){
    	// token for jinengo api access
    	String token = "7a2ba89a-787f-4b42-83cf-f990497e82c8";
    	
    	// jinengo api
		String uri = "http://134.106.13.85:81/restapi/JinengoRestful?operation=lookupcustomerroute&token=" + token + "&startname=" + startString + "&endname="+destinationString+"&starttime=" + starttime + "&duetime=0";
    	System.out.println(uri);
    	URL url;
    	Document doc = null;
    	
		try {
			
			url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	connection.setRequestMethod("GET");
	    	connection.setRequestProperty("Accept", "application/xml");

	    	InputStream xml = connection.getInputStream();

	    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	doc = db.parse(xml);
	    	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
    	return doc;
	}
}
