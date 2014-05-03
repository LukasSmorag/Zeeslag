import java.util.*;

public class Spel {
	
	public static boolean gedaan(Vector <Boot> v,Bord b) {
		boolean gedaan = true;
		for (int i=0 ; i<v.size() ; i++){
			if (!v.elementAt(i).vraagGezonken(b)) {
				gedaan = false;
			}
		}
		return gedaan;
	}
	
	
	public static boolean vraagKeuze() {
		Scanner in = new Scanner(System.in);
		String s;
		boolean legestring = false;
		boolean keuzefout = false;
		do
		{
			if(keuzefout) {
				System.out.println("Dit is geen geldige keuze. Geef opnieuw in");
			}
			do
			{
				if (legestring) {
					System.out.println("U heeft niets ingegeven. Gelieve iets in te geven");
				}
				s = in.nextLine();
				if (s.isEmpty()) {
					legestring = true;
				} else {
					legestring = false;
				}	
			}while (legestring);
			s = s.toUpperCase();
			keuzefout = false;
		} while (s.charAt(0) != 'Y' && s.charAt(0) != 'N');
		if (s.charAt(0) == 'Y') {
			return true;
		} else {
			return false;
		}
	}
	
	public static void printVlootStatus(Vector <Boot> vloot, Bord ditbord) {
		for (int i = 0 ; i < vloot.size() ; i++) {
			System.out.println("Boot met lengte "+vloot.elementAt(i).geefLengte()+" gezonken?"+vloot.elementAt(i).vraagGezonken(ditbord));
		}
	}
	
	public static void main(String [] args) {
		System.out.println("************************************");
		System.out.println("*         OPEN-ZEESLAG 2.0         *");
		System.out.println("************************************");
		System.out.println("-------------By LukiSoft------------");
		//initieer spel
		//maak 2 borden aan
		Bord spelerbord = new Bord();
		Bord pcbord = new Bord();
		
		//maak 2 gebruikers aan
		Computerspeler computer = new Computerspeler();
		GebruikerSpeler gebruiker = new GebruikerSpeler();
		
		//maak 2 vloten aan (vector van boten)
		Vector <Boot> pcvloot = new Vector <Boot>(5);
		Boot vliegdekschippc = new Boot(5);
		pcvloot.add(vliegdekschippc);
		Boot slagschippc = new Boot(4);
		pcvloot.add(slagschippc);
		Boot torpedojager1pc = new Boot(3);
		pcvloot.add(torpedojager1pc);
		Boot torpedojager2pc = new Boot(3);
		pcvloot.add(torpedojager2pc);
		Boot mijnenvegerpc = new Boot(2);
		pcvloot.add(mijnenvegerpc);
		
		Vector <Boot> spelervloot = new Vector <Boot>(5);
		Boot vliegdekschipsp = new Boot(5);
		spelervloot.add(vliegdekschipsp);
		Boot slagschipsp = new Boot(4);
		spelervloot.add(slagschipsp);
		Boot torpedojager1sp = new Boot(3);
		spelervloot.add(torpedojager1sp);
		Boot torpedojager2sp = new Boot(3);
		spelervloot.add(torpedojager2sp);
		Boot mijnenvegersp = new Boot(2);
		spelervloot.add(mijnenvegersp);
		
		//boten op bord plaatsen=> computer: automatisch, gebruiker: vragen
		for (int i=0 ; i<5 ; i++) {
			computer.zetWillekeurig(pcvloot.elementAt(i),pcbord);
			pcbord.zetOpBord(pcvloot.elementAt(i));
		}

		System.out.println("Wilt u uw boten zelf plaatsen? Y/N");
		boolean spelerplaatstboten = vraagKeuze();
		if (spelerplaatstboten) {
			for(int i=0 ; i<5 ; i++){
				System.out.println("Huidige toestand van uw bord:");
				spelerbord.print();
				gebruiker.vraagEigenschappen(spelervloot.elementAt(i),spelerbord);
				spelerbord.zetOpBord(spelervloot.elementAt(i));
			}
		} else {
			for (int i=0 ; i<5 ; i++){
				computer.zetWillekeurig(spelervloot.elementAt(i),spelerbord);
				spelerbord.zetOpBord(spelervloot.elementAt(i));
			}
		}
		
		//spel 
		System.out.println("Wilt u beginnen? Y/N");
		boolean beurt = vraagKeuze();
		while (!gedaan(spelervloot,spelerbord) && !gedaan(pcvloot,pcbord)) { 
			if (beurt) {
				System.out.println("====================");
				System.out.println("Gebruiker is aan zet");
				gebruiker.zet(pcbord);
				System.out.println("Bord van computer na uw zet");
				pcbord.printGemaskeerd();
				printVlootStatus(pcvloot,pcbord);
				beurt = false;
			} else {
				System.out.println("===================");
				System.out.println("Computer is aan zet");
				computer.zet(spelerbord, spelervloot);
				System.out.println("Uw bord na computerzet");
				spelerbord.printGemaskeerd();
				printVlootStatus(spelervloot,spelerbord);
				beurt = true;
			}
		}
		
		//slot
		System.out.println("Het spel is afgelopen");
		if (!beurt) {
			System.out.println("U heeft gewonnen");
		} else {
			System.out.println("De computer heeft gewonnen");
		}
	}	
}
