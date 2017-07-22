package util;


public interface Subject {
	
	public void registerObserver(FleetObserver O);
	
	public void removeObserver(FleetObserver O);
	
	public void notifyObserver();

}
