package com.jinengo.routengenerator.model;

/**
 * The User Model
 * 
 * @author lars & christopher
 *
 */
public class UserModel {
	
	private int ID;
	private int incomeRange;
	private int familyStatus;
	private boolean ownsPEV;
	private boolean ownsGasCar;
	private int carSharingMember;
	private boolean ownsEbike;
	private boolean publicTransportMember;
	private int railMembership;
	private int maxDistanceToWalk;
	private int maxDistanceToBike;
	private float sustainabilityPreference;
	private float comfortPreference;
	private float costsPreference;
	private float timePreference;
	private boolean isActiveUser;
	
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
	public boolean isActiveUser() {
		return isActiveUser;
	}
	public void setActiveUser(boolean isActiveUser) {
		this.isActiveUser = isActiveUser;
	}
}
