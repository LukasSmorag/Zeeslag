package smorag.lukas.zeeslag.entities;

public class Player {
	
	Board board;
	
	Player ( Board board ) {
		this.board = board;
	}
	
	public void play (int x, int y ){
		board.setFieldHit(x, y);
	}

}
