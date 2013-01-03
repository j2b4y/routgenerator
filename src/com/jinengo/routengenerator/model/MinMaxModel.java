package com.jinengo.routengenerator.model;

import java.util.ArrayList;

/**
 * Store min and max values for cost, emission, time
 * 
 * @author larsschuttemeyer
 *
 */
public class MinMaxModel {
	private float maxCost = 0;
	private float minCost = Float.MAX_VALUE;
	private float maxEmission = 0;
	private float minEmission = Float.MAX_VALUE;
	private float maxTraveltime = 0;
	private float minTraveltime = Float.MAX_VALUE;
	private float maxComfortRating = 0;
	private float minComfortRating = Float.MAX_VALUE;
	private float maxEffectiveTime = 0;
	private float minEffectiveTime = Float.MAX_VALUE;
	
	/**
	 * Init min max values
	 * 
	 * @param routeList
	 */
	public MinMaxModel(ArrayList<RouteModel> routeList) {
		for (RouteModel routeModel : routeList) {
			this.maxCost = Math.max(this.maxCost, routeModel.getTotalCost());
			this.maxEmission = Math.max(this.maxEmission, routeModel.getTotalEmission());
			this.maxTraveltime = Math.max(this.maxTraveltime, routeModel.getTotalTime());
			this.maxComfortRating = Math.max(this.maxComfortRating, routeModel.getComfortRating());
			this.maxEffectiveTime = Math.max(this.maxEffectiveTime, routeModel.getEffectiveTime());
			
			this.minCost = Math.min(this.minCost, routeModel.getTotalCost());
			this.minEmission = Math.min(this.minEmission, routeModel.getTotalEmission());
			this.minTraveltime = Math.min(this.minTraveltime, routeModel.getTotalTime());
			this.minComfortRating = Math.min(this.minComfortRating, routeModel.getComfortRating());
			this.minEffectiveTime = Math.min(this.minEffectiveTime, routeModel.getEffectiveTime());
		}
	}

	public float getMaxEffectiveTime() {
		return maxEffectiveTime;
	}

	public float getMinEffectiveTime() {
		return minEffectiveTime;
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

	public float getMaxComfortRating() {
		return maxComfortRating;
	}

	public float getMinComfortRating() {
		return minComfortRating;
	}

}
