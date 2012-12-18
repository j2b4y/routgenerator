package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.jinengo.routengenerator.api.ApiRequest;
import com.jinengo.routengenerator.model.UserModel;

/**
 * Creates a user specific route
 * 
 * @author lars & christopher
 *
 */
public class SpecificRouteGenerator {
	
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
		RouteProcessor routeProcessor = new RouteProcessor(userModel);
		// its possible to process more than one doc with the same user model
		routeProcessor.processXmlDocument(doc);
		
		// todo decide wich subroute is fitting most
	}
	
}
