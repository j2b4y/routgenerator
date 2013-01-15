package com.jinengo.routengenerator.service;

import java.sql.SQLException;
import java.util.Random;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.helper.ApiErrorException;
import com.jinengo.routengenerator.service.helper.QueryHandler;

/**
 * 
 * Handles all route requests
 * Save the selected route to database and get max ids from database
 * 
 * @author lars & christopher
 *
 */
public class RouteHandler {
	
private RouteDAO routeDAO;
	
	/**
	 * 
	 * Default constructor
	 * Init user data access object and query handler
	 * 
	 * @param generatePastData - true to generate routes in past, false to generate current day
	 * 
	 */
	public RouteHandler(boolean generatePastData) {
		int daysInThePast = generatePastData ? generateRandomDayInPast() : 0;
		this.routeDAO = new RouteDAO(new QueryHandler(), daysInThePast);
	}
	
	/**
	 * Generate random day in past
	 * 
	 * @return int - random day count
	 */
	private int generateRandomDayInPast() {
		Random rnd = new Random();
		// too generate data somewhere in between the last years
		int daysInThePast = 365 + 30;
		return rnd.nextInt(daysInThePast + 1);
	}
	
	/**
	 * Generate new route id
	 * 
	 * @return id - new route id
	 */
	private int getNewRouteId() {
		int routeId = 0;
		try {
			routeId = this.routeDAO.getMaxRouteId() + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routeId;
	}
	
	/**
	 * Generate new sub route id
	 * 
	 * @return id - new sub route id
	 */
	private int getNewSubRouteId() {
		int routeId = 0;
		try {
			routeId = this.routeDAO.getMaxSubRouteId() + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routeId;
	}
	
	/**
	 * Get comfort rating
	 * 
	 * @param id
	 * @return comfRating
	 */
	public float getComfortRating(int id) {
		float comfRating = 0;
		try {
			comfRating = this.routeDAO.getComfortRating(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comfRating;
	}
	
	/**
	 * Save route to Database
	 * 
	 * @param routeModel
	 * @param userModel
	 * @throws ApiErrorException 
	 */
	public void saveRoute(RouteModel routeModel, UserModel userModel) throws ApiErrorException {
		int routeId = getNewRouteId();
		if(routeModel != null) {
			if(routeModel.getTotalDistance() > 0) {
				routeModel.setID(routeId);
				try {
					this.routeDAO.insertRoute(routeModel);
					for (SubrouteModel subrouteModel : routeModel.getSubroutes()) {
						subrouteModel.setRouteID(routeId);
						subrouteModel.setID(getNewSubRouteId());
						this.routeDAO.insertSubRoute(subrouteModel);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				throw new ApiErrorException("Route enthält negative Werte. Distanz ist: " + routeModel.getTotalDistance() + ". Jinengo Api lieferte kein korrektes Ergebnis. Programm wird beendet!");
			}
		} else {
			throw new ApiErrorException("Route leer. Api liefert kein korrektes Ergebnis. Programm wird beendet!");
		}
		
	}
}
