import java.util.ArrayList;

/**
 * This class represents a ray that exits the board.
 */
public class Exit extends AHint {
	Posn startPos;
	Posn endPos;

	/**
	 * Constructs an Exit Hint expected to exit the board at endPos. Used internally when generating 
	 * the boolean encoding
	 *
	 * @param boardDim dimensions of board
	 * @param p current position
	 * @param dir direction of travel
	 * @param startPos starting position
	 * @param endPos ending position
	 */
	Exit(int boardDim, Posn p, Direction dir, Posn startPos, Posn endPos) {
		super(boardDim, p, dir); 
		this.startPos = startPos;
		this.endPos = endPos; 
	}
	
	
	/**
	 * Initializes an exit hint, setting current position to be the given startPos. Eases
	 * initialization for testing. 
	 *
	 * @param boardDim dimensions of board
	 * @param dir direction of travel
	 * @param startPos starting position
	 * @param endPos ending position
	 */
	Exit(int boardDim, Posn startPos, Direction dir, Posn endPos) {
		super(boardDim, startPos, dir); 
		this.startPos = startPos; 
		this.endPos = endPos; 
	}
	
	@Override
	public String generate() {
		if(this.outOfBounds(this.dir.atomCW(this.startPos))) { 
			if(this.startPos.equals(this.endPos)) {
				return String.format("(P%d%d & ~P%d%d)", this.dir.atomCCW(this.startPos).getX(),
														 this.dir.atomCCW(this.startPos).getY(),
														 this.dir.getNextPosn(this.startPos).getX(),
														 this.dir.getNextPosn(this.startPos).getY()); 
			}
			else {
				return String.format("(~P%d%d) & (~P%d%d ", this.dir.atomCCW(this.startPos).getX(),
												 		    this.dir.atomCCW(this.startPos).getY(),
												 		    this.dir.getNextPosn(this.startPos).getX(),
	    											 	  	this.dir.getNextPosn(this.startPos).getY())
				+ " & (" 
				+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
				+ "))"; 
			}
		}
		else if(this.outOfBounds(this.dir.atomCCW(this.startPos))) {
			if(this.startPos.equals(this.endPos)) {
				return String.format("(P%d%d & ~P%d%d)", this.dir.atomCW(this.startPos).getX(),
														 this.dir.atomCW(this.startPos).getY(),
														 this.dir.getNextPosn(this.startPos).getX(),
														 this.dir.getNextPosn(this.startPos).getY()); 
			}
			else {
				return String.format("(~P%d%d) & (~P%d%d ", this.dir.atomCW(this.startPos).getX(),
												 			this.dir.atomCW(this.startPos).getY(), 
												 			this.dir.getNextPosn(this.startPos).getX(),
												 			this.dir.getNextPosn(this.startPos).getY())
				+ " & (" 
				+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
				+ "))"; 
			}
		}
		else {
			if(this.startPos.equals(this.endPos)) {
				return String.format("~P%d%d & ((P%d%d | P%d%d) | ", this.dir.getNextPosn(this.startPos).getX(),
						 											 this.dir.getNextPosn(this.startPos).getY(), 
						 											 this.dir.atomCW(this.startPos).getX(),
																	 this.dir.atomCW(this.startPos).getY(),
																	 this.dir.atomCCW(this.startPos).getX(),
																	 this.dir.atomCCW(this.startPos).getY()) 
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
					+ ")"; 
			}
			else {
		    	return String.format("(~P%d%d & ~P%d%d) & (~P%d%d & ", this.dir.atomCW(this.startPos).getX(),
		    												  		   this.dir.atomCW(this.startPos).getY(),
		    												  		   this.dir.atomCCW(this.startPos).getX(),
		    												  		   this.dir.atomCCW(this.startPos).getY(), 
		    												  		   this.dir.getNextPosn(this.startPos).getX(),
		    												  		   this.dir.getNextPosn(this.startPos).getY())
		    		+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
		    		+ ")"; 
			}
		}
	}

	@Override
	public String generate(ArrayList<AHint> checked) {
		 if (checked.contains(this)) {
		      return AHint.nil;
		 }
		    
		 checked.add(this);

		 // get the position of an atom that would deflect the ray from clockwise
		 Posn atomCW = this.dir.atomCW(this.position);
		 // get the position of an atom that would deflect the ray from counterclockwise
		 Posn atomCCW = this.dir.atomCCW(this.position);

		 if(this.outOfBounds(atomCCW) && this.outOfBounds(atomCW)) {
			 if(this.dir.getNextPosn(this.position).equals(endPos)) {
				 return String.format("(~P%d%d)", this.position.getX(),
						 						  this.position.getY()); 
			 }
			 else
				 return AHint.nil; 
		 }
		 else if(this.outOfBounds(atomCW)) {
			 if(this.dir.nextCounterClockwiseDirection().getNextPosn(this.position).equals(endPos)) {
				 return String.format("(P%d%d)", atomCCW.getX(),
						 						 atomCCW.getY()); 
			 }
			 else {
				 return String.format("(~P%d%d & ~P%d%d) & (", atomCCW.getX(),
						 									   atomCCW.getY(),
						 									   this.dir.getNextPosn(this.position).getX(),
		    											 	   this.dir.getNextPosn(this.position).getY())
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(checked)
					+ ")";
			 }
		 }
		 else if(this.outOfBounds(atomCCW)) {
			 if(this.dir.nextClockwiseDirection().getNextPosn(this.position).equals(endPos)) {
				 return String.format("(P%d%d)", atomCW.getX(), atomCW.getY()); 
			 }
			 else {
				 return String.format("(~P%d%d & ~P%d%d) & (", atomCW.getX(),
						 									   atomCW.getY(),
						 									   this.dir.getNextPosn(this.position).getX(),
		    											 	   this.dir.getNextPosn(this.position).getY())
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(checked)
					+ ")";
			 }
		 }
		 else {
			 return String.format("(~P%d%d & (", this.dir.getNextPosn(this.position).getX(),
				 	  						     this.dir.getNextPosn(this.position).getY())
					 
			 		+ String.format("((~P%d%d & ~P%d%d) => ", atomCW.getX(), 
			 											      atomCW.getY(),
			 											      atomCCW.getX(), 
			 											      atomCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(new ArrayList<>(checked))
			 		+ ")"
			 		
			 		+ String.format(" & ((P%d%d & ~P%d%d) => ", atomCW.getX(), 
			 													atomCW.getY(),
			 													atomCCW.getX(), 
			 													atomCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.nextClockwiseDirection().getNextPosn(this.position), this.dir.nextClockwiseDirection(), this.startPos, this.endPos).generate(new ArrayList<>(checked))
			 		+ ")"
			 		
			 		+ String.format(" & ((~P%d%d & P%d%d) => ", atomCW.getX(),
			 													atomCW.getY(),
			 													atomCCW.getX(),
			 													atomCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.nextCounterClockwiseDirection().getNextPosn(this.position), this.dir.nextCounterClockwiseDirection(), this.startPos, this.endPos).generate(new ArrayList<>(checked))
			 		+ ")"
			 		
			 		+ String.format(" & ((P%d%d & P%d%d) => ", atomCW.getX(),
			 												   atomCW.getY(),
			 												   atomCCW.getX(),
			 												   atomCCW.getY())
			 		+ ((startPos.equals(endPos)) ? AHint.t + ")" : AHint.nil + ")") + "))";
		 }

	}
}
