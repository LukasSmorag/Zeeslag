package smorag.lukas.zeeslag.entities;

public class Field implements Comparable <Field> {
	
	private boolean boat;//is veld een boot of niet
	private boolean isHit;//is veld reeds geraakt of niet
	private int quality;
	private int x;
	private int y;
	
	Field (int x , int y) {
		this.setX(x);
		this.setY(y);
		boat = false;
		isHit = false;
	}
	
	public boolean isHit() {
		return isHit;
	}
	
	public boolean getBoat() {
		return boat;
	}
	
	public void setBoat (boolean boat) {
		this.boat = boat;
	}
	
	public void setHit(boolean hit) {
		this.isHit = true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	@Override
	public int compareTo (Field f) {
		int returner;
		if (this.quality > f.getQuality()) {
			returner = -1;
		} else {
			if (this.quality < f.getQuality()) {
				returner = 1;
			} else {
				returner = 0;
			}
		}
		return returner;
	}
}
