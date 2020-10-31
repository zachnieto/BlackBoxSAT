
public class Posn {
	private int x; 
	private int y; 
	
	Posn(int x, int y) {
		this.x = x; 
		this.y = y;
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
	
	public boolean equals(Object other) {
		if(this == other)
			return true; 
		
		if(!(other instanceof Posn))
			return false; 
		
		return this.x == ((Posn) other).getX() && this.y == ((Posn) other).getY();  
	}

}
