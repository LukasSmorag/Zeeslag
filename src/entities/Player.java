package entities;
import util.FleetObserver;

import Behavior.ComputerPlayerMove;
import Behavior.UserPlayerMove;
import Exceptions.IllegalPlayerTypeException;
import util.PlayBehavior;
import Behavior.*;
import entities.Board;

public class Player implements FleetObserver {
	
	private Board board;
	private boolean statusChanged;
	private PlayBehavior playBehavior;
	public static final int USERPLAYER = 0;
	public static final int COMPUTERPLAYER = 1;
	
	Player ( Board board , int playerType ) {
		this.statusChanged = false;
		this.board = board;
		setPlayerType(playerType);
	}
	
	public void setPlayerType (int playerType) {
		if (playerType == USERPLAYER) {
			this.playBehavior = new UserPlayerMove (board);
		} else if (playerType == COMPUTERPLAYER) {
			this.playBehavior = new ComputerPlayerMove (board);
		} else {
			throw new IllegalPlayerTypeException();
		}
	}
	
	public boolean isStatusChanged() {
		return statusChanged;
	}
	
	public void play (Fleet fleet){
		playBehavior.move(fleet);
	}

	@Override
	public void update() {
		System.out.println("UPDATE AANGEROEPEN IN SPELER");
		statusChanged = true;
	}

}
