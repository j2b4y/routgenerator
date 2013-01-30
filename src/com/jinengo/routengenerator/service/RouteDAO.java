package com.jinengo.routengenerator.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.service.helper.MSSQLConnectionHandler;
import com.jinengo.routengenerator.service.helper.QueryHandler;

/**
 * Data Access Object for (Sub)Routes
 * Perform Database request to save route and get route data
 * 
 * @author lars & christopher
 *
 */
public class RouteDAO {
	private QueryHandler queryHandler;
	private String tableRoute;
	private String tableSubRoute;
	private int daysInPast = 0;
	
	/**
	 * Default constructor to init values
	 * @param queryHandler
	 */
	public RouteDAO(QueryHandler queryHandler, int daysInPast) {
		this.daysInPast = daysInPast;
		this.queryHandler = queryHandler;
		this.tableRoute = "A_SOURCE_Route";
		this.tableSubRoute = "A_SOURCE_Subroute";
	}
		
	/**
	 * Get max route id from database
	 * @return max route id
	 * @throws SQLException
	 */
	public int getMaxRouteId() throws SQLException {
		int maxId = 0;
		
		String sqlQuery = "SELECT MAX(ROUTE_ID) maxID FROM " + this.tableRoute + " ";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			maxId = res.getInt("maxID");
		}
		return maxId;
	}
	
	/**
	 * Get max subroute id from database
	 * 
	 * @return max subroute id
	 * @throws SQLException
	 */
	public int getMaxSubRouteId() throws SQLException {
		int maxId = 0;
		
		String sqlQuery = "SELECT MAX(SUBROUTE_ID) maxID FROM " + this.tableSubRoute + " ";
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			maxId = res.getInt("maxID");
		}
		return maxId;
	}
	
	/**
	 * Get comfort rating for a given id
	 * @param id - user Id
	 * @return confortRating
	 * @throws SQLException
	 */
	public float getComfortRating(int id) throws SQLException {
		float comfRating = 0;
		
		String sqlQuery = "SELECT comfortRating FROM Transportation WHERE id = " + id;
		
		ResultSet res = this.queryHandler.selectSomething(sqlQuery);
		if(res.next()) {
			comfRating = res.getFloat("comfortRating");
		}

		return comfRating;
	}
	
	/**
	 * Insert route model into database
	 * @param routeModel
	 * @throws SQLException
	 */
	public void insertRoute(RouteModel routeModel) throws SQLException{
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		Statement query;
		query = conn.createStatement();
          
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
	    int need = 1;
	    int isProcessed = 0;
	    int luggage = routeModel.isLuggage() ? 1 : 0;
	    
        String sql = "INSERT INTO " + this.tableRoute + " (Route_ID, Jinengo_ID, Route_ZeitAuswahl, Route_Abfahrt, Route_Ankunft, Route_Startzeit, Route_Endzeit, Route_Distanz, Route_Kosten, Route_CO2Emission, Route_Reisezeit, Route_Beduerfnis, Route_Gepaeck, Route_Passagiere, effectiveTimeAdvantage, effectiveTimeDisadvantage, ecoImpactAdvantage, ecoImpactDisadvantage, timeAdvantage, timeDisadvantage, costsAdvantage, costsDisadvantage, comfortAdvantage, comfortDisadvantage, isProcessed) VALUES ("
            		+ routeModel.getID() + ", "
            		+ routeModel.getUserID() + ", "
            		+ "'" + routeModel.getDepartureTime().toString(fmt) + "', "
            		+ "'" + routeModel.getDepartureAddress() + "', "
            		+ "'" + routeModel.getDestinationAddress() + "', "
            		+ "'" + routeModel.getDepartureTime().minusDays(this.daysInPast).toString(fmt) + "', "
            		+ "'" + routeModel.getDestinationTime().minusDays(this.daysInPast).toString(fmt) + "', "
            		+ routeModel.getTotalDistance() + ", "
            		+ routeModel.getTotalCost() + ", "
            		+ routeModel.getTotalEmission() + ", "
            		+ routeModel.getTotalTime() + ", "
            		+ need + ", "
            		+ luggage + ", "
            		+ routeModel.getPassengers() + ", "
            		+ routeModel.getEffectiveTimeAdvantage() + ", "
            		+ routeModel.getEffectiveTimeDisadvantage() + ", "
            		+ routeModel.getEcoImpactAdvantage() + ", "
            		+ routeModel.getEcoImpactDisadvantage() + ", "
            		+ routeModel.getTimeAdvantage() + ", "
            		+ routeModel.getTimeDisadvantage() + ", "
            		+ routeModel.getCostAdvantage() + ", "
            		+ routeModel.getCostDisadvantage() + ", "
            		+ routeModel.getComfortRatingAdvantage() + ", "
            		+ routeModel.getComfortRatingDisadvantage() + ", "
            		+ isProcessed
            		+ ");";
       
        query.executeUpdate(sql);
		
	}
	
	/**
	 * Insert SubroteModel into database
	 * @param subrouteModel
	 * @throws SQLException
	 */
	public void insertSubRoute(SubrouteModel subrouteModel) throws SQLException{
		
		Connection conn = MSSQLConnectionHandler.getInstance();
		Statement query;
		query = conn.createStatement();
          
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
	    int periodInMinutes = new Period(subrouteModel.getDepartureTime(), subrouteModel.getDestinationTime()).toStandardMinutes().getMinutes();
	    
        String sql = "INSERT INTO " + this.tableSubRoute + "(Subroute_ID, Route_ID, Verkehrsmitteltyp_ID, Subroute_Start, Subroute_End, Subroute_Startzeit, Subroute_Endzeit, Subroute_Distanz, Subroute_Kosten, Subroute_CO2Emission, Subroute_ReisezeitNutzbar, Subroute_Reisezeit, Subroute_Verkehrsmittel) VALUES ("
            		+ subrouteModel.getID() + ", "
            		+ subrouteModel.getRouteID() + ", "
            		+ subrouteModel.getTransportationID() + ", "
            		+ "'" + subrouteModel.getDepartureAddress() + "', "
            		+ "'" + subrouteModel.getDestinationAddress() + "', "
            		+ "'" + subrouteModel.getDepartureTime().minusDays(this.daysInPast).toString(fmt) + "', "
            		+ "'" + subrouteModel.getDestinationTime().minusDays(this.daysInPast).toString(fmt) + "', "
            		+ subrouteModel.getDistance() + ", "
            		+ subrouteModel.getCosts() + ", "
            		+ subrouteModel.getEcoImpact() + ", "
            		+ subrouteModel.getTimeUsable() + ", "
            		+ periodInMinutes + ", "
            		+ "'" + subrouteModel.getTrasportationRaw() + "'"
            		+ ");";
        System.out.println(sql);
       
        query.executeUpdate(sql);
		
	}
}
