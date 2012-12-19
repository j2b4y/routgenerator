package com.jinengo.routengenerator.model;

import org.joda.time.DateTime;

/**
 * The SubrouteModel
 * 
 * @author lars & christopher
 *
 */
public class SubrouteModel {
	
	private int userID;
	private int routeID;
	private int ID;
	private int transportationID;
	private String departureGeography;
	private String departureAddress;
	private DateTime departureTime;
	private String destinationGeography;
	private String destinationAddress;
	private DateTime destinationTime;
	private float distance;
	private int time;
	private int timeUsable;
	private float costs;
	private float ecoImpact;
	private String contextInformation;
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getTransportationID() {
		return transportationID;
	}
	public void setTransportationID(int transportationID) {
		this.transportationID = transportationID;
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
	public DateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(DateTime departureTime) {
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
	public DateTime getDestinationTime() {
		return destinationTime;
	}
	public void setDestinationTime(DateTime destinationTime) {
		this.destinationTime = destinationTime;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getTimeUsable() {
		return timeUsable;
	}
	public void setTimeUsable(int timeUsable) {
		this.timeUsable = timeUsable;
	}
	public float getCosts() {
		return costs;
	}
	public void setCosts(float costs) {
		this.costs = costs;
	}
	public float getEcoImpact() {
		return ecoImpact;
	}
	public void setEcoImpact(float ecoImpact) {
		this.ecoImpact = ecoImpact;
	}
	public String getContextInformation() {
		return contextInformation;
	}
	public void setContextInformation(String contextInformation) {
		this.contextInformation = contextInformation;
	}
		
}
