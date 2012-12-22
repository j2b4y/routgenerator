package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;

public class RouteDecisionMaker {
	UserModel userModel;
	
	public RouteDecisionMaker(UserModel userModel) {
		this.userModel = userModel;
	}
	
	private RouteModel getMostComfortableRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if(resRoute != null){
				if(routeModel.getComfortRating() > resRoute.getComfortRating()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}			
		}
		return resRoute;
	}
	
	private RouteModel getMostSustainableRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if(resRoute != null){
				if(routeModel.getEcoImpactAdvantage() > resRoute.getEcoImpactAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}			
		}
		return resRoute;
	}
	
	private RouteModel getFastestRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if(resRoute != null){
				if(routeModel.getTimeAdvantage() > resRoute.getTimeAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}			
		}
		return resRoute;
	}
	
	private RouteModel getCheapestRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		for (RouteModel routeModel : routeList) {
			if(resRoute != null){
				if(routeModel.getCostAdvantage() > resRoute.getCostAdvantage()) {
					resRoute = routeModel;
				}
			} else {
				resRoute = routeModel;
			}			
		}
		return resRoute;
	}
	
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
	
	
	public RouteModel getMostFittingRoute(ArrayList<RouteModel> routeList) {
		Random rnd = new Random();
		if(routeList.size() > 0) {
			int rndPos = rnd.nextInt(routeList.size());
			// With highest possibility choose the best fitting route
			if (rndPos != 0) {
				return getFittingRoute(routeList, this.userModel);
			} else {
				// But sometimes select random route
				return routeList.get(rndPos);
			}
		}
		
		return null;
	}
}
