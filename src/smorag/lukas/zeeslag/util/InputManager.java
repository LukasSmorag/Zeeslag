package smorag.lukas.zeeslag.util;
import java.util.Scanner;

public class InputManager {
	
	public static int size = 10;
	
	public static Point getMoveInput () {
		String input = "";
		int x = 0;
		int y = 0;
		boolean inputOK = false;
		Scanner sc = new Scanner (System.in);
			while (! inputOK) {
				String message = null;
				System.out.print("Geef een veld in (rij + kolom): ");
				input = sc.nextLine();
				if (input != null) {
					if (input.length() == 2) {
						if (Character.isDigit( input.charAt(0) ) && Character.isLetter( input.charAt(1)) ) {
							if ( Character.digit(input.charAt(0),10) < size && ((int) input.toUpperCase().charAt(1) - 65) < size ) {
								inputOK = true;
								x = Character.digit(input.charAt(0),10);
								y = (int) input.toUpperCase().charAt(1) - 65;
							} else {
								message = "Veld ligt niet op het bord";
							}
						} else {
							message = "Eerste karakter moet een cijfer zijn, het tweede een letter";
						}
						
					} else {
						message = "Input heeft verkeerde lengte (2 tekens: 1 cijfer, gevolgd door 1 letter)";
					}
				} else {
					message = "Niets ingegeven";
				}
				if (message != null ) {
					System.out.println(message);
				}
			}
		return new Point (x,y);
	}
	
	public static boolean getChoice () {
		String message = "";
		boolean chosen = false;
		Scanner sc = new Scanner (System.in);
		boolean inputOK = false;
		while (! inputOK) {
			String choice = sc.nextLine();
			choice = choice.toUpperCase();
			if (choice != null) {
				if ( choice.equals("Y") || choice.equals("N")) {
					inputOK = true;
					if (choice.equals("Y")) {
						chosen = true;
					} 
				} else {
					message = "ongeldige ingave (Y/y of N/n)";
				}
			} else {
				message = "niets ingegeven";
			}
			if (! message.isEmpty()) {
				System.out.println(message);
			}
		}
		return chosen;
	}
	
	public static int getDirection() {
		String message = "";
		int selected = 0;
		Scanner sc = new Scanner (System.in);
		boolean inputOK = false;
		while (! inputOK) {
			String choice = sc.nextLine();
			if (choice != null) {
				if ( choice.equals("0") || choice.equals("1")) {
					inputOK = true;
					if (choice.equals("1")) {
						selected = 1;
					} 
				} else {
					message = "ongeldige ingave (moet 0 of 1 zijn)";
				}
			} else {
				message = "niets ingegeven";
			}
			if (! message.isEmpty()) {
				System.out.println(message);
			}
		}
		return selected;
	}
}
