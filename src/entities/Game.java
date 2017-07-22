package entities;

import util.InputManager;

public class Game {
	
	private Player computerPlayer;
	private Player userPlayer;
	private Board playerBoard;
	private Board computerBoard;
	private Fleet computerFleet;
	private Fleet playerFleet;
	
	public Game () {
		playerBoard = new Board();
		computerBoard = new Board();
		computerPlayer = new Player(playerBoard, Player.COMPUTERPLAYER);
		userPlayer = new Player(computerBoard, Player.USERPLAYER);
		computerFleet = new Fleet(computerBoard);
		playerFleet = new Fleet(playerBoard);
	}
	
	public void initGame() {		
		playerFleet.registerObserver(computerPlayer);
		
		System.out.println("Wilt u uw boten zelf plaatsen? Y/N");
		boolean spelerplaatstboten = InputManager.getChoice();
		if (spelerplaatstboten) {
			playerBoard.playerPlacesBoats(playerFleet);
		} else {
			playerBoard.computerPlacesBoats (playerFleet);
		}
		computerBoard.computerPlacesBoats (computerFleet);
	}

	public void start() {
		
		initGame();
		//spel 
		System.out.println("Wilt u de eerste zet doen? Y/N");
		boolean beurt = InputManager.getChoice();
		while ( ! playerFleet.isSunken(playerBoard) && ! computerFleet.isSunken(computerBoard)) { 
			if (beurt) {
				System.out.println("====================");
				System.out.println("U bent aan zet");
				userPlayer.play(computerFleet);
				System.out.println("Bord van computer na uw zet");
				computerBoard.printWithMask();
				computerFleet.printVlootStatus(computerBoard);
				beurt = false;
			} else {
				System.out.println("===================");
				System.out.println("Computer is aan zet");
				computerPlayer.play(playerFleet);
				playerFleet.evaluateStatus(playerBoard);
				System.out.println("Uw bord na computerzet");
				playerBoard.printWithMask();
				playerFleet.printVlootStatus(playerBoard);
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

