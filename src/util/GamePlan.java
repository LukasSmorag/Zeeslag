package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GamePlan {
	
	private static Map <String, Integer> fleetPlan;
	public static Map<String, Integer> getFleetPlan() {
		return fleetPlan;
	}

	private final static int  size = 10;
	
	public GamePlan () {
		fleetPlan = new HashMap <String, Integer>();
		fleetPlan.put("vliegdekschip" , 5 );
		fleetPlan.put("slagschip", 4 );
		fleetPlan.put("onderzeeër", 3 );
		fleetPlan.put("torpedojager", 3 );
		fleetPlan.put("patrouilleschip", 2 );
	}
	
	public Iterator <Map.Entry <String, Integer>> getIterator () {
		return fleetPlan.entrySet().iterator();
	}

	public static int getSize() {
		return size;
	}
}
