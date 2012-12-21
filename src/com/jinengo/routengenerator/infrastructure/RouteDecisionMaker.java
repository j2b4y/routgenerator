package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;

public class RouteDecisionMaker {
	UserModel userModel;
	
	public RouteDecisionMaker(UserModel userModel) {
		this.userModel = userModel;
	}
	
	private RouteModel getMostComfortableRoute(ArrayList<RouteModel> routeList) {
		RouteModel resRoute = null;
		
		// TODO: implement comfort
		return getFastestRoute(routeList);
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
	
	private RouteModel getFittingRoute(String userPref, ArrayList<RouteModel> routeList) {
		switch (userPref) {
		case "comf":
			return getMostComfortableRoute(routeList);
		case "sust":
			return getMostSustainableRoute(routeList);
		default: //time
			return getFastestRoute(routeList);
		}
	}
	
	private String getUserPreference(UserModel userModel) {
		float comf = userModel.getComfortPreference();
		float sust = userModel.getSustainabilityPreference();
		float time = userModel.getTimePreference();
		
		if (comf > sust && comf > time) {
			return "comf";
		} else if (sust > comf && sust > time) {
			return "sust";
		} else {
			return "time";
		}
	}
	
	public RouteModel getMostFittingRoute(ArrayList<RouteModel> routeList) {
		String userPref = getUserPreference(this.userModel);
		return getFittingRoute(userPref, routeList);
	}
}
