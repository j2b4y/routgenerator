package ReadRESTAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvreader {
	
	public ArrayList<String> csvhandler(String file){
	
	    FileReader myFile = null;
	    BufferedReader buff = null;
	    final List<String> lines = new ArrayList<String>();
		
	    try {
	        myFile = new FileReader(file);
	        buff = new BufferedReader(myFile);
	        String line;
	        while ((line = buff.readLine()) != null) {
	            //System.out.println(line);
	 
	            lines.add(line);
	        }
	    } catch (IOException e) {
	        System.err.println("Error2 :" + e);
	    } finally {
	        try {
	            buff.close();
	            myFile.close();
	        } catch (IOException e) {
	            System.err.println("Error2 :" + e);
	        }
	    }
	 
	    final String[][] valuesArray = new String[lines.size()][];
	    int cnt = 0;
	    for (final String line : lines) {
	        valuesArray[cnt++] = line.split(",");
	    }
	    
		return (ArrayList<String>)lines;
	}

}