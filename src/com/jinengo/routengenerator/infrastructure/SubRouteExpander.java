package com.jinengo.routengenerator.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import com.jinengo.routengenerator.model.SubrouteModel;
import com.jinengo.routengenerator.model.UserModel;
import com.jinengo.routengenerator.service.RouteHandler;

/**
 * Expand subRoute Properties with self calculated values
 * 
 * @author lars + christopher
 *
 */
public class SubRouteExpander {
	private UserModel userModel;
	private RouteHandler routeHandler;
	
	public SubRouteExpander(UserModel userModel) {
		this.userModel = userModel;
		this.routeHandler = new RouteHandler();
	}
	
	/**
	 * generate a transportation id depending on user properties
	 * 
	 * @param rawTransportationType
	 * @param userModel
	 * @return
	 */
	private SubrouteModel generateTransportationID(String rawTransportationType, UserModel userModel, SubrouteModel subrouteModel) {
		Random rnd = new Random();
		
		switch (rawTransportationType) {
		case "Car":
			if(userModel.getCarSharingMember() != -1) {
				subrouteModel.setTransportationID(userModel.getCarSharingMember());
			} else {
				if(userModel.getIncomeRange() < 3) {
					subrouteModel.setTransportationID(4); // Kleinwagen
				}
				if(userModel.getIncomeRange() == 3) {
					subrouteModel.setTransportationID(5); // Mittelklasse-Wagen
				}
				if(userModel.getIncomeRange() > 3) {
					subrouteModel.setTransportationID(6); // Oberklasse-Wagen
				}
			}
			break;		
		case "Train":
			// generate random train id (15-21)
			int rndTrain = rnd.nextInt(7) + 15;
			
			// 50 percent propability that income range decides train
			boolean incRange = rnd.nextInt(2) == 0;
			
			// generate random int between 0 and 1 to variant train kind
			int rndSubTrain = rnd.nextInt(1);
			
			if(incRange) {
				if(userModel.getIncomeRange() < 3) {
					subrouteModel.setTransportationID(17 + rndSubTrain); // ME or MEr			
				}
				if(userModel.getIncomeRange() == 3) {
					subrouteModel.setTransportationID(15 + rndSubTrain); // RE or REx
				}
				if(userModel.getIncomeRange() > 3) {
					subrouteModel.setTransportationID(20 + rndSubTrain); // IC or ICE
				}
			}
			subrouteModel.setTimeUsable(subrouteModel.getTime());
			break;
		
		case "Foot":
			boolean driveBike; 
			int bikeId;
			int footId = 1;
			
			if (userModel.isOwnsEbike()) {
				driveBike = rnd.nextInt(2) == 0;
				bikeId = 3;
			} else {
				driveBike = rnd.nextInt(4) == 0;
				bikeId = 2;
			}
			
			if(driveBike) {
				subrouteModel.setTransportationID(bikeId);
			} else {
				subrouteModel.setTransportationID(footId);
			}
			
			break;
			
		case "ElectricCar":
			if(userModel.isOwnsPEV()) {
				int electrCarId = 7;
				subrouteModel.setTransportationID(electrCarId);
			} else {
				// if user did not own electric car, generate
				subrouteModel = generateTransportationID("Car", userModel, subrouteModel);
			}
			break;
			
		case "Bus":
			int busId = 12;
			int busVariant = rnd.nextInt(3) + busId;
			subrouteModel.setTransportationID(busVariant);
		default:
			break;
		}
		
		subrouteModel.setComfortRating(this.routeHandler.getComfortRating(subrouteModel.getTransportationID()));
		
		return subrouteModel;
	}
	
	/**
	 * Set properties to subrouteModel
	 * 
	 * @param subrouteModel
	 * @return subrouteModel - with extended properties
	 */
	private SubrouteModel expandSubrouteProperties(SubrouteModel subrouteModel) {
		
		subrouteModel.setUserID(this.userModel.getID());
		subrouteModel = generateTransportationID(subrouteModel.getTrasportationRaw(), this.userModel, subrouteModel);
		// TODO: contextInformation;
		return subrouteModel;
	}
	
	/**
	 * iterate over each subroute to expand properties
	 * 
	 * @param subroutes
	 * @return subroute - with extended properties
	 */
	public ArrayList<SubrouteModel> expandProperties(ArrayList<SubrouteModel> subroutes) {
		ArrayList<SubrouteModel> expandedSubrouteList = new ArrayList<SubrouteModel>();
		
		for (SubrouteModel subrouteModel : subroutes) {
			expandedSubrouteList.add(expandSubrouteProperties(subrouteModel));
		}
		return expandedSubrouteList;
	}
}
