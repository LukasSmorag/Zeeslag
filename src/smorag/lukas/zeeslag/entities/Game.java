package smorag.lukas.zeeslag.entities;

import java.util.Iterator;

import smorag.lukas.zeeslag.util.InputManager;
public class Game {
		
		public void printVlootStatus(Fleet fleet, Board board) {
			Iterator <Boat> it = fleet.getIterator();
			while (it.hasNext()) {
				Boat b = it.next();
				System.out.println(String.format("%-20s", b.toString()) + " gezonken? " + b.getSunken(board));
			}
		}

		
		public void start() {
			System.out.println("************************************");
			System.out.println("*         OPEN-ZEESLAG 2.0         *");
			System.out.println("************************************");
			System.out.println("-------------By LukiSoft------------");

			Board playerBoard = new Board();
			Board computerBoard = new Board();
			
			ComputerPlayer computer = new ComputerPlayer(playerBoard);
			UserPlayer user = new UserPlayer(computerBoard);

			//maak 2 vloten aan (vector van boten)
			Fleet computerFleet = new Fleet(computerBoard);
			Fleet playerFleet = new Fleet(playerBoard);
			
			playerFleet.registerObserver(computer);
			
			//boten op bord plaatsen=> computer: automatisch, gebruiker: vragen
			computerFleet.computerPlacesBoats (computerBoard);
			
			System.out.println("Wilt u uw boten zelf plaatsen? Y/N");
			boolean spelerplaatstboten = InputManager.getChoice();
			if (spelerplaatstboten) {
				playerFleet.playerPlacesBoats(playerBoard);
			} else {
				playerFleet.computerPlacesBoats (playerBoard);
			}
			
			//spel 
			System.out.println("Wilt u de eerste zet doen? Y/N");
			boolean beurt = InputManager.getChoice();
			while ( ! playerFleet.isSunken(playerBoard) && ! computerFleet.isSunken(computerBoard)) { 
				if (beurt) {
					System.out.println("====================");
					System.out.println("U bent aan zet");
					user.doMove();
					System.out.println("Bord van computer na uw zet");
					computerBoard.printWithMask();
					printVlootStatus(computerFleet,computerBoard);
					beurt = false;
				} else {
					System.out.println("===================");
					System.out.println("Computer is aan zet");
					computer.doMove(playerFleet);
					playerFleet.evaluateStatus(playerBoard);
					System.out.println("Uw bord na computerzet");
					playerBoard.printWithMask();
					printVlootStatus(playerFleet,playerBoard);
					beurt = true;
				}
			}
			
			//slot
			System.out.println("Het spel is afgelopen");
			if (!beurt) {
				System.out.println("U hebt gewonnen");
			} else {
				System.out.println("De computer heeft gewonnen");
			}
		}	
}

