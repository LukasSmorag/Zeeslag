package smorag.lukas.zeeslag.entities;
import smorag.lukas.zeeslag.util.InputManager;
import smorag.lukas.zeeslag.util.Point;

public class UserPlayer extends Player {
	
	UserPlayer(Board board) {
		super(board);
	}
	
	
	public void doMove () {
		Point p = InputManager.getMoveInput();
		super.play(p.getX(), p.getY());
	}
}
