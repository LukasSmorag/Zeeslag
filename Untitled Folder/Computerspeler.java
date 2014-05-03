import java.util.*;

public class Computerspeler extends Speler {
	Vector <Punt> topzetten = new Vector <Punt> (20);
	int vorigehitr;
	int vorigehitk;
	int vorigevlootstatus;
	
	Computerspeler() {
		vorigehitr = 20;
		vorigehitk = 20;
		vorigevlootstatus = 6;
	}
	
	public int vlootStatus (Vector <Boot> v, Bord ditbord) {
		int teller = 0;
		for (int i = 0 ; i < v.size(); i++) {
			if (v.elementAt(i).vraagGezonken(ditbord)) {
				teller++;
			}
		}
		return teller;
	}
	
	public void zetWillekeurig(Boot dezeboot,Bord ditbord) {
		dezeboot.rdir(0);
		do
		{
			if(((int)(Math.random()*2))==0){
				dezeboot.rdir(0);
				dezeboot.kbegin((int)(Math.random() * ((10-dezeboot.geefLengte())+1)));
				dezeboot.rbegin((int)(Math.random() * 10));
			} else {
				dezeboot.rdir(1);
				dezeboot.rbegin((int)(Math.random()*(10-dezeboot.geefLengte())+1));
				dezeboot.kbegin((int)(Math.random()*10));
			}
			dezeboot.kdir(1-dezeboot.geefRdir());
		}while (!ditbord.plaatsbaar(dezeboot));
	}
	
	public void zet(Bord ditbord,Vector <Boot> dezevloot) {
		super.sorteerOpKwaliteit(resterendezetten);
		System.out.println("Begin computerzet. vlootstatus(nu) != vorigevlootstatus?"+(vlootStatus(dezevloot,ditbord) != vorigevlootstatus));
		if (resterendezetten.elementAt(0).geefKwaliteit() <= 34 || vlootStatus(dezevloot,ditbord) != vorigevlootstatus) {
			vorigehitr = defaultwaarde;
			vorigehitk = defaultwaarde;
			kenKwaliteitToeResterendeZetten(ditbord, dezevloot);//vergelijking (hierboven î) is altijd false (behalve 1e keer)
			//werkt ook niet als wel true is
			super.sorteerOpKwaliteit(resterendezetten);
			//System.out.println("resterende zetten werden op kwaliteit herschikt");
			//System.out.println("resterenede zetten na hersortering");
			//super.printEersteResterendeZetten(10);
		} 
		
		int r = resterendezetten.elementAt(0).geef(false);
		int k = resterendezetten.elementAt(0).geef(true);
		//System.out.println("Computerzet was ("+r+","+k+")");
		vorigevlootstatus = vlootStatus(dezevloot, ditbord);
		//System.out.println("vorige vloot status = "+vorigevlootstatus);
		super.zet(ditbord,r, k);
		if (ditbord.veldIsBoot(r,k)) {
			zetOmliggendeCellenBovenaanResterendeZetten(r,k);
			zetAsveldenBovenaanResterendeZetten(r,k);
			vorigehitr = r;
			vorigehitk = k;
		}
		//System.out.println("resterende zetten na afwerken beurt");
		//super.printEersteResterendeZetten(10);
	}
	
	public void zetAsveldenBovenaanResterendeZetten(int r,int k) {
		if (vorigehitr != defaultwaarde) {
			if (vorigehitr-r == 0) {
				for(int t=0 ; t<resterendezetten.size() ; t++) {
					if (resterendezetten.elementAt(t).geef(false) == r && resterendezetten.elementAt(t).geefKwaliteit() > 34 ) {
						resterendezetten.elementAt(t).stelKwaliteit(100);
						super.zetBovenaanEnDeleteOrigineel(resterendezetten, t);
						//System.out.println("As-zet boven gezet");
					}
				}
			} else {
				for(int t=0 ; t<resterendezetten.size() ; t++) {
					if (resterendezetten.elementAt(t).geef(true) == k && resterendezetten.elementAt(t).geefKwaliteit() > 34) {
						resterendezetten.elementAt(t).stelKwaliteit(100);
						super.zetBovenaanEnDeleteOrigineel(resterendezetten,t);
						//System.out.println("As-zet boven gezet");
					}
				}
			}
		}
	}
	
	public void zetOmliggendeCellenBovenaanResterendeZetten(int r,int k) {
		if ((r-1) >= 0 && resterendeZettenBevat(r-1,k)) {
			int p = primus();
			resterendezetten.elementAt(geefPlaats(r-1,k)).stelKwaliteit(p);
			zetBovenaanEnDeleteOrigineel(resterendezetten,geefPlaats(r-1,k));			
			//System.out.println("bovenaan resterendezetten gezet:("+(r-1)+","+k+"), kwaliteit ="+p);
		}
		if ((r+1) < 10 && resterendeZettenBevat(r+1,k)) {
			int p = primus();
			resterendezetten.elementAt(geefPlaats(r+1,k)).stelKwaliteit(p);
			zetBovenaanEnDeleteOrigineel(resterendezetten,geefPlaats(r+1,k));
			//System.out.println("bovenaan resterendezetten gezet:("+(r+1)+","+k+"), kwaliteit = "+p);
		}
		if ((k-1) >= 0 && resterendeZettenBevat(r,k-1)) {
			int p = primus();
			resterendezetten.elementAt(geefPlaats(r,k-1)).stelKwaliteit(p);
			zetBovenaanEnDeleteOrigineel(resterendezetten,geefPlaats(r,k-1));
			//System.out.println("bovenaan resterendezetten gezet:("+r+","+(k-1)+"), kwaliteit = "+p);
		}
		if ((k+1) < 10 && resterendeZettenBevat(r,k+1)) {
			int p = primus();
			resterendezetten.elementAt(geefPlaats(r,k+1)).stelKwaliteit(p);
			zetBovenaanEnDeleteOrigineel(resterendezetten,geefPlaats(r,k+1));
			//System.out.println("bovenaan resterendezetten gezet:("+r+","+(k+1)+"), kwaliteit = "+p);
		}
	}
	
	
	public void kenKwaliteitToeResterendeZetten(Bord ditbord, Vector <Boot> v) {
		int r,k;
		for (int i = 0 ; i < resterendezetten.size() ; i++) {
			r = resterendezetten.elementAt(i).geef(false);
			k = resterendezetten.elementAt(i).geef(true);
			resterendezetten.elementAt(i).stelKwaliteit(bepaalKwaliteitPerVloot(r,k,v,ditbord));
			//System.out.println("kwaliteit ("+r+","+k+")="+bepaalKwaliteitPerVloot(r,k,v,ditbord));
		}
	}
	
	public int bepaalKwaliteitPerVloot(int r, int k, Vector <Boot> v, Bord ditbord) {
		int kwaliteit = 0;
		for (int i = 0 ; i < v.size() ; i++) {
			if (!v.elementAt(i).vraagGezonken(ditbord)) {
				kwaliteit += bepaalKwaliteitPerBoot(r,k,v.elementAt(i),ditbord);
				//System.out.println("# mog. bij l="+v.elementAt(i).geefLengte()+"= "+kwaliteit);
			}
		}
		return kwaliteit;
	}
	
	public int bepaalKwaliteitPerBoot(int r,int k, Boot dezeboot, Bord ditbord) {
		int kwaliteit = 0;
		for (int i = 0 ; i < dezeboot.geefLengte() ; i++) {
			if ((k-i) >= 0) {
				kwaliteit += berekenKwaliteitPerBeginplaats(r,(k-i),dezeboot.geefLengte(),0,ditbord);
			}
			if ((r-i) >= 0) {
				kwaliteit += berekenKwaliteitPerBeginplaats((r-i),k,dezeboot.geefLengte(),1,ditbord);
			}
		}
		return kwaliteit;
	}
	
	public int berekenKwaliteitPerBeginplaats(int r, int k, int lengte, int richting, Bord ditbord)	{
		int rdir,kdir;
		if (richting == 0) {
			rdir = 0;
		} else {
			rdir = 1;
		}
		kdir = 1-rdir;
		int i = 0;
		int kwaliteit= 1;
		while (kwaliteit == 1 && i < lengte) {
			if (super.opBord(r+ i*rdir, k+ i*kdir)) {
				if (ditbord.veldIsGeraakt(r + i*rdir,k + i*kdir)) {
					kwaliteit = 0;
				}
			} else {
				kwaliteit = 0;
			}
			i++;
		}
		return kwaliteit;
	}
}
