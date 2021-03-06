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
		this.routeHandler = new RouteHandler(true);
	}
	
	/**
	 * generate a transportation id depending on user properties
	 * 
	 * @param rawTransportationType
	 * @param userModel
	 * @return subrouteModel
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
			
			// 50 percent propability that income range decides train
			boolean incRange = rnd.nextInt(2) == 0;
			
			// generate random int between 0 and 1 to variant train kind
			
			
			if(incRange) {
				if(userModel.getIncomeRange() <= 3) {
					float prblty = rnd.nextFloat();
					if(prblty < 0.3){
						// ME
						subrouteModel.setTransportationID(17);
					} else if (prblty < 0.6) {
						// MEr
						subrouteModel.setTransportationID(18);
					} else if (prblty < 0.8) {
						// RE
						subrouteModel.setTransportationID(15);
					} else if (prblty <= 0.9) {
						// REx
						subrouteModel.setTransportationID(16);
					} else {
						// NWB
						subrouteModel.setTransportationID(19);
					}
				}
				if(userModel.getIncomeRange() > 3) {
					int rndSubTrain = rnd.nextInt(2);
					subrouteModel.setTransportationID(20 + rndSubTrain); // IC or ICE
				}
			} else {
				// generate random train id (15-21)
				int rndTrain = rnd.nextInt(7) + 15;
				subrouteModel.setTransportationID(rndTrain);
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
			int electrCarId = 7;
			subrouteModel.setTransportationID(electrCarId);
			
			break;
			
		case "Bus":
			int busId = 12;
			int omnibusId = 13;
			
			// every 4th time take omnibus
			int busVariant = rnd.nextInt(4);
			if ( busVariant > 0 ) {
				subrouteModel.setTransportationID(busId);
			} else {
				subrouteModel.setTransportationID(omnibusId);
			}
			
			break;
			
		case "Tram":
			int tramId = 14;
			subrouteModel.setTransportationID(tramId);
			
			break;	
			
		default:
			System.out.println("unbekanntes Verkehrsmittel");
			subrouteModel.setTransportationID(-1);
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
