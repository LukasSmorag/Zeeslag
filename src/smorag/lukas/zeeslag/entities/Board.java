package smorag.lukas.zeeslag.entities;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Exceptions.IllegalDirectionException;
import smorag.lukas.zeeslag.util.Directions;
import smorag.lukas.zeeslag.util.GamePlan;
import smorag.lukas.zeeslag.util.InputManager;
import smorag.lukas.zeeslag.util.Point;

public class Board {

	private Field[][] board;
	private int size;
	private GamePlan gamePlan = new GamePlan();
	private static final String CURRENTSITUATION = 		"Geef de coordinaten van een boot met lengte %d";
	private static final String PLACEERROR = 			"Boot kruist andere boot of valt buiten het veld! Geef opnieuw in.";
	private static final String ASKBEGINFIELD = 		"Geef het beginveld van de boot";
	private static final String ASKDIRECTION = 			"Geef de richting van de boot (0 = horizontaal, 1 = vertikaal)";
	private static final String CURRENTBOARDSITUATION = "Huidige toestand van uw bord:";

	public Board() {
		this.size = GamePlan.getSize();
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

	public int countNoHitFields(int x, int y, Directions direction) {
		int ydiv = 0;
		int xdiv = 0;
		int fields = 0;
		boolean end = false;
		switch (direction) {
		case UP:
			xdiv = -1;
			break;
		case DOWN:
			xdiv = 1;
			break;
		case LEFT:
			ydiv = -1;
			break;
		case RIGHT:
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
	 * setKwaliteit bepaalt de gok-kwaliteit per veld, per vloot. Per veld wordt
	 * berekend op hoeveel manieren een boot gezet kan worden.
	 */

	public void setQuality(Fleet fleet) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (!board[x][y].isHit()) {
					int quality = 0;
					int horLeft = countNoHitFields(x, y, Directions.LEFT);
					int horRight = countNoHitFields(x, y, Directions.RIGHT);
					int verDown = countNoHitFields(x, y, Directions.DOWN);
					int verUp = countNoHitFields(x, y, Directions.UP);
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

	public void placeOnBoard (Boat boat) {
		for (int i = 0; i < boat.getLength(); i++) {
			(board[boat.getXStart() + i * boat.getXDir()][boat.getYStart() + i* boat.getYDir()]).setBoat(true);
		}
	}

	public boolean placable (Boat boat) {
		boolean placable = true;
		int t = 0;
		while (t < boat.getLength() && placable) {
			if (((boat.getXStart() + t * boat.getXDir()) < size) && ((boat.getYStart() + t * boat.getYDir()) < size)) {
				if ((board[boat.getXStart() + t * boat.getXDir()][boat.getYStart() + t * boat.getYDir()]).getBoat()) {
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
		for (int x = 0 ; x < size ; x++) {
			if (x == size) {
				System.out.print(x + "|");
			} else {
				System.out.print(x + " |");
			}
			for (int y = 0; y < size; y++) {
				if (y == (size - 1)) {
					if (board[x][y].isHit()) {
						if (board[x][y].getBoat()) {
							System.out.print("X\n");
						} else {
							System.out.print(" \n");
						}
					} else {
						System.out.print("#\n");
					}
				} else {
					if (board[x][y].isHit()) {
						if (board[x][y].getBoat()) {
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

	public void playerPlacesBoats (Fleet fleet) {
		Iterator <Entry<String, Integer>> fleetIterator = gamePlan.getIterator();
		while (fleetIterator.hasNext()) {
			System.out.println(CURRENTBOARDSITUATION);
			print();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map.Entry <String, Integer> me = (Map.Entry) fleetIterator.next();
			Boat b = askPorpertyInput(me.getKey() , me.getValue());
			placeOnBoard(b);
			fleet.addBoat(b);
			fleet.status++;
		}
		print();
	}
	
	public void computerPlacesBoats (Fleet fleet) {
		Iterator <Entry<String, Integer>> fleetIterator = gamePlan.getIterator();
		while (fleetIterator.hasNext()) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map.Entry <String, Integer> me = (Map.Entry) fleetIterator.next();
			Boat b = getPlacableBoat(me.getValue() , me.getKey());
			placeOnBoard(b);
			fleet.addBoat(b);
			fleet.status++;
		}
		//print();
	}

	public Boat askPorpertyInput (String name, int length) {
		boolean placingError = false;
		int direction = 0;
		Boat b = null;
		System.out.println(String.format(CURRENTSITUATION, length));
		do {
			if (placingError) {
				System.out.println(PLACEERROR);
			}
			System.out.println(ASKBEGINFIELD);
			Point p = InputManager.getMoveInput();
			System.out.println(ASKDIRECTION);
			direction = InputManager.getDirection();
			if (direction == Boat.HORIZONTAL) {
				b = new Boat(length, p.getX(), p.getY(), Boat.HORIZONTAL, name);
			} else if (direction == Boat.VERTICAL) {
				b = new Boat(length, p.getX(), p.getY(), Boat.VERTICAL, name);
			} else {
				throw new IllegalDirectionException();
			}
			placingError = true;
		} while (! placable(b));
		return b;
	}
}
