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

	/**
	 * Return the most comfortable route
	 * 
	 * @param routeList
	 * @return RouteModel - confortable route
	 */
	private RouteModel getMostComfortableRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if (resRoute != null) {
				// TODO: check if comp works correctly
				// TODO: whats the difference between flaot and double
				if (Float.compare(routeModel.getComfortRating(), resRoute.getComfortRating()) > 0) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}
		}
		return resRoute;
	}

	/**
	 * Return the most sustainable route
	 * 
	 * @param routeList
	 * @return RouteModel - sustainable route
	 */
	private RouteModel getMostSustainableRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if (resRoute != null) {
				if (routeModel.getEcoImpactAdvantage() > resRoute.getEcoImpactAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}
		}
		return resRoute;
	}

	/**
	 * Return the fastest route
	 * 
	 * @param routeList
	 * @return RouteModel - fastest route
	 */
	private RouteModel getFastestRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if (resRoute != null) {
				if (routeModel.getTimeAdvantage() > resRoute.getTimeAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}
		}
		return resRoute;
	}

	/**
	 * Return the cheapest route
	 * 
	 * @param routeList
	 * @return RouteModel - cheapest route
	 */
	private RouteModel getCheapestRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if (resRoute != null) {
				if (routeModel.getCostAdvantage() > resRoute.getCostAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}
		}
		return resRoute;
	}

	/**
	 * Calculate which route type is prefered most
	 * 
	 * @param routeList
	 * @param userModel
	 * @return RouteModel - most prefered route
	 */
	private RouteModel getFittingRoute(ArrayList<RouteModel> routeList, UserModel userModel) {
		float comf = userModel.getComfortPreference();
		float sust = userModel.getSustainabilityPreference();
		float time = userModel.getTimePreference();
		float cost = userModel.getCostsPreference();

		if (comf > sust && comf > time && comf > cost) {
			return getMostComfortableRoute(routeList);
		} else if (sust > comf && sust > time && sust > cost) {
			return getMostSustainableRoute(routeList);
		} else if (time > comf && time > sust && time > cost) {
			return getFastestRoute(routeList);
		} else {
			return getCheapestRoute(routeList);
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
			if (prblty != 0) {
				return getFittingRoute(routeList, this.userModel);
			} else {
				// But sometimes select random route
				int rndPos = rnd.nextInt(routeList.size());
				return routeList.get(rndPos);
			}
		}
		return null;
	}
}
