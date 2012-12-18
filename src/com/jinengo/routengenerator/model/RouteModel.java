package com.jinengo.routengenerator.model;

import java.util.ArrayList;

/**
 * The RouteModel
 * 
 * @author lars & christopher
 *
 */
public class RouteModel {
	private float userID;
	private float ID;
	private float timeSelected;
	private String departureGeography = "";
	private String departureAddress;
	private float departureTime;
	private String destinationGeography;
	private String destinationAddress;
	private float destinationTime;
	private float totalTime;
	private int needID = 1;
	private boolean luggage;
	private int passengers;
	private float ecoImpactAdvantage;
	private float ecoImpactDisadvantage;
	private float timeAdvantage;
	private float timeDisadvantage;
	private float effectiveTimeAdvantage;
	private float effectiveTimeDisadvantage;
	private float costAdvantage;
	private float costDisadvantage;
	private ArrayList<SubrouteModel> subroutes;
	
	public float getUserID() {
		return userID;
	}
	public void setUserID(float userID) {
		this.userID = userID;
	}
	public float getID() {
		return ID;
	}
	public void setID(float iD) {
		ID = iD;
	}
	public float getTimeSelected() {
		return timeSelected;
	}
	public void setTimeSelected(float timeSelected) {
		this.timeSelected = timeSelected;
	}
	public String getDepartureGeography() {
		return departureGeography;
	}
	public void setDepartureGeography(String departureGeography) {
		this.departureGeography = departureGeography;
	}
	public String getDepartureAddress() {
		return departureAddress;
	}
	public void setDepartureAddress(String departureAddress) {
		this.departureAddress = departureAddress;
	}
	public float getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(float departureTime) {
		this.departureTime = departureTime;
	}
	public String getDestinationGeography() {
		return destinationGeography;
	}
	public void setDestinationGeography(String destinationGeography) {
		this.destinationGeography = destinationGeography;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public float getDestinationTime() {
		return destinationTime;
	}
	public void setDestinationTime(float destinationTime) {
		this.destinationTime = destinationTime;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
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
	public ArrayList<SubrouteModel> getSubroutes() {
		return subroutes;
	}
	public void setSubroutes(ArrayList<SubrouteModel> subroutes) {
		this.subroutes = subroutes;
	}
	
	
}

