package com.jinengo.routengenerator.controller;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.jinengo.routengenerator.infrastructure.RouteDecisionMaker;
import com.jinengo.routengenerator.infrastructure.RouteExpander;
import com.jinengo.routengenerator.infrastructure.RouteMapper;
import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.RouteHandler;
import com.jinengo.routengenerator.service.helper.ApiErrorException;
import com.jinengo.routengenerator.service.helper.ApiRequest;
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
	 * @param userModel - user model
	 * @throws ApiErrorException - exception if jinengo api doesn't work correctly
	 */
	public void generateSpecificRoute(UserModel userModel) throws ApiErrorException {
		
		/**
		 *  start route generation process
		 */
		// generate random destinations	 
		ArrayList<String> destinations = DestinationGenerator.getRandomDestinations();
		
		// generate route with jinengo api depending on destinations
		Document doc = ApiRequest.getXmlRouteDocument(destinations.get(0), destinations.get(1), "0");
		
		// process xml api result and save it in route object
		ArrayList<RouteModel> routeList = RouteMapper.processXmlDocument(doc);
		
		// expand properties of each route with properties specific to the user
		RouteExpander routeExpander = new RouteExpander(userModel);
		routeList = routeExpander.expandProperties(routeList);
		
		if(routeList.size() > 0) {
			// decide wich route is fitting most to user model
			RouteDecisionMaker routeDecisionMaker = new RouteDecisionMaker(userModel);
			RouteModel routeModel = routeDecisionMaker.getMostFittingRoute(routeList);
			
			// init route handler to save data, true to generate routes in past, false to generate current day
			RouteHandler routeHandler = new RouteHandler(true);
			routeHandler.saveRoute(routeModel, userModel);
			
		} else {
			// if no route fits to user properties, stop generating route
			String msg = "Es konnte keine passende Route für den Nutzer ermittelt werden.";
			System.out.println(msg);
			return;
		}

	}
	
}
