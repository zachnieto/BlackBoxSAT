import java.util.ArrayList;

/**
 * This class represents a ray that Hits an atom.
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
    
    	// Generate the boolean expression that determines whether or not this Hit hint is satisfied given some set of atoms (entering from the border--Section 3.5)
	public String generate() {
	// Corner case: if the CCW atom is out of bounds, then there must be no CW atom to avoid border reflection
    	if(this.outOfBounds(this.dir.atomCCW(this.position))) {
    		return String.format("(~P%d%d) & (P%d%d | ", 
				     this.dir.atomCW(this.position).getX(),
				     this.dir.atomCW(this.position).getY(), 
				     this.dir.getNextPosn(this.position).getX(),
				     this.dir.getNextPosn(this.position).getY())
    			+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
    			+ ")"; 	
    	}
	// Corner case: if the CW atom is out of bounds, then there must be no CCW atom to avoid border reflection
    	else if(this.outOfBounds(this.dir.atomCW(this.position))) {
    		return String.format("(~P%d%d) & (P%d%d | ",
				     this.dir.atomCCW(this.position).getX(),
				     this.dir.atomCCW(this.position).getY(),
				     this.dir.getNextPosn(this.position).getX(),
				     this.dir.getNextPosn(this.position).getY())
        			+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
        			+ ")"; 
    	}
	// Normal entry case: there must be neither a CW atom nor a CCW atom to avoid border reflection
    	else {
	    	return String.format("(~P%d%d) & (~P%d%d) & (P%d%d | ",
				     this.dir.atomCW(this.position).getX(),
				     this.dir.atomCW(this.position).getY(),
				     this.dir.atomCCW(this.position).getX(),
				     this.dir.atomCCW(this.position).getY(),
				     this.dir.getNextPosn(this.position).getX(),
				     this.dir.getNextPosn(this.position).getY())
	    		   + new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>())
	    		   + ")";
	}
	}

	// Continue generating the expression for a ray that has already entered the board (Section 3.3)
	public String generate(ArrayList<AHint> checked) {
	    if (checked.contains(this)) {
	      return AHint.nil;
	    }
	    
	    checked.add(this);

	    // Get the position of an atom that would deflect the ray clockwise
	    Posn atomCW = this.dir.atomCW(this.position);
	    // Get the position of an atom that would deflect the ray counterclockwise
	    Posn atomCCW = this.dir.atomCCW(this.position);
	    
		// First check the border cases (Section 3.6): towards the border, going clockwise along the border, and going counterclockwise along the border (Section 3.6)
		// If the ray is about to exit the board,
		if(this.outOfBounds(atomCW) && this.outOfBounds(atomCCW))
			// then the only way for the hit to be satisfied is for there to be an atom at the current position.
			return "P" + this.position.getX() + this.position.getY();
		// If the ray would exit on a counterclockwise deflection,
		else if ( this.outOfBounds(atomCW)  ) {
			// then the hit could be satisfied with an atom here, or with no counterclockwise deflection and an atom later.
			return String.format("(P%d%d | ((P%d%d => " + AHint.nil + ") & (~P%d%d => ",
						this.dir.getNextPosn(this.position).getX(),
						this.dir.getNextPosn(this.position).getY(),
						atomCCW.getX(), 
						atomCCW.getY(), 
						atomCCW.getX(),
						atomCCW.getY())
					+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		}
		// If the ray would exit on a clockwise deflection,
		else if ( this.outOfBounds(atomCCW) ) {
			// then the hit could be satisfied with an atom here, or with no clockwise deflection and an atom later.
			return String.format("(P%d%d | ((P%d%d => " + AHint.nil + ") & (~P%d%d => ",
						this.dir.getNextPosn(this.position).getX(),
						this.dir.getNextPosn(this.position).getY(),
						atomCW.getX(),
						atomCW.getY(), 
						atomCW.getX(),
						atomCW.getY())
				+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		}
		// If the ray is somewhere in the middle of the board, then check for an atom here or an atom after the ray continues in the deflected direction.
		else {			
			// Case 1: no deflection
			return String.format("(P%d%d | ((~P%d%d & ~P%d%d) => ",
					this.dir.getNextPosn(this.position).getX(),
			 	  	this.dir.getNextPosn(this.position).getY(),
					atomCW.getX(),
					atomCW.getY(),
					atomCCW.getX(),
					atomCCW.getY())
				+ new Hit(this.boardDim, dir.getNextPosn(this.position), this.dir).generate(new ArrayList<>(checked))
				+ ")"
				// Case 2: clockwise deflection
				+ String.format(" & ((P%d%d & ~P%d%d) => ",
						atomCW.getX(),
						atomCW.getY(), 
						atomCCW.getX(),
						atomCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextClockwiseDirection().getNextPosn(this.position), this.dir.nextClockwiseDirection()).generate(new ArrayList<>(checked))
				+ ")"
				// Case 3: counterclockwise deflection
				+ String.format(" & ((~P%d%d & P%d%d) => ",
						atomCW.getX(),
						atomCW.getY(), 
						atomCCW.getX(),
						atomCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextCounterClockwiseDirection().getNextPosn(this.position), this.dir.nextCounterClockwiseDirection()).generate(new ArrayList<>(checked))
				+ ")"
				// Case 4: refelection immediately means the Hit is unsatisfied
				+ String.format(" & ((P%d%d & P%d%d) => " + AHint.nil + ")",
						atomCW.getX(),
						atomCW.getY(), 
						atomCCW.getX(),
						atomCCW.getY())
				+ ")"; 
		}
	}
}
