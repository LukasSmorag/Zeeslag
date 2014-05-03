package smorag.lukas.zeeslag.entities;
import java.util.Iterator;

public class Board {

	private Field[][] board;
	private static int size = 10;

	Board() {
		board = new Field[size][size];
		for (int r = 0; r < size; r++) {
			for (int k = 0; k < size; k++) {
				board[r][k] = new Field(r, k);
			}
		}
	}
	
	public int getSize () {
		return size;
	}

	public int countNoHitFields(int x, int y, String direction) {
		int ydiv = 0;
		int xdiv = 0;
		int fields = 0;
		boolean end = false;
		switch (direction) {
		case "up":
			xdiv = -1;
			break;
		case "down":
			xdiv = 1;
			break;
		case "left":
			ydiv = -1;
			break;
		case "right":
			ydiv = 1;
			break;
		}
		while (!end) {
			if ( ((x + fields * xdiv) < size) && ((x + fields * xdiv) >= 0) && ((y + fields * ydiv) < size) && ((y + fields * ydiv) >= 0)) {
				if (!board[x + fields * xdiv][y + fields * ydiv].isHit()) {
					fields++;
				} else {
					end = true;
				}
			} else {
				end = true;
			}
		}
		return fields;
	}

	/*
	 * setKwaliteit bepaalt de gok-kwaliteit per veld, per vloot. Per veld word
	 * berekend op hoeveel manieren een boot gezet kan worden.
	 */

	public void setQuality(Fleet fleet) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (!board[x][y].isHit()) {
					int quality = 0;
					int horLeft = countNoHitFields(x, y, "left");
					int horRight = countNoHitFields(x, y, "right");
					int verDown = countNoHitFields(x, y, "down");
					int verUp = countNoHitFields(x,y,"up");
					int horTot = horLeft + horRight - 1;
					int verTot = verUp + verDown -  1;
					int horMin = Math.min(horLeft, horRight);
					int verMin = Math.min(verUp, verDown);
					Iterator <Boat> it = fleet.getIterator();
					while (it.hasNext()) {
						Boat b = (Boat) it.next();
						if (horTot >= b.getLength()) {
							if (horMin < b.getLength()) {
								quality += horMin;
							} else {
								quality += b.getLength();
							}
						}
						if (verTot >= b.getLength()) {
							if (verMin < b.getLength()) {
								quality += verMin;
							} else {
								quality += b.getLength();
							}
						}
					}
					board[x][y].setQuality(quality);
				}
			}
		}
	}

	public void setFieldHit(int x, int y) {
		board[x][y].setHit(true);
	}

	public Field getField(int x, int y) {
		if ( x >= 0 && x < size && y >= 0 && y < size) {
			return board[x][y];
		}
		return null;
	}

	public void placeOnBoard(Boat boat) {
		for (int i = 0; i < boat.getLength(); i++) {
			(board[boat.getXStart() + i * boat.getXDir()][boat.getYStart() + i* boat.getYDir()]).setBoat(true);
		}
	}

	public boolean placable(Boat boat) {
		boolean placable = true;
		int t = 0;
		while (t < boat.getLength() && placable) {
			if (((boat.getXStart() + t * boat.getXDir()) < size)
					&& ((boat.getYStart() + t * boat.getYDir()) < size)) {
				if ((board[boat.getXStart() + t * boat.getXDir()][boat
						.getYStart() + t * boat.getYDir()]).getBoat()) {
					placable = false;
				}
			} else {
				placable = false;
			}
			t++;
		}
		return placable;
	}

	public Boat getPlacableBoat(int length, String name) {
		int x = 0;
		int y = 0;
		int direction = 0;
		boolean placable = false;
		while (!placable) {
			direction = (int) (Math.random() * 2);
			if (direction == 1) {
				x = (int) (Math.random() * (size - length + 1));
				y = (int) (Math.random() * size);
			} else {
				x = (int) (Math.random() * size);
				y = (int) (Math.random() * (size - length + 1));
			}
			placable = placable(new Boat(length, x, y, direction, null));
		}
		return new Boat(length, x, y, direction , name);
	}

	public void print() {
		System.out.println("   A B C D E F G H I J");
		for (int r = 0; r < size; r++) {
			if (r == size) {
				System.out.print(r + "|");
			} else {
				System.out.print(r + " |");
			}
			for (int k = 0; k < size; k++) {
				if (k == (size - 1)) {
					if (board[r][k].getBoat()) {
						System.out.print("X\n");
					} else {
						System.out.print(" \n");
					}
				} else {
					if (board[r][k].getBoat()) {
						System.out.print("X ");
					} else {
						System.out.print("  ");
					}
				}
			}
		}
	}

	public void printWithMask() {
		System.out.println("   A B C D E F G H I J");
		for (int r = 0; r < size; r++) {
			if (r == size) {
				System.out.print(r + "|");
			} else {
				System.out.print(r + " |");
			}
			for (int k = 0; k < size; k++) {
				if (k == (size - 1)) {
					if (board[r][k].isHit()) {
						if (board[r][k].getBoat()) {
							System.out.print("X\n");
						} else {
							System.out.print(" \n");
						}
					} else {
						System.out.print("#\n");
					}
				} else {
					if (board[r][k].isHit()) {
						if (board[r][k].getBoat()) {
							System.out.print("X ");
						} else {
							System.out.print("  ");
						}
					} else {
						System.out.print("# ");
					}
				}
			}
		}
	}
}
