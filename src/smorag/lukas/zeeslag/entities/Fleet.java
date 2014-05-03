package smorag.lukas.zeeslag.entities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import smorag.lukas.zeeslag.util.InputManager;
import smorag.lukas.zeeslag.util.Point;
import smorag.lukas.zeeslag.util.Subject;
import smorag.lukas.zeeslag.util.FleetObserver;

public class Fleet implements Subject{
	
	Map <String, Integer> fleetPlan;
	List <Boat> fleet;
	private List <FleetObserver> observers;
	private int status;
	
	public Fleet (Board bord) {
		status = 0;
		fleet  = new ArrayList <Boat> ();
		observers = new ArrayList <FleetObserver>();
		fleetPlan = new HashMap <String, Integer> ();
		fleetPlan.put("vliegdekschip" , 5 );
		fleetPlan.put("slagschip", 4 );
		fleetPlan.put("onderzeeër", 3 );
		fleetPlan.put("torpedojager", 3 );
		fleetPlan.put("patrouilleschip", 2 );
	}
	
	public void computerPlacesBoats (Board board) {
		for ( Map.Entry <String, Integer> me : fleetPlan.entrySet()) {
			Boat b = board.getPlacableBoat(me.getValue() , me.getKey());
			board.placeOnBoard(b);
			fleet.add(b);
			status++;
		}
		//bord.print();
	}
	
	public void playerPlacesBoats (Board board) {
		for (Map.Entry <String, Integer> me : fleetPlan.entrySet()) {
			System.out.println("Huidige toestand van uw bord:");
			board.print();
			Boat b = askPorpertieInput(board, me.getKey() , me.getValue());
			board.placeOnBoard(b);
			fleet.add(b);
			status++;
		}
		board.print();
	}
	
	public Boat askPorpertieInput (Board bord, String naam, int lengte) {
		boolean plaatsfout = false;
		int richting = 0;
		Boat b;
		System.out.println("Geef de coordinaten in van een boot in met lengte " + lengte);
		do {
			if (plaatsfout) {
				System.out.println("Boot kruist andere boot of valt buiten het veld! Geef opnieuw in.");
			}
			System.out.println("Geef het beginveld van de boot");
			Point p = InputManager.getMoveInput();
			System.out.println("Geef de richting van de boot (0 = horizontaal, 1 = vertikaal)");
			richting = InputManager.getDirection();
			b = new Boat(lengte, p.getX(), p.getY(), richting, naam);
			plaatsfout = true;
		} while (! bord.placable(b));
		return b;
	}
	
	
	public boolean isSunken (Board board) {
		boolean isSunken = true;
		for (Boat b : fleet){
			if ( ! b.getSunken (board)) {
				isSunken = false;
				break;
			}
		}
		return isSunken;
	}

	public Iterator <Boat> getIterator () {
		return fleet.iterator();
	}
	
	public void evaluateStatus (Board board) {
		
		int currentStatus = 0;
		
		for (Boat b : fleet){
			if ( ! b.getSunken (board)) {
				currentStatus++;
			}
		}
		
		if (currentStatus != status) {
			notifyObserver();
			status = currentStatus;
		}	
	}


	@Override
	public void registerObserver(FleetObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(FleetObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObserver() {
		for (FleetObserver vo : observers) {
			vo.update();
		}
	}
	
}
