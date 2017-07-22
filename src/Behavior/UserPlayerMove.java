package Behavior;

import entities.Board;
import entities.Fleet;
import util.InputManager;
import util.PlayBehavior;
import util.Point;

public class UserPlayerMove implements PlayBehavior {
	
	private Board board;
	
	public UserPlayerMove (Board board) {
		this.board = board;
	}

	@Override
	public void move( Fleet fleet) {
		Point p = InputManager.getMoveInput();
		board.setFieldHit(p.getX(), p.getY());
	}

}
