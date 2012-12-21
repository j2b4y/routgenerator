package com.jinengo.routengenerator.controller;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.jinengo.routengenerator.api.ApiRequest;
import com.jinengo.routengenerator.infrastructure.RouteDecisionMaker;
import com.jinengo.routengenerator.infrastructure.RouteExpander;
import com.jinengo.routengenerator.infrastructure.RouteMapper;
import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.RouteHandler;
import com.jinengo.routengenerator.service.helper.DestinationGenerator;

/**
 * Creates a user specific route
 * 
 * @author lars & christopher
 *
 */
public class RouteController {
	
	/**
	 * generate route depending on UserModel
	 * 
	 * @param userModel
	 */
	public void generateSpecificRoute(UserModel userModel) {
		// generate random destinations
		DestinationGenerator dg = new DestinationGenerator(); 
		ArrayList<String> destinations = dg.getRandomDestinations();
		
		// generate route depending on destinations
		ApiRequest apiRequest = new ApiRequest();
		Document doc = apiRequest.getXmlRouteDocument(destinations.get(0), destinations.get(1), "0");
		
		// process xml result and save it in route object
		RouteMapper routeProcessor = new RouteMapper();
		ArrayList<RouteModel> routeList = routeProcessor.processXmlDocument(doc);
		
		// expand properties of each route with properties specific to the user
		RouteExpander routeExpander = new RouteExpander(userModel, routeList);
		routeList = routeExpander.expandProperties();
		
		// decide wich route is fitting most to user model
		RouteDecisionMaker routeDecisionMaker = new RouteDecisionMaker(userModel);
		RouteModel routeModel = routeDecisionMaker.getMostFittingRoute(routeList);
		
		// write route to database
		RouteHandler routeHandler = new RouteHandler();
		routeHandler.saveRoute(routeModel, userModel);
	}
	
}
