package Behavior;

import smorag.lukas.zeeslag.entities.Board;
import smorag.lukas.zeeslag.entities.Fleet;
import smorag.lukas.zeeslag.util.InputManager;
import smorag.lukas.zeeslag.util.PlayBehavior;
import smorag.lukas.zeeslag.util.Point;

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
