
public abstract class AHint {
	protected int boardDim;
	protected Posn position; 
	protected Direction dir; 

	AHint(int boardDim, Posn p, Direction dir) {
		this.setBoardDim(boardDim);
		this.position = p; 
		this.dir = dir; 
	}

	
	public int getBoardDim() { return boardDim; }
	 
	public void setBoardDim(int boardDim) { this.boardDim = boardDim; }

	public abstract String generate();

	public Posn getNextPosn() {
		return dir.getNextPosn(this.position); 
	}
	
	public boolean outOfBounds(Posn position) {
		return position.getX() == -1 
		    || position.getY() == -1 
		    || position.getX() == this.boardDim
		    || position.getY()== this.boardDim; 
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
	    return other1.position.getX() == this.position.getX()
	    && other1.position.getY() == this.position.getY()
	    && other1.dir == this.dir; 
	  }
}
