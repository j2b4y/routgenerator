package com.jinengo.routengenerator;
import com.jinengo.routengenerator.controller.MainController;


public class RouteGenerator {

	/**
	 * @param args
	 */	
	public static void main(String[] args) {
		
		System.out.println("Starten sie eine Aktion durch Eingabe eine der folgenden Zahlen:");
		System.out.println("1: Routen generieren und in DB schreiben! ");
		System.out.println("2: User-Preferences auslesen und Routen zuordnen");
		
		// get keyboard input
		String eingabe = "1";
//	    try {
//	    	InputStreamReader input  = new InputStreamReader(System.in);
//		    BufferedReader keyboardInput = new BufferedReader(input);
//			eingabe = keyboardInput.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	    // generate routes
	    switch (eingabe) {
		case "1":
			MainController mc = new MainController();
			mc.generateData();
			break;

		default:
			break;
		}
	}
}
