
public abstract class AHint implements IHint {
	protected int boardDim;
	protected int x;
	protected int y;
	protected int dir;

	AHint(int boardDim, int x, int y, int dir) {
		this.setBoardDim(boardDim);
		this.setX(x);
		this.setY(y);
		this.setDir(dir);
	}

	
	public int getBoardDim() { return boardDim; }
	 
	public void setBoardDim(int boardDim) { this.boardDim = boardDim; }
	
	public int getX() { return x; }
	
	public void setX(int x) { this.x = x; }
	
	public int getY() { return y; }
	 
	public void setY(int y) { this.y = y; }
	 
	public int getDir() { return dir; }
	
	public void setDir(int dir) { this.dir = dir; }
	
	public abstract String generate();

	public int[] getNextPosn() {
		if(dir == 0) {
			return new int[] {this.x, this.y - 1}; 
		}
		else if(dir == 1) {
			return new int[] {this.x + 1, this.y}; 
		}
		else if(dir == 2) {
			return new int[] {this.x, this.y + 1}; 
		}
		else if(dir == 3) {
			return new int[] {this.x - 1, this.y}; 
		}
		else
			throw new IllegalArgumentException("illegal dir"); 
	}

	public int[] getDeflect(int up) {
		if(this.dir == 0)
			return new int[] { this.getNextPosn()[0] - up, this.getNextPosn()[1] };
		else if(this.dir == 1)
			return new int[] { this.getNextPosn()[0], this.getNextPosn()[1] - up };
		else if(this.dir == 2)
			return new int[] { this.getNextPosn()[0] + up, this.getNextPosn()[1] };
		else if(this.dir == 3)
			return new int[] { this.getNextPosn()[0], this.getNextPosn()[1] + up };
		else
			throw new IllegalArgumentException("illegal dir"); 
	}
	
	public boolean outOfBounds(int[] position) {
		return position[0] == -1 
		    || position[1] == -1 
		    || position[0] == this.boardDim
		    || position[1] == this.boardDim; 
	}
	
	@Override
	  public boolean equals(Object other) {
	    if (this == other) {
	      return true;
	    }

	    if (!(other instanceof Hit)) {
	      return false;
	    }

	    AHint other1 = (AHint) other;
	    return other1.x == this.x
	    && other1.y == this.y
	    && other1.dir == this.dir; 
	  }
}
