import java.util.ArrayList;

/**
 * This class represents a ray that exits the board.
 */
public class Exit extends AHint {
	Posn startPos; //TODO: put this in AHint to use as starting location for hints (checking default generate case)
	Posn endPos;

	/**
	 * Constructs an Exit Hint expects to exit the board.
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
	
	@Override
	public String generate() {
		if(this.outOfBounds(this.dir.ballCW(this.startPos))) {
			if(this.startPos.equals(this.endPos)) {
				return String.format("P%d%d", this.dir.ballCCW(this.startPos).getX(), this.dir.ballCCW(this.startPos).getY()); 
			}
			else {
				return String.format("(~P%d%d)", this.dir.ballCCW(this.startPos).getX(), this.dir.ballCCW(this.startPos).getY())
				+ " & (" 
				+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
				+ ")"; 
			}
		}
		else if(this.outOfBounds(this.dir.ballCCW(this.startPos))) {
			if(this.startPos.equals(this.endPos)) {
				return String.format("P%d%d", this.dir.ballCW(this.startPos).getX(), this.dir.ballCW(this.startPos).getY()); 
			}
			else {
				return String.format("(~P%d%d)", this.dir.ballCW(this.startPos).getX(), this.dir.ballCW(this.startPos).getY())
				+ " & (" 
				+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>())
				+ ")"; 
			}
		}
		else {
			if(this.startPos.equals(this.endPos)) {
				return String.format("(P%d%d | P%d%d) | (", this.dir.ballCW(this.startPos).getX(), this.dir.ballCW(this.startPos).getY(),
					    								   this.dir.ballCCW(this.startPos).getX(), this.dir.ballCCW(this.startPos).getY()) 
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>()) + ")"; 
			}
			else {
		    	return String.format("(~P%d%d & ~P%d%d) & (", this.dir.ballCW(this.startPos).getX(), this.dir.ballCW(this.startPos).getY(),
		    											     this.dir.ballCCW(this.startPos).getX(), this.dir.ballCCW(this.startPos).getY())
		    		+ new Exit(this.boardDim, this.dir.getNextPosn(this.startPos), this.dir, this.startPos, this.endPos).generate(new ArrayList<>()) + ")"; 
			}
		}
	}

	@Override
	public String generate(ArrayList<AHint> checked) {
		 if (checked.contains(this)) {
		      return AHint.nil;
		    }
		    
		 checked.add(this);

		 // get the position of a ball that would deflect the ray from clockwise
		 Posn ballCW = this.dir.ballCW(this.position);
		 // get the position of a ball that would deflect the ray from counterclockwise
		 Posn ballCCW = this.dir.ballCCW(this.position);

		 if(this.outOfBounds(ballCCW) && this.outOfBounds(ballCW)) {
			 if(this.dir.getNextPosn(this.position).equals(endPos))
				 return String.format("~P%d%d", this.position.getX(), this.position.getY()); 
			 else
				 return AHint.nil; 
		 }
		 else if(this.outOfBounds(ballCW)) {
			 if(this.dir.nextCounterClockwiseDirection().getNextPosn(this.position).equals(endPos))
				 return String.format("P%d%d", ballCCW.getX(), ballCCW.getY()); 
			 else
				 return String.format("((~P%d%d & ~P%d%d) & ", ballCCW.getX(), ballCCW.getY(), this.position.getX(), this.position.getY())
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(checked) + ")"; 
		 }
		 else if(this.outOfBounds(ballCCW)) {
			 if(this.dir.nextClockwiseDirection().getNextPosn(this.position).equals(endPos))
				 return String.format("P%d%d", ballCW.getX(), ballCW.getY()); 
			 else
				 return String.format("((~P%d%d & ~P%d%d) & ", ballCW.getX(), ballCW.getY(), this.position.getX(), this.position.getY())
					+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(checked) + ")";
		 }
		 else {
			 return String.format("(~P%d%d & (", this.position.getX(), this.position.getY())
					 
			 		+ String.format("((~P%d%d & ~P%d%d) => ", ballCW.getX(), ballCW.getY(), ballCCW.getX(), ballCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.getNextPosn(this.position), this.dir, this.startPos, this.endPos).generate(checked) + ")"
			 		
			 		+ String.format(" & ((P%d%d & ~P%d%d) => ", ballCW.getX(), ballCW.getY(), ballCCW.getX(), ballCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.nextClockwiseDirection().getNextPosn(this.position), this.dir.nextClockwiseDirection(), this.startPos, this.endPos).generate(checked) + ")"
			 		
			 		+ String.format(" & ((~P%d%d & P%d%d) => ", ballCW.getX(), ballCW.getY(), ballCCW.getX(), ballCCW.getY())
			 		+ new Exit(this.boardDim, this.dir.nextCounterClockwiseDirection().getNextPosn(this.position), this.dir.nextCounterClockwiseDirection(), this.startPos, this.endPos).generate(checked) + ")"
			 		
			 		+ String.format(" & ((P%d%d & P%d%d) => ", ballCW.getX(), ballCW.getY(), ballCCW.getX(), ballCCW.getY())
			 		+ ((startPos.equals(endPos)) ? AHint.t + ")" : AHint.nil + ")") + "))";
		 }

	}
}
