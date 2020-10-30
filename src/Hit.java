import java.util.ArrayList;

//a class to represent a hit Hintl
public class Hit extends AHint {
	// direction convention: 
	// 0 - N
	// 1 - E
	// 2 - S
	// 3 - W
	
	
	
    Hit(int boardDim, int x, int y, int dir) {
      super(boardDim, x, y, dir);
    }

    @Override
	public String generate() {
    	return this.generate(new ArrayList<>());
	}

	@Override
	public String generate(ArrayList<IHint> hints) { //problem: cycles? - must keep track of places we already visited

    	if (hints.contains(this)) {
    		return "0";
		}

    	hints.add(this);

		int[] upDeflection = this.getDeflect(1); 
		int[] downDeflection = this.getDeflect(-1); 
		
		if( this.outOfBounds(upDeflection) && this.outOfBounds(downDeflection)   )
			return "(P" + this.x + this.y + ")";
		else if ( this.outOfBounds(upDeflection)  ) {
			return String.format("(P%d%d | ((P%d%d => 0) & ~P%d%d => ",
						this.x, this.y, 
						downDeflection[0], downDeflection[1], 
						downDeflection[0], downDeflection[1])
					+ new Hit(this.boardDim, this.getNextPosn()[0], this.getNextPosn()[1], dir).generate(hints) + "))";
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
			
			return String.format("(P%d%d | ((~P%d%d & ~P%d%d) =>  ", 
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
