package com.jinengo.routengenerator.model;

import java.util.ArrayList;

import org.joda.time.DateTime;

/**
 * The RouteModel
 * 
 * @author lars & christopher
 *
 */
public class RouteModel {
	private int userID;
	private int ID;
	private DateTime timeSelected;
	private String departureAddress;
	private DateTime departureTime;
	private String destinationAddress;
	private DateTime destinationTime;
	private float totalTime;
	private float totalDistance;
	private float totalEmission;
	private float totalCost;
	private int needID = 1;
	private boolean luggage = false;
	private int passengers;
	private float ecoImpactAdvantage;
	private float ecoImpactDisadvantage;
	private float timeAdvantage;
	private float timeDisadvantage;
	private float effectiveTime;
	private float effectiveTimeAdvantage;
	private float effectiveTimeDisadvantage;
	private float costAdvantage;
	private float costDisadvantage;
	private float comfortRatingAdvantage;
	private float comfortRatingDisadvantage;
	private float comfortRating;
	private ArrayList<SubrouteModel> subroutes;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public DateTime getTimeSelected() {
		return timeSelected;
	}
	public void setTimeSelected(DateTime timeSelected) {
		this.timeSelected = timeSelected;
	}
	public String getDepartureAddress() {
		return departureAddress;
	}
	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}
	public DateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(DateTime departureTime) {
		this.departureTime = departureTime;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public DateTime getDestinationTime() {
		return destinationTime;
	}
	public void setDestinationTime(DateTime destinationTime) {
		this.destinationTime = destinationTime;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	public float getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}
	public float getTotalEmission() {
		return totalEmission;
	}
	public void setTotalEmission(float totalEmission) {
		this.totalEmission = totalEmission;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public int getNeedID() {
		return needID;
	}
	public void setNeedID(int needID) {
		this.needID = needID;
	}
	public boolean isLuggage() {
		return luggage;
	}
	public void setLuggage(boolean luggage) {
		this.luggage = luggage;
	}
	public int getPassengers() {
		return passengers;
	}
	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}
	public float getEcoImpactAdvantage() {
		return ecoImpactAdvantage;
	}
	public void setEcoImpactAdvantage(float ecoImpactAdvantage) {
		this.ecoImpactAdvantage = ecoImpactAdvantage;
	}
	public float getEcoImpactDisadvantage() {
		return ecoImpactDisadvantage;
	}
	public void setEcoImpactDisadvantage(float ecoImpactDisadvantage) {
		this.ecoImpactDisadvantage = ecoImpactDisadvantage;
	}
	public float getTimeAdvantage() {
		return timeAdvantage;
	}
	public void setTimeAdvantage(float timeAdvantage) {
		this.timeAdvantage = timeAdvantage;
	}
	public float getTimeDisadvantage() {
		return timeDisadvantage;
	}
	public void setTimeDisadvantage(float timeDisadvantage) {
		this.timeDisadvantage = timeDisadvantage;
	}
	public float getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(float effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public float getEffectiveTimeAdvantage() {
		return effectiveTimeAdvantage;
	}
	public void setEffectiveTimeAdvantage(float effectiveTimeAdvantage) {
		this.effectiveTimeAdvantage = effectiveTimeAdvantage;
	}
	public float getEffectiveTimeDisadvantage() {
		return effectiveTimeDisadvantage;
	}
	public void setEffectiveTimeDisadvantage(float effectiveTimeDisadvantage) {
		this.effectiveTimeDisadvantage = effectiveTimeDisadvantage;
	}
	public float getCostAdvantage() {
		return costAdvantage;
	}
	public void setCostAdvantage(float costAdvantage) {
		this.costAdvantage = costAdvantage;
	}
	public float getCostDisadvantage() {
		return costDisadvantage;
	}
	public void setCostDisadvantage(float costDisadvantage) {
		this.costDisadvantage = costDisadvantage;
	}
	public float getComfortRatingAdvantage() {
		return comfortRatingAdvantage;
	}
	public void setComfortRatingAdvantage(float comfortRatingAdvantage) {
		this.comfortRatingAdvantage = comfortRatingAdvantage;
	}
	public float getComfortRatingDisadvantage() {
		return comfortRatingDisadvantage;
	}
	public void setComfortRatingDisadvantage(float comfortRatingDisadvantage) {
		this.comfortRatingDisadvantage = comfortRatingDisadvantage;
	}
	public float getComfortRating() {
		return comfortRating;
	}
	public void setComfortRating(float comfortRating) {
		this.comfortRating = comfortRating;
	}
	public ArrayList<SubrouteModel> getSubroutes() {
		return subroutes;
	}
	public void setSubroutes(ArrayList<SubrouteModel> subroutes) {
		this.subroutes = subroutes;
	}
	
}

