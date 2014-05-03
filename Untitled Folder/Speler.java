import java.util.*;

public class Speler {
	protected Vector <Punt> resterendezetten = new Vector <Punt> (100);
	final protected int defaultwaarde = 20; 
	
	Speler() {
		for (int i=0 ; i<100 ; i++) {
			resterendezetten.add(new Punt(i/10,i%10));
		}
	}
	
	public int primus()	{
		int beste;
		if (resterendezetten.elementAt(0).geefKwaliteit() > 34 && resterendezetten.elementAt(0).geefKwaliteit() < 100) {
			beste = resterendezetten.elementAt(0).geefKwaliteit() + 1;
		} else {
			beste = 35;
		}
		return beste;
	}
	
	public int geefPlaats(int r ,int  k) {
		boolean bevat = false;
		int i = -1;
		while ( i < resterendezetten.size() && !bevat) {
			i++;
			if (resterendezetten.elementAt(i).geef(false) == r && resterendezetten.elementAt(i).geef(true) == k ) {
				bevat = true;
			}
		}
		//System.out.println("Het element ("+r+","+k+") bevond zich op plaats:"+i);
		return i;	
	}
	
	public void printEersteResterendeZetten(int i) {
		System.out.println("-------------------------");
		System.out.println("Eerste "+i+" resterende zetten:");
		for (int t = 0 ; t < i ; t++) {
			System.out.println("plaats "+t+": ("+resterendezetten.elementAt(t).geef(false)+","+resterendezetten.elementAt(t).geef(true)+")="+resterendezetten.elementAt(t).geefKwaliteit());
		}
	}
	
	public void zetBovenaanEnDeleteOrigineel(Vector <Punt> v, int i) {
		v.insertElementAt(v.elementAt(i), 0);
		v.removeElementAt(i+1);
	}
	
	public boolean resterendeZettenBevat(int r,int k) {
		boolean bevat = false;
		int i = 0;
		while ( i < resterendezetten.size() && !bevat) {
			if (resterendezetten.elementAt(i).geef(false) == r && resterendezetten.elementAt(i).geef(true) == k ) {
				bevat = true;
			}
			i++;
		}
		return bevat;
	}
	
	public void sorteerOpKwaliteit(Vector <Punt> v) {
		for (int t = 0 ; t < v.size(); t++) {
			int max = 0;
			int locatie = 0;
			for (int i = 0; i < v.size()-t ; i++) {
				if (v.elementAt(i).geefKwaliteit() > max) {
					max = v.elementAt(i).geefKwaliteit();
					locatie = i;
				}
				
			}
			v.add(v.elementAt(locatie));
			v.removeElementAt(locatie);			
		}
	}
	
	public boolean opBord(int r,int k) {
		boolean opbord = true;
		if (r < 0 || r > 9 || k < 0 || k > 9) {
			opbord = false;
		}
		return opbord;
	}
	
	public void zet (Bord ditbord, int r, int k ){
		ditbord.stelVeldGeraakt(r, k);
		resterendezetten.removeElementAt(geefPlaats(r, k));
	}
}
