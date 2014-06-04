package smorag.lukas.zeeslag.entities;

public class Boat {
	
	private int length;
	private String name;
	private int xDir,yDir,xBegin,yBegin;//richtingsincrementor rijen, richt.incr.kolommen,beginrij,beginkolom
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	private boolean sunken;
	
	public Boat ( int l , int x, int y , int direction, String name ) {//richting 0 = horizontaal, 1 = vertikaal
		this.yDir = 1 - direction;
		this.xDir = direction; 
		this.xBegin = x;
		this.yBegin = y;
		this.length = l;
		sunken = false;
		this.name = name;
	}
	
	public String getName () {
		return name;
	}
	
	public void setXdir (int xDir) {
		this.xDir = xDir;
	}
	
	public void setYdir (int yDir) {
		this.yDir = yDir;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getXDir ()
	{
		return xDir;
	}
	
	public int getYDir () {
		return yDir;
	}
	
	public int getXStart () {
		return xBegin;
	}
	
	public int getYStart () {
		return yBegin;
	}
	
	public boolean getSunken (Board board) {
		sunken = true;
		int i = 0;
		while (i < length && sunken) {
			if (! board.getField(xBegin + i * xDir, yBegin + i * yDir).isHit()) {
				sunken = false;
			}
			i++;
		}
		return sunken;
	}
	
	public String toString() {
		return (name + " (" + length + ")");
	}
	
	public int getxDir() {
		return xDir;
	}

	public void setxDir(int xDir) {
		this.xDir = xDir;
	}

	public int getyDir() {
		return yDir;
	}

	public void setyDir(int yDir) {
		this.yDir = yDir;
	}

	public int getxBegin() {
		return xBegin;
	}

	public void setxBegin(int xBegin) {
		this.xBegin = xBegin;
	}

	public int getyBegin() {
		return yBegin;
	}

	public void setyBegin(int yBegin) {
		this.yBegin = yBegin;
	}

	public boolean isSunken() {
		return sunken;
	}

	public void setSunken(boolean sunken) {
		this.sunken = sunken;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setName(String name) {
		this.name = name;
	}
}
