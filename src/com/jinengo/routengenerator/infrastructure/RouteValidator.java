package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;

/**
 * validate if route fits to user properties
 * e.g. user owns the right car and distance is not to long to walk
 * 
 * @author larsschuttemeyer
 *
 */
public class RouteValidator {
	private UserModel userModel;
	
	public RouteValidator(UserModel userModel) {
		this.userModel = userModel;
	}
	
	/**
	 * Check if subroute distance is longer than users max distance to walk / by bike
	 *  
	 * @param routeModel
	 * @return boolean - if distance is to long
	 */
	private boolean distanceToLong(RouteModel routeModel){
		
		for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
			if(subrouteModel.getTransportationID() == 1) {
				if (this.userModel.getMaxDistanceToWalk() < (subrouteModel.getDistance() / 1000)) {
					return true;
				}
			}
			if(subrouteModel.getTransportationID() == 2 || subrouteModel.getTransportationID() == 3) {
				if (this.userModel.getMaxDistanceToBike() < (subrouteModel.getDistance() / 1000)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * validate if user owns the car type used for the route
	 * 
	 * @param routeModel
	 * @return boolean - owns the car
	 */
	private boolean userOwnsTransportation(RouteModel routeModel) {
		if (routeModel.getSubroutes().get(0).getTransportationID() == 7) {
			return this.userModel.isOwnsPEV();
		} 
		// TODO: TESTs
		if (routeModel.getSubroutes().get(0).getTransportationID() >= 4 && routeModel.getSubroutes().get(0).getTransportationID() <= 6) {
			return this.userModel.isOwnsGasCar();
		}
		return true;
	}
	
	/**
	 * validate route
	 *  
	 * @param routeModel
	 * @return boolean - isValid
	 */
	private boolean isValidRoute(RouteModel routeModel) {
		return !distanceToLong(routeModel) && userOwnsTransportation(routeModel);
	}
	
	/**
	 * validate a list of routes
	 * check for each route if its possible alternative depending on user properties
	 * 
	 * @param routeList
	 * @return routeList - a cleaned and validated routelist
	 */
	public ArrayList<RouteModel> getValidatedRoutelist(ArrayList<RouteModel> routeList) {
		ArrayList<RouteModel> validatedRouteList = new ArrayList<RouteModel>(); 
		
		for (RouteModel routeModel : routeList) {
		
			// if route is valid
			if(isValidRoute(routeModel)) {
				validatedRouteList.add(routeModel);
			}
			
		}
		return validatedRouteList;
	}
}
