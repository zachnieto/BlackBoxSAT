public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public Direction nextClockwiseDirection() {
        switch (this) {
            case NORTH:
            	return EAST;
            case EAST:
            	return SOUTH;
            case SOUTH:
            	return WEST; 
            case WEST:
            	return NORTH;  	  	
        }
        
        throw new IllegalArgumentException("somehow illegal argument") ;
    }

    public Direction nextCounterClockwiseDirection() {
    	switch (this) {
	        case NORTH:
	        	return WEST;
	        case EAST:
	        	return NORTH;
	        case SOUTH:
	        	return EAST; 
	        case WEST:
	        	return SOUTH;
    	}
    	
    	throw new IllegalArgumentException("somehow illegal argument") ;
    }
    
    public Direction nextDoubleDirection() {
    	switch (this) {
        case NORTH:
        	return SOUTH;
        case EAST:
        	return WEST;
        case SOUTH:
        	return NORTH; 
        case WEST:
        	return EAST;
	}
	
	throw new IllegalArgumentException("somehow illegal argument") ;
    }
    
    public Posn ballCW(Posn cur) { 
    	switch (this) {
        case NORTH:
        	return new Posn(cur.getX() - 1, cur.getY() - 1); 
        case EAST:
        	 return new Posn(cur.getX() + 1, cur.getY() - 1);
        case SOUTH:
        	return new Posn(cur.getX() + 1,  cur.getY() + 1);
        case WEST:
        	return new Posn(cur.getX() - 1,  cur.getY() + 1);
        
        	
    	}
       	throw new IllegalArgumentException("nothing"); 

    	}
    	
    public Posn ballCCW(Posn cur) { 
       	switch (this) {
       		case NORTH:
            	return new Posn(cur.getX() + 1, cur.getY() - 1); 
            case EAST:
            	 return new Posn(cur.getX() + 1, cur.getY() + 1);
            case SOUTH:
            	return new Posn(cur.getX() - 1,  cur.getY() + 1);
            case WEST:
            	return new Posn(cur.getX() - 1,  cur.getY() - 1);
    }	
       	throw new IllegalArgumentException("nothing"); 
    }
    
    public Posn getNextPosn(Posn cur) {
    	switch (this) {
	    	case NORTH:
	        	return new Posn(cur.getX(), cur.getY() - 1); 
	        case EAST:
	        	 return new Posn(cur.getX() + 1, cur.getY());
	        case SOUTH:
	        	return new Posn(cur.getX(),  cur.getY() + 1);
	        case WEST:
	        	return new Posn(cur.getX() - 1,  cur.getY());
    	}
       	throw new IllegalArgumentException("nothing"); 

    }
}
