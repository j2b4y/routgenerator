package com.jinengo.routengenerator.service;

import java.sql.SQLException;

import com.jinengo.routengenerator.model.RouteModel;
import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.helper.QueryHandler;

/**
 * 
 * 
 * @author lars & christopher
 *
 */
public class RouteHandler {
	
private RouteDAO routeDAO;
	
	/**
	 * default constructor
	 * init user data access object and query handler
	 */
	public RouteHandler() {
		this.routeDAO = new RouteDAO(new QueryHandler());
	}
	
	private int getNewRouteId() {
		int routeId = 0;
		try {
			routeId = this.routeDAO.getMaxRouteId() + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routeId;
	}
	
	private int getNewSubRouteId() {
		int routeId = 0;
		try {
			routeId = this.routeDAO.getMaxSubRouteId() + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return routeId;
	}
	
	public float getComfortRating(int id) {
		float comfRating = 0;
		try {
			comfRating = this.routeDAO.getComfortRating(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comfRating;
	}
	
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
