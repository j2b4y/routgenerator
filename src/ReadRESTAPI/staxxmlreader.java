package ReadRESTAPI;

import java.io.InputStream;
import java.sql.Time;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.joda.time.DateTime;

import DBConnection.MSSQLConnection;

public class staxxmlreader {
	
	boolean bstartroute = false;
	boolean bendroute = false;
	boolean bcost = false;
	boolean bdistance = false;
	boolean bemission = false;
	boolean btraveltime = false;
	String stotalcosts= "";
	String stotaldistance= "";
	String stotalemissions= "";
	String stotaltraveltime= "";
	boolean bsubroute = false;
	boolean bsubcosts = false;
	boolean bsubdistance = false;
	boolean bsubemissions = false;
	boolean bsubtraveltime = false;
	boolean bsubtransport = false;
	boolean subname = false;
	boolean bstart= false;
	boolean bend = false;
	boolean substarttime = false;
	boolean subendtime = false;
	String ssubcosts="";
	String ssubdistance="";
	String ssubemissions="";
	String ssubtraveltime="";
	String ssubtransport="";
	String ssubstart="";
	String ssubstarttime="";
	String ssubend ="";
	String ssubendtime="";
	int subroutscounter = 0;

	public void streamreader(InputStream stream) throws XMLStreamException, FactoryConfigurationError{
		
		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
		
		while ( xmlStreamReader.hasNext() )
		{

		  switch ( xmlStreamReader.getEventType() )
		  {
		    case XMLStreamConstants.START_DOCUMENT:
//		      System.out.println( "START_DOCUMENT: " + xmlStreamReader.getVersion() );
		      break;

		    case XMLStreamConstants.END_DOCUMENT:
//		      System.out.println( "END_DOCUMENT: " );
		      xmlStreamReader.close();
		      break;

		    case XMLStreamConstants.NAMESPACE:
//		      System.out.println( "NAMESPACE: " + xmlStreamReader.getNamespaceURI() );
		      break;

		    case XMLStreamConstants.START_ELEMENT:
//			    System.out.println( "START_ELEMENT: " + xmlStreamReader.getLocalName() );
		    	switch(xmlStreamReader.getLocalName())
		    	{
		    	case "route":
		    		bstartroute = true;
		    		break;
		    	
		    	case "totalcosts":
		    		bcost = true;
		    		break;
		    		
		    	case "totaldistance":
		    		bdistance = true;
		    		break;
		    		
		    	case "totalemission":
		    		bemission = true;
		    		break;
		    		
		    	case "totaltraveltime":
		    		btraveltime = true;
		    		break;
		    		
		    	case "costs":
		    		bsubcosts = true;
		    		break;
		    		
		    	case "distance":
		    		bsubdistance = true;
		    		break;
		    		
		    	case "emissions":
		    		bsubemissions = true;
		    		break;
		    		
		    	case "traveltime":
		    		bsubtraveltime = true;
		    		break;
		    		
		    	case "transportation":
		    		bsubtransport = true;
		    		break;
		    		
		    	case "startsituation":
		    		bstart = true;
		    		break;
		    		
		    	case "endsituation":
		    		bend = true;
		    		break;
		    		
		    	case "name":
		    		subname = true;
		    		break;
		    		
		    	case "starttime":
		    		substarttime = true;
		    		break;
		    		
		    	case "duetime":
		    		subendtime = true;
		    		break;
		    		
		    	
		    	}
		    	break;


		    case XMLStreamConstants.CHARACTERS:
		      if ( ! xmlStreamReader.isWhiteSpace() ){
//		    	  System.out.println(xmlStreamReader.getText());
		    	  
		    	  if(bcost){
		    		  stotalcosts = xmlStreamReader.getText();
		    		  bcost = false;
		    	  }
		    	  if(bdistance){
		    		  stotaldistance = xmlStreamReader.getText();
		    		  bdistance = false;
		    	  }
		    	  if(bemission){
		    		  stotalemissions = xmlStreamReader.getText();
		    		  bemission = false;
		    	  }
		    	  if(btraveltime){
		    		  stotaltraveltime = xmlStreamReader.getText();
		    		  btraveltime = false;
		    	  }
		    	  if(bsubcosts){
		    		  ssubcosts = xmlStreamReader.getText();
		    		  bsubcosts = false;
		    	  }
		    	  if(bsubdistance){
		    		  ssubdistance= xmlStreamReader.getText();
		    		  bsubdistance=false;
		    	  }
		    	  if(bsubemissions){
		    		  ssubemissions= xmlStreamReader.getText();
		    		  bsubemissions = false;
		    	  }
		    	  if(bsubtraveltime){
		    		  ssubtraveltime= xmlStreamReader.getText();
		    		  bsubtraveltime = false;
		    	  }
		    	  if(bsubtransport){
		    		  ssubtransport= xmlStreamReader.getText();
		    		  bsubtransport = false;
		    	  }
		    	  if(bstart && subname){
		    		  ssubstart = xmlStreamReader.getText();
		    		  bstart = false;
		    		  subname = false;
		    	  }
		    	  if(substarttime){
		    		  ssubstarttime = xmlStreamReader.getText();
		    		  substarttime = false;
		    	  }
		    	  if(bend && subname){
		    		  ssubend = xmlStreamReader.getText();
		    		  bend = false;
		    		  subname = false;
		    	  }
		    	  if(subendtime){
		    		  ssubendtime = xmlStreamReader.getText();
		    		  subendtime = false;
		    	  }
		      }
		      break;


		    case XMLStreamConstants.END_ELEMENT:
//		      System.out.println( "END_ELEMENT: " + xmlStreamReader.getLocalName() );
		    	switch(xmlStreamReader.getLocalName())
		    	{
		    	case "route":
		    		routhandler();
		    		break;
		    		
		    	case "subroute":
		    		subrouthandler();
		    		break;

		    	}
		    	
		      break;

		  }
		  xmlStreamReader.next();
		}		
	}
	
	private void routhandler(){
		
		if(stotalcosts!="" && stotaldistance != "" && stotalemissions != "" && stotaltraveltime != "")
		{
			DateTime traveltime = new DateTime((long) (Float.parseFloat(stotaltraveltime)));
			
			System.out.println("----> " + traveltime.toString("HH:mm:ss"));
			
			System.out.println("INSERT INTO ROUTS () VALUES {"+stotalcosts+","+stotaldistance+","+stotalemissions+","+stotaltraveltime+"}");
			MSSQLConnection.insertRout(stotalcosts, stotaldistance, stotalemissions, stotaltraveltime);
		}
		else
		{
			System.out.println("{"+stotalcosts+","+stotaldistance+","+stotalemissions+","+stotaltraveltime+"}");
			System.out.println("Fehler: nicht alle Felder gefuellt");
		}
			
		stotalcosts= "";
		stotaldistance= "";
		stotalemissions= "";
		stotaltraveltime= "";
	
	}
	
	private void subrouthandler(){
		
		if(ssubcosts!="" && ssubdistance != "" && ssubemissions != "" && ssubtraveltime != "" && ssubtransport != "" && ssubstart != "" && ssubstarttime != "" && ssubend != "" && ssubendtime != "")
		{
			DateTime startdate = new DateTime(Long.parseLong(ssubstarttime));
			DateTime enddate = new DateTime(Long.parseLong(ssubendtime));
			
			DateTime date = enddate.minus(Long.parseLong(ssubstarttime));
			
			System.out.println("starttime: "+ssubstarttime);
			System.out.println("endtime: "+ssubendtime);
			System.out.println("traveltime: "+ssubtraveltime);
			System.out.println("traveltime: "+date);
			
			System.out.println("INSERT INTO SUBROUTS () VALUES {"+ssubcosts+","+ssubdistance+","+ssubemissions+","+ssubtraveltime+","+ssubtransport+","+ssubstart+","+startdate.toString("yyyy-MM-dd HH:mm:ss")+","+ssubend+","+enddate.toString("yyyy-MM-dd HH:mm:ss")+"}");
		}
		else
		{
			System.out.println("Fehler: nicht alle Felder gefuellt");
		}
			
		ssubcosts="";
		ssubdistance="";
		ssubemissions="";
		ssubtraveltime="";
		ssubtransport="";
		ssubstart="";
		ssubstarttime="";
		ssubend ="";
		ssubendtime="";
	}
	
}
