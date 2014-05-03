import java.util.Scanner;


public class GebruikerSpeler extends Speler {
	
	public boolean InputCijfers (String s)
	{
		boolean cijfers = true;
		int i = 0;
		while (i<s.length() && cijfers) {
			if (!Character.isDigit(s.charAt(i))) {
				cijfers = false;
			}
			i++;
		}
		return cijfers;
	}
	
	public int vraagRij ()
	{
		int rij;
		boolean fout = false;
		System.out.println("rij:");
		Scanner in = new Scanner(System.in);
		String s;
		do
		{
			if (fout) {
				System.out.println("Ongeldige ingave. Geef opnieuw in...");
			}
			s = vraagNietLegeString();
			if (s.length() > 2) {
				s = s.substring(0,2);
			}
			if (!InputCijfers(s)) {
				fout = true;
			} else {
				fout=false;
			}
		} while (fout);
		//in.close();
		rij = Integer.parseInt(s);
		return rij;
	}
	
	public String vraagNietLegeString()
	{
		Scanner in = new Scanner (System.in);
		String s;
		boolean geeningave = false;
		do
		{
			if (geeningave) {
				System.out.println("U heeft niets ingegeven, gelieve opnieuw in te geven");
			}
			s = in.nextLine();
			if (s.isEmpty()) {
				geeningave=true;
			} else {
				geeningave=false;
			}
		}while (geeningave);
		return s;
	}

	public int vraagKolom()
	{
		char k;
		String s;
		System.out.println("kolom:");
		boolean kfout = false;
		do
		{
			if(kfout) {
				System.out.println("Ongeldige ingave. Geef opnieuw in...");
			}
			s = vraagNietLegeString();
			if (!Character.isLetter(s.charAt(0))) {
				kfout = true;
			} else {
				kfout = false;
			}
		} while (kfout);
		s = s.toUpperCase();
		k = s.charAt(0);
		return (int)k-65;
	}
	
	public int vraagRichting()
	{
		int richting;
		String s;
		boolean richtingfout = false;
		System.out.println("Geef de richting in: 0=HORIZONTAAL, 1=VERTIKAAL");
		do
		{
			if(richtingfout){
				System.out.println("Ongeldige ingave. Geef opnieuw in");
			}
			s = vraagNietLegeString();
			if(s.charAt(0) != '0' && s.charAt(0) != '1'){
				richtingfout = true;
			} else {
				richtingfout = false;
			}
		}while (richtingfout);
		s = s.substring(0,1);
		richting = Integer.parseInt(s);
		return richting;
	}
	
	public void vraagEigenschappen(Boot dezeboot, Bord ditbord)
	{
		int rmax = 9;
		int kmax = 9;
		boolean plaatsfout = false;
		System.out.println("Geef een boot in met lengte "+dezeboot.geefLengte());
		do
		{
			if(plaatsfout){
				System.out.println("Boot kruist andere boot! Geef opnieuw in.");
			}
			int richting = vraagRichting();
			if (richting == 0) {
				kmax = 10-dezeboot.geefLengte();
				dezeboot.rdir(0);
			} else {
				rmax = 10-dezeboot.geefLengte();
				dezeboot.rdir(1);
			}
			System.out.println("Geef de startrij van de boot in");
			boolean rijfout = false;
			do
			{
				if (rijfout) {
					System.out.println("Boot valt (gedeeltelijk) buiten het veld. Geef opnieuw een rij");
				}
				dezeboot.rbegin(vraagRij());
				rijfout = true;
			} while (dezeboot.geefRbegin() > rmax);
			boolean kolomfout = false;
			System.out.println("Geef de startkolom van de boot in");
			do
			{
				if (kolomfout) {
					System.out.println("Boot valt (gedeeltelijk) buiten het veld. Geef opnieuw een rij");
				}
				dezeboot.kbegin(vraagKolom());
				kolomfout = true;
			} while (dezeboot.geefKbegin()>kmax);
			plaatsfout=true;
			dezeboot.kdir(1-dezeboot.geefRdir());
		}while(!ditbord.plaatsbaar(dezeboot));
	}
	
	public void zet (Bord ditbord)
	{
		int r,k;
		System.out.println("Geef uw zet in");
		boolean rijfout = false;
		do
		{
			if (rijfout) {
				System.out.println("Rij valt buiten het veld, geef opnieuw in");
			}
			r = vraagRij();
			rijfout = true;
		} while (r > 9);
		boolean kolomfout = false;
		do
		{
			if (kolomfout) {
				System.out.println("Kolom valt buiten het veld, geef opnieuw in");
			}
			k = vraagKolom();
			kolomfout = true;
		} while (k > 9);
		System.out.println("uw zet was ("+r+","+k+")");
		super.zet(ditbord, r, k);
	}
}
