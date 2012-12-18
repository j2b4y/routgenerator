package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;

/**
 * Process Route Document an Map it to RouteModel and SubrouteModel
 * 
 * @author lars & christopher
 *
 */
public class RouteProcessor {

	private UserModel userModel;
	
	public RouteProcessor(UserModel userModel) {
		this.userModel = userModel;		
	}
	
	private String getNodeValue(String nodeName, Element elem){
		return elem.getElementsByTagName(nodeName).item(0).getTextContent();
	}
	
	private SubrouteModel processSubRouteElement(Element subRouteElem) {
		SubrouteModel subrouteModel = new SubrouteModel();
		subrouteModel.setCosts(Float.parseFloat(getNodeValue("costs", subRouteElem)));
		subrouteModel.setDistance(Float.parseFloat(getNodeValue("distance", subRouteElem)));
		subrouteModel.setEcoImpact(Float.parseFloat(getNodeValue("emissions", subRouteElem)));
		//subrouteModel.setTime(Integer.parseInt(getNodeValue("traveltime", subRouteElem)));
		
		return subrouteModel;
	}
	
	private RouteModel processRouteElement(Element routeElem) {
		RouteModel routeModel = new RouteModel();
		ArrayList<SubrouteModel> subRoutes = new ArrayList<SubrouteModel>();
		
		// get subroutes from document
		NodeList subRouteNodes = routeElem.getElementsByTagName("subroute");

    	int subRouteCount = subRouteNodes.getLength();
    	System.out.println(subRouteCount);
    	for (int i = 0; i < subRouteCount; i++) {
    		Node subRoute = subRouteNodes.item(i);
    		if(subRoute.getNodeType() == Node.ELEMENT_NODE){
                Element subRouteElement = (Element)subRoute;
                subRoutes.add(processSubRouteElement(subRouteElement));
    		}
    	}
		// Set all subroute to route model
    	routeModel.setSubroutes(subRoutes);
		
		// Set Route properties
		String totalTime = routeElem.getElementsByTagName("totaltraveltime").item(0).getTextContent();
		routeModel.setTotalTime(Float.parseFloat(totalTime));

		return routeModel;
	}
	
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
