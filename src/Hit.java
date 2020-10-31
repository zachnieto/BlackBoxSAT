import java.util.ArrayList;

//a class to represent a hit Hintl
public class Hit extends AHint {
	// direction convention: 
	// 0 - N
	// 1 - E
	// 2 - S
	// 3 - W

    Hit(int boardDim, Posn p, Direction dir) {
      super(boardDim, p, dir);
    }

    @Override
	public String generate() { //TODO: order of operations 
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

	
	public String generate(ArrayList<AHint> checked) { //problem: cycles? - must keep track of places we already visited
		// if recursion leads back to a cell-direction pair we've already checked, terminate the expression
	    if (checked.contains(this)) {
	      return AHint.nil;
	    }
	    
	    checked.add(this);

	    // get the position of a ball that would deflect the ray from clockwise
	    Posn ballCW = this.dir.ballCW(this.position);
	    // get the position of a ball that would deflect the ray from counterclockwise
	    Posn ballCCW = this.dir.ballCCW(this.position);
	    
		if( this.outOfBounds(ballCW) && this.outOfBounds(ballCCW)   )
			return "P" + this.position.getX() + this.position.getY();
		else if ( this.outOfBounds(ballCW)  ) {
			return String.format("(P%d%d | ((P%d%d => " + AHint.nil + ") & (~P%d%d => ",
						this.position.getX(), this.position.getY(), 
						ballCCW.getX(), ballCCW.getY(), 
						ballCCW.getX(), ballCCW.getY())
					+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		}
		else if ( this.outOfBounds(ballCCW) ) {
			return String.format("(P%d%d | ((P%d%d => " + AHint.nil + ") & (~P%d%d => ",
						this.position.getX(), this.position.getY(), 
						ballCW.getX(), ballCW.getY(), 
						ballCW.getX(), ballCW.getY())
				+ new Hit(this.boardDim, this.dir.getNextPosn(this.position), this.dir).generate(checked) + ")))";
		
		}		
		else {			
			return String.format("(P%d%d | ((~P%d%d & ~P%d%d) => ", 
					this.position.getX(), this.position.getY(), 
					ballCW.getX(), ballCW.getY(),
					ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, dir.getNextPosn(this.position), this.dir).generate(checked) + ")"
				+ String.format(" & ((P%d%d & ~P%d%d) => ",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextClockwiseDirection().getNextPosn(this.position), this.dir.nextClockwiseDirection()).generate(checked) + ")"
				+ String.format(" & ((~P%d%d & P%d%d) => ",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY())
				+ new Hit(this.boardDim, this.dir.nextCounterClockwiseDirection().getNextPosn(this.position), this.dir.nextCounterClockwiseDirection()).generate(checked) + ")"
				+ String.format(" & ((P%d%d & P%d%d) => " + AHint.nil + ")",
						ballCW.getX(), ballCW.getY(), 
						ballCCW.getX(), ballCCW.getY()) + ")"; 
		}
	
	}
}
