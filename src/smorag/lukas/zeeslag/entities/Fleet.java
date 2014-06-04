package smorag.lukas.zeeslag.entities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import smorag.lukas.zeeslag.util.Subject;
import smorag.lukas.zeeslag.util.FleetObserver;

public class Fleet implements Subject {
	
	private List <Boat> fleet;
	private List <FleetObserver> observers;
	int status;
	private static final String SUNKEN = "GEZONKEN SCHEPEN:";
	private static final String NOTSUNKEN = "TE ZINKEN SCHEPEN:";
	
	public Fleet (Board bord) {
		status = 0;
		fleet  = new ArrayList <Boat> ();
		observers = new ArrayList <FleetObserver>();
	}
	
	public void addBoat (Boat boat) {
		fleet.add(boat);
	}
	
	public boolean isSunken (Board board) {
		boolean isSunken = true;
		for (Boat b : fleet) {
			if ( ! b.getSunken (board)) {
				isSunken = false;
				break;
			}
		}
		return isSunken;
	}
	
	public void evaluateStatus (Board board) {
		int currentStatus = 0;
		for (Boat b : fleet) {
			if ( ! b.getSunken (board)) {
				currentStatus++;
			}
		}
		if (currentStatus != status) {
			notifyObserver();
			status = currentStatus;
		}	
	}
	
	public Iterator <Boat> getIterator() {
		return fleet.iterator();
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

	public void printVlootStatus(Board board) {
		
		StringBuffer sunken = new StringBuffer();
		StringBuffer notSunken = new StringBuffer();
		
		for (Boat b : fleet) {
			if (b.getSunken(board)) {
				sunken.append(b.toString() + "\n");
			} else {
				notSunken.append(b.toString() + "\n");
			}
		}
		if (!notSunken.toString().equals("")) {
			System.out.println(NOTSUNKEN);
			System.out.println(notSunken.toString());
		}
		if (!sunken.toString().equals("")) {
			System.out.println(SUNKEN);
			System.out.println(sunken.toString());	
		}
	}
	
}
