package smorag.lukas.zeeslag.entities;

public class Boat {
	
	private int length;
	private String name;
	private int xDir,yDir,xBegin,yBegin;//richtingsincrementor rijen, richt.incr.kolommen,beginrij,beginkolom
	private boolean sunken;
	
	Boat ( int l , int x, int y , int direction, String name ) {//richting 0 = horizontaal, 1 = vertikaal
		yDir = 1 - direction;
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
	
	public void setXdir (int rdir) {
		this.xDir = rdir;
	}
	
	public void setYdir (int k) {
		yDir = k;
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
}
