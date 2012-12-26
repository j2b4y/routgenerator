package com.jinengo.routengenerator.service;

import java.sql.SQLException;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.helper.QueryHandler;

/**
 * 
 * Handles all route requests to database
 * Save the selected route to database and get max ids from database
 * 
 * @author lars & christopher
 *
 */
public class RouteHandler {
	
private RouteDAO routeDAO;
	
	/**
	 * Default constructor
	 * Init user data access object and query handler
	 */
	public RouteHandler() {
		this.routeDAO = new RouteDAO(new QueryHandler());
	}
	
	/**
	 * Generate new route id
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
	 * @param routeModel
	 * @param userModel
	 */
	public void saveRoute(RouteModel routeModel, UserModel userModel) {
		int routeId = getNewRouteId();
		if(routeModel != null) {
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
			System.out.println("Route leer");
		}
		
	}
}
