import java.util.ArrayList;

/**
 * This class represents a ray that Hits a ball.
 */
public class Hit extends AHint {

	/**
	 * Constructs a Hit using the board dimensions, position of the ray, and direction of travel.
	 *
	 * @param boardDim dimensions of board
	 * @param p position of ray
	 * @param dir direction of ray travel
	 */
    Hit(int boardDim, Posn p, Direction dir) {
      super(boardDim, p, dir);
    }

	// generate the boolean expression that determines whether or not this Hit hint is satisfied given some set of balls
	public String generate() { //TODO: order of operations
	// border case: if the CCW ball is out of bounds, then there must be no CW ball 
    	if(this.outOfBounds(this.dir.ballCCW(this.position))) {
    		return String.format("(~P%d%d) & (", this.dir.ballCW(this.position).getX(), this.dir.ballCW(this.position).getY())
    			+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
    			+ ")"; 	
    	}
    	else if(this.outOfBounds(this.dir.ballCW(this.position))) {
    		return String.format("(~P%d%d) & (", this.dir.ballCCW(this.position).getX(), this.dir.ballCCW(this.position).getY())
        			+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
        			+ ")"; 
    	}
    	else {
	    	return String.format("(~P%d%d) & (~P%d%d) & (", this.dir.ballCW(this.position).getX(), this.dir.ballCW(this.position).getY(),
	    											   this.dir.ballCCW(this.position).getX(), this.dir.ballCCW(this.position).getY())
	    		   + new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
	    		   + ")";
    	}
	}

	// continue generating the expression for a ray that has already entered the board
	public String generate(ArrayList<AHint> checked) {
	    if (checked.contains(this)) {
	      return AHint.nil;
	    }
	    
	    checked.add(this);

	    // get the position of a ball that would deflect the ray from clockwise
	    Posn ballCW = this.dir.ballCW(this.position);
	    // get the position of a ball that would deflect the ray from counterclockwise
	    Posn ballCCW = this.dir.ballCCW(this.position);
	    
		// First check the border cases: towards the border, going clockwise around the border, and going counter-clockwise around the border
		// if the ray is about to exit the board,
		if( this.outOfBounds(ballCW) && this.outOfBounds(ballCCW)   )
			// then the only way for the hit to be satisfied is for there to be a ball at the current position
			return "P" + this.position.getX() + this.position.getY();
		// if the ray would exit on a counter-clockwise deflection,
		else if ( this.outOfBounds(ballCW)  ) {
			// then the hit could be satisfied with a ball here, or with no counter-clockwise deflection and a ball later
			return String.format("(P%d%d | (~P%d%d & ",
						this.position.getX(), this.position.getY(), 
						ballCCW.getX(), ballCCW.getY(), 
					+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		}
		// if the ray would exit on a clockwise deflection,
		else if ( this.outOfBounds(ballCCW) ) {
			// then the hit could be satisfied with a ball here, or with no clockwise deflection and a ball later
			return String.format("(P%d%d | (~P%d%d & ",
						this.position.getX(), this.position.getY(), 
						ballCW.getX(), ballCW.getY(), 
				+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		
		}
		// if the ray is somewhere in the middle of the board, then check for a ball here or a ball after the ray continues in the deflected direction
		else {			
			// no deflection
			return String.format("(P%d%d | ((~P%d%d & ~P%d%d) => ", 
					this.position.getX(), this.position.getY(), 
					ballCW.getX(), ballCW.getY(),
					ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, dir.getNextPosn(this.position), this.dir).generate(checked) + ")"
				// clockwise deflection
				+ String.format(" & ((P%d%d & ~P%d%d) => ",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextClockwiseDirection().getNextPosn(this.position), this.dir.nextClockwiseDirection()).generate(checked) + ")"
				// counter-clockwise deflection
				+ String.format(" & ((~P%d%d & P%d%d) => ",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextCounterClockwiseDirection().getNextPosn(this.position), this.dir.nextCounterClockwiseDirection()).generate(checked) + ")"
				// double deflection immediately means this hit is unsatisfied
				+ String.format(" & ~(P%d%d & P%d%d))",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY()) + ")"; 
		}
	
	}
}
