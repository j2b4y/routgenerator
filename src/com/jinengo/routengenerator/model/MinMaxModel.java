package com.jinengo.routengenerator.model;

import java.util.ArrayList;

public class MinMaxModel {
	private float maxCost = 0;
	private float minCost = Float.MAX_VALUE;
	private float maxEmission = 0;
	private float minEmission = Float.MAX_VALUE;
	private float maxTraveltime = 0;
	private float minTraveltime = Float.MAX_VALUE;
	
	public void initialize(ArrayList<RouteModel> routeList) {
		
		for (RouteModel routeModel : routeList) {
			float m = routeModel.getTotalCost();
			float n = m;
			this.maxCost = Math.max(this.maxCost, routeModel.getTotalCost());
			this.maxEmission = Math.max(this.maxEmission, routeModel.getTotalEmission());
			this.maxTraveltime = Math.max(this.maxTraveltime, routeModel.getTotalTime());
			
			this.minCost = Math.min(this.minCost, routeModel.getTotalCost());
			this.minEmission = Math.min(this.minEmission, routeModel.getTotalEmission());
			this.minTraveltime = Math.min(this.minTraveltime, routeModel.getTotalTime());
			
		}
		
	}

	public float getMaxCost() {
		return maxCost;
	}



	public float getMinCost() {
		return minCost;
	}



	public float getMaxEmission() {
		return maxEmission;
	}



	public float getMinEmission() {
		return minEmission;
	}



	public float getMaxTraveltime() {
		return maxTraveltime;
	}



	public float getMinTraveltime() {
		return minTraveltime;
	}

}
