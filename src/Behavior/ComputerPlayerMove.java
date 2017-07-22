package Behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.PlayBehavior;
import entities.*;


public class ComputerPlayerMove implements PlayBehavior {
	
	private List<Field> remainingFields = new ArrayList<Field>();
	private List<Field> preferableFields = new ArrayList<Field>();
	private int prevHitX;
	private int prevHitY;
	private static final int defaultValue = -1;
	private Board board;
	private boolean statusChanged;

	public ComputerPlayerMove (Board board) {
		this.board = board;
		statusChanged = true;
		setPrevHitX (defaultValue);
		setPrevHitY(defaultValue);
		for (int x = 0; x < board.getSize(); x++) {
			for (int y = 0; y < board.getSize(); y++) {
				remainingFields.add(board.getField(x, y));
			}
		}
	}

	public void addToPreferableFieldsIfNoHit (int x , int y) {
		Field v = board.getField(x, y);
		if (v != null) {
			if (!v.isHit()) {
				preferableFields.add(v);
				//System.out.println("In voorkeurZetten gezet:(" + x + "," + y + ")");
			}
		}
	}

	public void putSurroundingFieldsInPreferable (Field playField) {
		addToPreferableFieldsIfNoHit( playField.getX() - 1 , playField.getY() );
		addToPreferableFieldsIfNoHit( playField.getX() + 1 , playField.getY() );
		addToPreferableFieldsIfNoHit( playField.getX() , playField.getY() - 1 );
		addToPreferableFieldsIfNoHit( playField.getX() , playField.getY() + 1 );
	}
	
	public void deleteNonAxisFields ( Field checkVeld , String direction ) {
		//System.out.println("Deleten niet-as velden...");
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

	@Override
	public void move (Fleet fleet) {
		Field playField = null;
		board.setQuality(fleet);
		Collections.sort(remainingFields);
		if (preferableFields.isEmpty() || statusChanged) {
			if (statusChanged) {
				statusChanged = false;
				preferableFields.clear();
			}
			//System.out.println("random zet");
			playField = remainingFields.get(0);
			if (playField.getBoat()) {
				putSurroundingFieldsInPreferable(playField);
				prevHitX = playField.getX();
				//volgende 2 regels ok??
				setPrevHitX(playField.getX());
				setPrevHitY(playField.getY());
			} 
		} else {
			//System.out.println("voorkeurzet");
			playField = preferableFields.get(0);
			if (playField.getBoat()) {
				putSurroundingFieldsInPreferable(playField);
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

		board.setFieldHit(playField.getX(), playField.getY());
		System.out.println("Computerzet was (" + playField.getX() + "," + (char)(playField.getY() + 65) + ")");		
	}
}
