package com.jinengo.routengenerator.infrastructure;

import org.joda.time.DateTime;
import org.w3c.dom.Element;

import com.jinengo.routengenerator.model.SubrouteModel;

/**
 * Mapper to map xml api result to subRoute Object
 * 
 * @author lars + christopher
 *
 */
public class SubRouteMapper {
	
	/**
	 * Helper to get node values from xml document
	 * 
	 * @param nodeName - XML Node Name
	 * @param elem - XML Element
	 * @return String - Node value
	 */
	private String getNodeValue(String nodeName, Element elem){
		return elem.getElementsByTagName(nodeName).item(0).getTextContent();
	}
	
	/**
	 * Helper to get node values from xml document
	 * 
	 * @param nodeName - XML Node Name
	 * @param elem - XML Element
	 * @param pos - Position in Node List
	 * @return String - Node value
	 */
	private String getNodeValue(String nodeName, Element elem, int pos){
		return elem.getElementsByTagName(nodeName).item(pos).getTextContent();
	}
	
	/**
	 * Map and calculate SubRoute values
	 * 
	 * @param subRouteElem
	 * @return subRouteModel
	 */
	public SubrouteModel mapSubRoute(Element subRouteElem) {
		SubrouteModel subrouteModel = new SubrouteModel();
		
		subrouteModel.setCosts(Float.parseFloat(getNodeValue("costs", subRouteElem)));
		subrouteModel.setDistance(Float.parseFloat(getNodeValue("distance", subRouteElem)));
		subrouteModel.setEcoImpact(Float.parseFloat(getNodeValue("emissions", subRouteElem)));
		subrouteModel.setTime((int)Float.parseFloat(getNodeValue("traveltime", subRouteElem)));
		subrouteModel.setTrasportationRaw(getNodeValue("transportation", subRouteElem));
		subrouteModel.setDepartureAddress(getNodeValue("name", subRouteElem, 0));
		subrouteModel.setDepartureTime(new DateTime((long)Float.parseFloat(getNodeValue("starttime", subRouteElem))));
		subrouteModel.setDestinationAddress(getNodeValue("name", subRouteElem, 1));
		subrouteModel.setDestinationTime(new DateTime((long)Float.parseFloat(getNodeValue("duetime", subRouteElem))));
		
		return subrouteModel;
	}
}
