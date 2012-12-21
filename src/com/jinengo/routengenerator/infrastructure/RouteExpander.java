package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.MinMaxModel;
import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;

/**
 * Expand route properties with details that are not returned by jinengo api
 * 
 * @author lars + christopher
 *
 */
public class RouteExpander {
	private UserModel userModel;
	private ArrayList<RouteModel> routeList;
	private MinMaxModel minMaxModel;
	
	/**
	 * Default Constructor
	 * init values depending on current route list
	 * 
	 * @param userModel - the current user
	 * @param routeList - the current routes
	 */
	public RouteExpander(UserModel userModel, ArrayList<RouteModel> routeList) {
		this.userModel = userModel;
		this.routeList = routeList;
		this.minMaxModel = new MinMaxModel();
		this.minMaxModel.initialize(routeList);
	}
	
	/**
	 * Calculate Person Count
	 * if Married or in a group, its more probable that more person will drive
	 * 
	 * @param userModel
	 * @return person count
	 */
	private int calcPersonCount(UserModel userModel) {
		Random rnd = new Random();
		int cnt = 1;
		
		// every 4th person travel in group
		boolean isGroup = rnd.nextInt(4) == 0;
		
		// group and married person are 1 to 5 person
    	if(userModel.getFamilyStatus() == 2 || isGroup){
    		// 1 to 5 persons
    		cnt = rnd.nextInt(5) + 1;
    	}
    	
		return cnt;
	}
	
	/**
	 * Calculate if user hast luggage
	 * 
	 * @param userModel
	 * @return boolean - has luggage
	 */
	private boolean hasLuggage(UserModel userModel) {
		Random rnd = new Random();
		int prblty = 0;
		
		// 2 times more probable that married person has luggage
    	if(userModel.getFamilyStatus() == 2){
    		prblty = rnd.nextInt(2);
    	} else {
    		prblty = rnd.nextInt(4);
    	}
    	
		return prblty == 0;
	}
	
	/**
	 * expand subroute
	 * 
	 * @param subroutes
	 * @param userModel
	 * @return expanded subroute
	 */
	private ArrayList<SubrouteModel> expandSubRoute(ArrayList<SubrouteModel> subroutes, UserModel userModel) {
		SubRouteExpander subRouteExpander = new SubRouteExpander(userModel);
		return subRouteExpander.expandProperties(subroutes);
	}
	
	/**
	 * expand the route model with informations
	 * 
	 * @param routeModel
	 * @return routeModel - extended route model
	 */
	private RouteModel expandRouteProperties(RouteModel routeModel) {

		routeModel.setUserID(this.userModel.getID());
		
		// calc passenger and luggage
		routeModel.setLuggage(hasLuggage(this.userModel));		
		routeModel.setPassengers(calcPersonCount(userModel));
		
		// set advantagaes
		routeModel.setCostAdvantage(this.minMaxModel.getMaxCost() - routeModel.getTotalCost());
		routeModel.setCostDisadvantage(routeModel.getTotalCost() - this.minMaxModel.getMinCost());
		routeModel.setEcoImpactAdvantage(this.minMaxModel.getMaxEmission() - routeModel.getTotalEmission());
		routeModel.setEcoImpactDisadvantage(routeModel.getTotalEmission() - this.minMaxModel.getMinEmission());
		routeModel.setTimeAdvantage(this.minMaxModel.getMaxTraveltime() - routeModel.getTotalTime());
		routeModel.setTimeDisadvantage(routeModel.getTotalTime() - this.minMaxModel.getMinTraveltime());
		
		// set departure and destination info
		int lastSub = routeModel.getSubroutes().size() - 1;
		routeModel.setDepartureAddress(routeModel.getSubroutes().get(0).getDepartureAddress());
		routeModel.setDepartureTime(routeModel.getSubroutes().get(0).getDepartureTime());
		routeModel.setDestinationAddress(routeModel.getSubroutes().get(lastSub).getDestinationAddress());
		routeModel.setDestinationTime(routeModel.getSubroutes().get(lastSub).getDestinationTime());
		routeModel.setTimeSelected(routeModel.getSubroutes().get(0).getDepartureTime());
		
		// expand subroute properties of current route
		routeModel.setSubroutes(expandSubRoute(routeModel.getSubroutes(), this.userModel));
		
		return routeModel;
	}
	
	private boolean distanceToLong(RouteModel routeModel){
		boolean distanceToLong = false;
		
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
		
		return distanceToLong;
	}
	
	/**
	 * Expand each route from routelist with properties
	 * 
	 * @return routeList
	 */
	public ArrayList<RouteModel> expandProperties() {
		ArrayList<RouteModel> expandedRouteList = new ArrayList<RouteModel>(); 
		RouteModel expRoute;
		
		for (RouteModel routeModel : this.routeList) {
			
			expRoute = expandRouteProperties(routeModel);
			
			// if subroute is foot/bike, check that distance is not longer
			// than user preferences
			if(!distanceToLong(expRoute)) {
				expandedRouteList.add(expRoute);
			}
			
		}
		
		return expandedRouteList;
	}
}
