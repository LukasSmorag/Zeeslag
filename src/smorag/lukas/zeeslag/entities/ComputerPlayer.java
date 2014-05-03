package smorag.lukas.zeeslag.entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import smorag.lukas.zeeslag.util.FleetObserver;

public class ComputerPlayer extends Player implements FleetObserver {

	private List<Field> remainingFields = new ArrayList<Field>();
	private List<Field> preferableFields = new ArrayList<Field>();
	private int prevHitX;
	private int prevHitY;
	private static final int defaultValue = -1;
	private boolean statusChanged;

	ComputerPlayer(Board board) {
		super(board);
		setPrevHitX (defaultValue);
		setPrevHitY(defaultValue);
		statusChanged = false;
		for (int x = 0; x < board.getSize(); x++) {
			for (int y = 0; y < board.getSize(); y++) {
				remainingFields.add(board.getField(x, y));
			}
		}
	}

	public int fleetStatus( List<Boat> fleet) {
		int counter = 0;
		for (Boat b : fleet) {
			if (b.getSunken(board)) {
				counter++;
			}
		}
		return counter;
	}

	public void doMove (Fleet fleet) {
		Field playField = null;
		board.setQuality(fleet);
		Collections.sort(remainingFields);
		if (preferableFields.isEmpty() || statusChanged) {
			if (statusChanged) {
				statusChanged = false;
				preferableFields.clear();
			}
			System.out.println("random zet");
			playField = remainingFields.get(0);
			if (playField.getBoat()) {
				putSurroundingFieldsInPreferable(playField.getX(), playField.getY());
				prevHitX = playField.getX();
				setPrevHitY(0);
				setPrevHitY(playField.getY());
			} 
		} else {
			System.out.println("voorkeurzet");
			playField = preferableFields.get(0);
			if (playField.getBoat()) {
				putSurroundingFieldsInPreferable(playField.getX(), playField.getY());
				if (playField.getX() == prevHitX) {
					deleteNonAxisFields(playField, "horizontal");
				} else {
					deleteNonAxisFields(playField, "vertical");
				}
				setPrevHitX(defaultValue);
				setPrevHitY(defaultValue);
			} 
		}

		if (preferableFields.contains(playField)) {
			preferableFields.remove(playField);
		}
		remainingFields.remove(playField);
		
		super.play(playField.getX(), playField.getY());
		System.out.println("Computerzet was (" + playField.getX() + "," + (char)(playField.getY() + 65) + ")");
		
	}
	
	public void addToPreferableFieldsIfNoHit (int x , int y) {
		Field v = board.getField(x, y);
		if (v != null) {
			if (!v.isHit()) {
				preferableFields.add(v);
				System.out.println("In voorkeurZetten gezet:(" + x + "," + y + ")");
			}
		}
	}

	public void putSurroundingFieldsInPreferable(int x, int y) {
		addToPreferableFieldsIfNoHit( x - 1 , y );
		addToPreferableFieldsIfNoHit( x + 1 , y );
		addToPreferableFieldsIfNoHit( x , y - 1 );
		addToPreferableFieldsIfNoHit( x , y + 1 );
	}
	
	public void deleteNonAxisFields ( Field checkVeld , String direction ) {
		System.out.println("Deleten niet-as velden...");
		if (direction.equals("horizontal")) {
			for (int i = 0 ; i < remainingFields.size() ; i++) {
				Field v = remainingFields.get(i);
				if (checkVeld.getX() != v.getX()) {
					preferableFields.remove(v);
				}
			}
		} else {
			for (int i = 0 ; i < remainingFields.size() ; i++) {
				Field v = remainingFields.get(i);
				if (checkVeld.getY() != v.getY()) {
					preferableFields.remove(v);
				}
			}
		}
	}

	@Override
	public void update() {
		System.out.println("UPDATE AANGEROEPEN IN COMPUTERSPELER");
		statusChanged = true;
	}

	public int getPrevHitY() {
		return prevHitY;
	}

	public void setPrevHitY(int prevHitY) {
		this.prevHitY = prevHitY;
	}
	
	public int getPrevHitX() {
		return prevHitX;
	}

	public void setPrevHitX(int prevHitX) {
		this.prevHitX = prevHitX;
	}

	
}
