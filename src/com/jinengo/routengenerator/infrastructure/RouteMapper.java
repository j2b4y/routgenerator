package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;

/**
 * Process Route Document an map it to RouteModel and SubrouteModel
 * Calculates values wich are not returned from the jinengo api
 * 
 * @author lars & christopher
 *
 */
public class RouteMapper {
	
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
	 * Map and calculate SubRoute values
	 * 
	 * @param subRouteElem
	 * @return subRouteModel
	 */
	private SubrouteModel processSubRouteElement(Element subRouteElem) {
		SubRouteMapper subRouteMapper = new SubRouteMapper();
		return subRouteMapper.mapSubRoute(subRouteElem);
	}
	
	/**
	 * proces and map all subroutes
	 * 
	 * @param subRouteNodes
	 * @return list of subroute
	 */
	private ArrayList<SubrouteModel> getSubroutes(NodeList subRouteNodes) {
		ArrayList<SubrouteModel> subRoutes = new ArrayList<SubrouteModel>();
		
    	int subRouteCount = subRouteNodes.getLength();
    	System.out.println(subRouteCount);
    	for (int i = 0; i < subRouteCount; i++) {
    		Node subRoute = subRouteNodes.item(i);
    		if(subRoute.getNodeType() == Node.ELEMENT_NODE){
                Element subRouteElement = (Element)subRoute;
                subRoutes.add(processSubRouteElement(subRouteElement));
    		}
    	}
    	
    	return subRoutes;
	}
	
	/**
	 * Map and calculate Route calues
	 * 
	 * @param routeElem
	 * @return routeModel
	 */
	private RouteModel processRouteElement(Element routeElem) {
		RouteModel routeModel = new RouteModel();
		
		// Set all subroute to route model
    	routeModel.setSubroutes(getSubroutes(routeElem.getElementsByTagName("subroute")));
		
		// Set Route properties
    	routeModel.setTotalCost(Float.parseFloat(getNodeValue("totalcosts", routeElem)));
    	routeModel.setTotalDistance(Float.parseFloat(getNodeValue("totaldistance", routeElem)));
    	routeModel.setTotalEmission(Float.parseFloat(getNodeValue("totalemission", routeElem)));
    	routeModel.setTotalTime(Float.parseFloat(getNodeValue("totaltraveltime", routeElem)));
		
		return routeModel;
	}
	
	/**
	 * Process the XML Document returned from the jinengo api
	 * 
	 * @param doc - route xml doc
	 * @return route list
	 */
	public ArrayList<RouteModel> processXmlDocument(Document doc){
    	ArrayList<RouteModel> routeList = new ArrayList<RouteModel>();
    	
    	// get all route nodes from document
    	NodeList routeNodes = doc.getElementsByTagName("route");
    	
    	// process all routes
    	int routeCount = routeNodes.getLength();
    	
    	for (int i = 0; i < routeCount; i++) {
    		Node route = routeNodes.item(i);
    		if(route.getNodeType() == Node.ELEMENT_NODE){
                Element routeElement = (Element)route;
                routeList.add(processRouteElement(routeElement));
    		}
    	}
    	return routeList;
	}
}
