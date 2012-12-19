package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.UserModel;

public class RouteExpander {
	private UserModel userModel;
	private ArrayList<RouteModel> routeList;
	private float maxCost = 0;
	private float minCost = Float.MAX_VALUE;
	private float maxEmission = 0;
	private float minEmission = Float.MAX_VALUE;
	private float maxTraveltime = 0;
	private float minTraveltime = Float.MAX_VALUE;
	
	public RouteExpander(UserModel userModel, ArrayList<RouteModel> routeList) {
		this.userModel = userModel;
		this.routeList = routeList;
	}
	
	private void setMinValue(String type) {
		
		for (RouteModel routeModel : this.routeList) {
			
			switch (type) {
			case "costs":
				this.minCost = Math.min(this.minCost, routeModel.getTotalCost());
				break;
				
			case "emission":
				this.minEmission = Math.min(this.minEmission, routeModel.getTotalEmission());
				break;
				
			case "time":
				this.minTraveltime = Math.min(this.minTraveltime, routeModel.getTotalTime());
				break;
				
			default:
				break;
			}
			
		}
		
	}
	
	private void setMaxValue(String type) {
		
		for (RouteModel routeModel : this.routeList) {
			
			switch (type) {
			case "costs":
				this.maxCost = Math.max(this.maxCost, routeModel.getTotalCost());
				break;
				
			case "emission":
				this.maxEmission = Math.max(this.maxEmission, routeModel.getTotalEmission());
				break;
				
			case "time":
				this.maxTraveltime = Math.max(this.maxTraveltime, routeModel.getTotalTime());
				break;
				
			default:
				break;
			}
			
		}
		
	}
	
	private void initMaxMinValues() {
		setMaxValue("costs");
		setMaxValue("emission");
		setMaxValue("time");
		
		setMinValue("costs");
		setMinValue("emission");
		setMinValue("time");
	}
	
	private RouteModel expandRouteProperties(RouteModel routeModel) {
		routeModel.setID(this.userModel.getID());
		routeModel.setCostAdvantage(this.maxCost - routeModel.getTotalCost());
		routeModel.setCostDisadvantage(routeModel.getTotalCost() - this.minCost);
		routeModel.setEcoImpactAdvantage(this.maxEmission - routeModel.getTotalEmission());
		routeModel.setEcoImpactDisadvantage(routeModel.getTotalEmission() - this.minEmission);
		routeModel.setTimeAdvantage(this.maxTraveltime - routeModel.getTotalTime());
		routeModel.setTimeDisadvantage(routeModel.getTotalTime() - this.minTraveltime);
		
		int len = routeModel.getSubroutes().size();
		routeModel.setDepartureAddress(routeModel.getSubroutes().get(0).getDepartureAddress());
		routeModel.setDepartureTime(routeModel.getSubroutes().get(0).getDepartureTime());
		routeModel.setDestinationAddress(routeModel.getSubroutes().get(len - 1).getDestinationAddress());
		routeModel.setDestinationTime(routeModel.getSubroutes().get(len - 1).getDestinationTime());
		
		return routeModel;
	}
	
	public ArrayList<RouteModel> expandProperties() {
		ArrayList<RouteModel> expandedRouteList = new ArrayList<RouteModel>(); 
		initMaxMinValues();
		
		for (RouteModel routeModel : this.routeList) {
			expandedRouteList.add(expandRouteProperties(routeModel));
		}
		
		return expandedRouteList;
	}
}
