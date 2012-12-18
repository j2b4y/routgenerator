package com.jinengo.routengenerator.model;

public class UserModel {
	// user id
	private int ID;

	//auswahl auto
	private int incomeRange;

	//verheiratet eher gepäck
	private int familyStatus;
	
	//auswahl auto
	private boolean ownsPEV;
	
	//auswahl auto
	private boolean ownsGasCar;
	
	//von anderer Tabelle
	private int carSharingMember;
	
	//dann eher ne zu Fuß Route mit dem Fahrrad fahren...
	private boolean ownsEbike;
	
	//preis umsonst bus
	private boolean publicTransportMember;
	
	//preis bahn ca 1/4
	private int railMembership;
	
	// max distanace
	private int maxDistanceToWalk;
	private int maxDistanceToBike;
	
	// preferences
	private float sustainabilityPreference;
	private float comfortPreference;
	private float costsPreference;
	private float timePreference;
	
	// getter and setter
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(int incomeRange) {
		this.incomeRange = incomeRange;
	}
	public int getFamilyStatus() {
		return familyStatus;
	}
	public void setFamilyStatus(int familyStatus) {
		this.familyStatus = familyStatus;
	}
	public boolean isOwnsPEV() {
		return ownsPEV;
	}
	public void setOwnsPEV(boolean ownsPEV) {
		this.ownsPEV = ownsPEV;
	}
	public boolean isOwnsGasCar() {
		return ownsGasCar;
	}
	public void setOwnsGasCar(boolean ownsGasCar) {
		this.ownsGasCar = ownsGasCar;
	}
	public int getCarSharingMember() {
		return carSharingMember;
	}
	public void setCarSharingMember(int carSharingMember) {
		this.carSharingMember = carSharingMember;
	}
	public boolean isOwnsEbike() {
		return ownsEbike;
	}
	public void setOwnsEbike(boolean ownsEbike) {
		this.ownsEbike = ownsEbike;
	}
	public boolean isPublicTransportMember() {
		return publicTransportMember;
	}
	public void setPublicTransportMember(boolean publicTransportMember) {
		this.publicTransportMember = publicTransportMember;
	}
	public int getRailMembership() {
		return railMembership;
	}
	public void setRailMembership(int railMembership) {
		this.railMembership = railMembership;
	}
	public int getMaxDistanceToWalk() {
		return maxDistanceToWalk;
	}
	public void setMaxDistanceToWalk(int maxDistanceToWalk) {
		this.maxDistanceToWalk = maxDistanceToWalk;
	}
	public int getMaxDistanceToBike() {
		return maxDistanceToBike;
	}
	public void setMaxDistanceToBike(int maxDistanceToBike) {
		this.maxDistanceToBike = maxDistanceToBike;
	}
	public float getSustainabilityPreference() {
		return sustainabilityPreference;
	}
	public void setSustainabilityPreference(float sustainabilityPreference) {
		this.sustainabilityPreference = sustainabilityPreference;
	}
	public float getComfortPreference() {
		return comfortPreference;
	}
	public void setComfortPreference(float comfortPreference) {
		this.comfortPreference = comfortPreference;
	}
	public float getCostsPreference() {
		return costsPreference;
	}
	public void setCostsPreference(float costsPreference) {
		this.costsPreference = costsPreference;
	}
	public float getTimePreference() {
		return timePreference;
	}
	public void setTimePreference(float timePreference) {
		this.timePreference = timePreference;
	}
	
}
