import java.util.ArrayList;

public class Exit extends AHint {
	Posn startPos; 
	Posn endPos; 

	Exit(int boardDim, Posn p, Direction dir, Posn startPos, Posn endPos) {
		super(boardDim, p, dir); 
		this.startPos = startPos;
		this.endPos = endPos; 
	}
	
	@Override
	public String generate() {
		return String.format("~P%d%d & ~P%d%d & ", this.getDeflect(1)[0], this.getDeflect(1)[1], 
											       this.getDeflect(0)[0], this.getDeflect(0)[1])
				+ this.generate(new ArrayList<>());
	}

	
	@Override
	public String generate(ArrayList<IHint> hints) {
		
		if (hints.contains(this)) {
    		return "0";
		}

    	hints.add(this);
    	
    	int[] upDeflection = this.getDeflect(1); 
		int[] downDeflection = this.getDeflect(-1); 
		
		if( this.outOfBounds(upDeflection) && this.outOfBounds(downDeflection) )
			if( this.getNextPosn().equals(endPos) )
				return String.format("(P%d%d => 0) & (~P%d%d => 1)", this.x, this.y, this.x, this.y);
			else 
				return "0"; 
		else if ( this.outOfBounds(upDeflection)  ) {
			return String.format("P%d%d", 0, 0);
		}
		else if ( this.outOfBounds(downDeflection) ) {
			return String.format("(P%d%d | ((P%d%d => 0) & ~P%d%d => ",
						this.x, this.y, 
						upDeflection[0], upDeflection[1], 
						upDeflection[0], upDeflection[1])
				+ new Hit(this.boardDim, this.getNextPosn()[0], this.getNextPosn()[1], dir).generate(hints) + "))";
		
		}		
		else {
			Hit deflFromAbove = new Hit(this.boardDim, this.x, this.y, (this.dir + 1) % 4); 
			Hit deflFromBelow = new Hit(this.boardDim, this.x, this.y, ((this.dir == 0) ? 3 : (this.dir - 1)));
			
			return String.format("(P%d%d | ((~P%d%d & ~P%d%d) => ", 
					this.x, this.y, 
					upDeflection[0], upDeflection[1],
					downDeflection[0], downDeflection[1])
				+ new Hit(this.boardDim, this.getNextPosn()[0], this.getNextPosn()[1], this.dir).generate(hints) + ")"
				+ String.format(" & ((P%d%d & ~P%d%d) => ",
						upDeflection[0], upDeflection[1], 
						downDeflection[0], downDeflection[1])
				+ new Hit(this.boardDim, deflFromAbove.getNextPosn()[0], deflFromAbove.getNextPosn()[1], (this.dir + 1) % 4).generate(hints) + ")"
				+ String.format(" & ((~P%d%d & P%d%d) => ",
						upDeflection[0], upDeflection[1], 
						downDeflection[0], downDeflection[1])
				+ new Hit(this.boardDim, deflFromBelow.getNextPosn()[0], deflFromBelow.getNextPosn()[1], ((this.dir == 0) ? 3 : (this.dir - 1))).generate(hints) + ")"
				+ String.format(" & ((P%d%d & P%d%d) => 0",
						upDeflection[0], upDeflection[1], 
						downDeflection[0], downDeflection[1]) + "))"; 
		}
    	

	}
}
