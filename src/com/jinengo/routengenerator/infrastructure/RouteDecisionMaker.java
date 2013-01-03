package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;

/**
 * Get the most fitting route depending on user preferences
 * 
 * @author lars
 * 
 */
public class RouteDecisionMaker {
	UserModel userModel;

	/**
	 * Default Constructor
	 * 
	 * @param userModel
	 */
	public RouteDecisionMaker(UserModel userModel) {
		this.userModel = userModel;
	}
	
	public enum RoutePreference {
		COMF, SUST, FAST, CHEAP
	}

	/**
	 * Return the most preferred route
	 * 
	 * @param routeList
	 * @return RouteModel - comfortable route
	 */
	private RouteModel getMostPreferredRoute(ArrayList<RouteModel> routeList, RoutePreference pref) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if (resRoute != null) {
				switch (pref) {
				case COMF:
					if (routeModel.getComfortRating() > resRoute.getComfortRating()) {
						resRoute = routeModel;
					}
					break;
				case SUST:
					if (routeModel.getEcoImpactAdvantage() > resRoute.getEcoImpactAdvantage()) {
						resRoute = routeModel;
					}
					break;
				case FAST:
					if (routeModel.getTimeAdvantage() > resRoute.getTimeAdvantage()) {
						resRoute = routeModel;
					}
					break;
				
				case CHEAP:
					if (routeModel.getCostAdvantage() > resRoute.getCostAdvantage()) {
						resRoute = routeModel;
					}
					break;
				default:
					break;
				}
				
			} else {
				resRoute = routeModel;
			}
		}
		return resRoute;
	}




	/**
	 * Calculate which route type is preferred most
	 * 
	 * @param routeList
	 * @param userModel
	 * @return RouteModel - most preferred route
	 */
	private RouteModel getFittingRoute(ArrayList<RouteModel> routeList, UserModel userModel) {
		float comf = userModel.getComfortPreference();
		float sust = userModel.getSustainabilityPreference();
		float time = userModel.getTimePreference();
		float cost = userModel.getCostsPreference();

		if (comf > sust && comf > time && comf > cost) {
			return getMostPreferredRoute(routeList, RoutePreference.COMF);
		} else if (sust > comf && sust > time && sust > cost) {
			return getMostPreferredRoute(routeList, RoutePreference.SUST);
		} else if (time > comf && time > sust && time > cost) {
			return getMostPreferredRoute(routeList, RoutePreference.FAST);
		} else {
			return getMostPreferredRoute(routeList, RoutePreference.CHEAP);
		}
	}

	/**
	 * Calculate the most fitting route
	 * 
	 * @param routeList
	 * @return RouteModel - route fitting most to user
	 */
	public RouteModel getMostFittingRoute(ArrayList<RouteModel> routeList) {
		Random rnd = new Random();
		if (routeList.size() > 0) {		
			int prblty = rnd.nextInt(4);
			// With highest possibility choose the most fitting route
			if (prblty > 0) {
				return getFittingRoute(routeList, this.userModel);
			} else {
				// But every 4th time select random route
				int rndPos = rnd.nextInt(routeList.size());
				return routeList.get(rndPos);
			}
		}
		return null;
	}
}
