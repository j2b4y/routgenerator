package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.Period;

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
	
	/**
	 * Default Constructor
	 * init values depending on current route list
	 * 
	 * @param userModel - the current user
	 */
	public RouteExpander(UserModel userModel) {
		this.userModel = userModel;
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
		
		// group and married person are 1 to 6 person
    	if(userModel.getFamilyStatus() == 2 || isGroup){
    		// 1 to 6 persons
    		cnt = rnd.nextInt(6) + 1;
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
	 * calc total costs if changed due to bahn card user
	 * 
	 * @param routeModel
	 * @return routeModel - with total costs
	 */
	private float calcTotalCosts(RouteModel routeModel) {
		float totalCosts = 0;
		for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
			totalCosts += subrouteModel.getCosts();
		}
		return totalCosts;
	}
	
	/**
	 * Set train cost depending on bahn card
	 * 
	 * @param routeModel
	 * @return routeModel - with resetted costs
	 */
	private RouteModel setTrainCosts(RouteModel routeModel) {
		ArrayList<SubrouteModel> subrouteList = new ArrayList<SubrouteModel>();
		
		for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
			if ( subrouteModel.getTrasportationRaw().equalsIgnoreCase("Train") ) {
			
				switch (this.userModel.getRailMembership()) {
					// BahnCard 25 (2. Klasse) or BahnCard 50 (1. Klasse)
					case 2:
					case 6:
						subrouteModel.setCosts((float) (subrouteModel.getCosts() * 0.75));
						break;
					
					// BahnCard 50 (2. Klasse)
					case 3:
						subrouteModel.setCosts((float) (subrouteModel.getCosts() * 0.50));
						break;
					
					// Bahn Card 100
					case 4:
					case 7:
						subrouteModel.setCosts(0);
						break;
	
					default:
						break;
				}
			}
			subrouteList.add(subrouteModel);
		}
		routeModel.setSubroutes(subrouteList);
		
		// calculate new total costs
		routeModel.setTotalCost(calcTotalCosts(routeModel));
		return routeModel;
	}
	
	/**
	 * expand the route model with informations
	 * 
	 * @param routeModel
	 * @return routeModel - extended route model
	 */
	private RouteModel expandRouteProperties(RouteModel routeModel) {
		
		routeModel.setUserID(this.userModel.getID());
		
		// set departure and destination info
		routeModel.setTimeSelected(routeModel.getSubroutes().get(0).getDepartureTime());
		
		routeModel.setDepartureAddress(routeModel.getSubroutes().get(0).getDepartureAddress());
		routeModel.setDepartureTime(routeModel.getSubroutes().get(0).getDepartureTime());
		
		int lastSub = routeModel.getSubroutes().size() - 1;
		routeModel.setDestinationAddress(routeModel.getSubroutes().get(lastSub).getDestinationAddress());
		routeModel.setDestinationTime(routeModel.getSubroutes().get(lastSub).getDestinationTime());
		
		// calc total time
		int totalTimeInMinutes = new Period(routeModel.getDepartureTime(), routeModel.getDestinationTime()).toStandardMinutes().getMinutes();
		routeModel.setTotalTime(totalTimeInMinutes);
		
		// expand subroute properties of current route
		routeModel.setSubroutes(expandSubRoute(routeModel.getSubroutes(), this.userModel));
		
		// calc comfort rating and effective time (depending on subroutes)
		routeModel.setComfortRating(calcComfortRating(routeModel));
		routeModel.setEffectiveTime(calcEffectiveTime(routeModel));
		
		// reset total costs and subroute cost if user owns "bahn card"
		routeModel = setTrainCosts(routeModel);
		
		// set advantages
		return routeModel;
	}
	
	/**
	 * Calc comfort rating over all subroutes
	 * 
	 * @param routeModel
	 * @return comfortRating
	 */
	private float calcComfortRating(RouteModel routeModel) {
		float comfortRating = 0;
		for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
			comfortRating += subrouteModel.getComfortRating();
		}
		
		return comfortRating / routeModel.getSubroutes().size();
	}
	
	/**
	 * Calc effective tme over all subroutes
	 * 
	 * @param routeModel
	 * @return effectiveTime
	 */
	private float calcEffectiveTime(RouteModel routeModel) {
		float effectiveTime = 0;
		for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
			effectiveTime += subrouteModel.getTimeUsable();
		}
		
		return effectiveTime;
	}
	
	/**
	 * Calculate advantages and disadvantages depending in min/max values
	 * 
	 * @param minMaxModel
	 * @param routeModel
	 * @return extended routeModel
	 */
	private RouteModel calculateAdvantages(MinMaxModel minMaxModel, RouteModel routeModel) {
		// Cost
		routeModel.setCostAdvantage(minMaxModel.getMaxCost() - routeModel.getTotalCost());
		routeModel.setCostDisadvantage(routeModel.getTotalCost() - minMaxModel.getMinCost());
		
		// Eco Impact
		routeModel.setEcoImpactAdvantage(minMaxModel.getMaxEmission() - routeModel.getTotalEmission());
		routeModel.setEcoImpactDisadvantage(routeModel.getTotalEmission() - minMaxModel.getMinEmission());
		
		// Travel Time
		routeModel.setTimeAdvantage(minMaxModel.getMaxTraveltime() - routeModel.getTotalTime());
		routeModel.setTimeDisadvantage(routeModel.getTotalTime() - minMaxModel.getMinTraveltime());	
		
		// Comfort
		routeModel.setComfortRatingAdvantage(routeModel.getComfortRating() - minMaxModel.getMinComfortRating());
		routeModel.setComfortRatingDisadvantage(minMaxModel.getMaxComfortRating() - routeModel.getComfortRating());	
		
		// Effective Time
		routeModel.setEffectiveTimeAdvantage(routeModel.getEffectiveTime() - minMaxModel.getMinEffectiveTime());
		routeModel.setEffectiveTimeDisadvantage(minMaxModel.getMaxEffectiveTime() - routeModel.getEffectiveTime());	
		
		return routeModel;
	}
	
	/**
	 * Expand each route from routelist with properties
	 * 
	 * @return routeList
	 */
	public ArrayList<RouteModel> expandProperties(ArrayList<RouteModel> routeList) {
		RouteValidator validator = new RouteValidator(this.userModel);
		ArrayList<RouteModel> expandedRouteList = new ArrayList<RouteModel>(); 		
		
		// calc passengers and luggage
		int passengers = calcPersonCount(this.userModel);
		boolean luggage = hasLuggage(this.userModel);		
		
		// expand route properties
		for (RouteModel routeModel : routeList) {
			routeModel.setLuggage(luggage);		
			routeModel.setPassengers(passengers);
			expandedRouteList.add(expandRouteProperties(routeModel));		
		}
		
		// validate each route, if it fits to user details
		expandedRouteList = validator.getValidatedRoutelist(expandedRouteList);
		
		// calculate advantages and disadvantages
		MinMaxModel minMaxModel = new MinMaxModel(expandedRouteList);
		for (int i = 0; i < expandedRouteList.size(); i++) {
			expandedRouteList.set(i, calculateAdvantages(minMaxModel, expandedRouteList.get(i)));
		}

		return expandedRouteList;
	}
}
