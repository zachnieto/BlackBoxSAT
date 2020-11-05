import org.junit.jupiter.api.Test;
import org.logicng.io.parsers.ParserException;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing class for AHints.
 */
@RunWith(JUnitQuickcheck.class)
public class AHintTest {
	

    /**
     * Substitutes each occurrence of var in the expression with the b value.
     * Used to make testing certain ball configurations more convenient, but has no value in the main code suite.  
     * 
     * @param expr expression to have replacements.
     * @param var variable to be replaced
     * @param b value to be inserted
     * @return the new substituted expression
     */
    public static String substitute(String expr, String var, boolean b) {
        if (b)
            return expr.replaceAll(var, AHint.t);
        else
            return expr.replaceAll(var, AHint.nil);
    }
	
    @Test
    void testBoards() throws ParserException {
    	final boolean fast = true; 
    	final boolean slow = !fast; 
    	
    	AHint unsatHint1 = new Exit(3, new Posn(-1, 1), Direction.EAST, new Posn(1, -1));
		AHint unsatHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
		AHint unsatHint3 = new Hit(3, new Posn(0, -1), Direction.SOUTH);
		AHint unsatHint4 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(3, 2));
    	
        AHint satHint1 = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(0, -1));
        AHint satHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
        AHint satHint3 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(0, 3));
        AHint satHint4 = new Exit(3, new Posn(0, 3), Direction.NORTH, new Posn(-1, 2));
        AHint satHint5 = new Hit(3, new Posn(1, 3), Direction.NORTH);
        AHint satHint6 = new Exit(3, new Posn(2, 3), Direction.NORTH, new Posn(3, 2));
        AHint satHint7 = new Exit(3, new Posn(3, 2), Direction.WEST, new Posn(2, 3));
        AHint satHint8 = new Exit(3, new Posn(3, 1), Direction.WEST, new Posn(3, 1));
        AHint satHint9 = new Hit(3, new Posn(3, 0), Direction.WEST);
        AHint satHint10 = new Hit(3, new Posn(2, -1), Direction.SOUTH);
        AHint satHint11 = new Exit(3, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1));
        AHint satHint12 = new Exit(3, new Posn(0, -1), Direction.SOUTH, new Posn(-1, 0));
        
        AHint bigSat1 = new Exit(5, new Posn(-1, 0), Direction.EAST, new Posn(0, -1));
        AHint bigSat2 = new Hit(5, new Posn(-1, 1), Direction.EAST); 
        AHint bigSat3 = new Hit(5, new Posn(-1, 2), Direction.EAST); 
        AHint bigSat4 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(-1, 3)); 
        AHint bigSat5 = new Hit(5, new Posn(-1, 4), Direction.EAST); 
        AHint bigSat6 = new Hit(5, new Posn(0, 5), Direction.NORTH); 
        AHint bigSat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(1, 5));
        AHint bigSat8 = new Hit(5, new Posn(2, 5), Direction.NORTH); 
        AHint bigSat9 = new Hit(5, new Posn(3, 5), Direction.NORTH); 
        AHint bigSat10 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(5, 3)); 
        AHint bigSat11 = new Hit(5, new Posn(5, 4), Direction.WEST); 
        AHint bigSat12 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(4, 5)); 
        AHint bigSat13 = new Hit(5, new Posn(5, 2), Direction.WEST); 
        AHint bigSat14 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(4, -1)); 
        AHint bigSat15 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(2, -1)); 
        AHint bigSat16 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(5, 1)); 
        AHint bigSat17 = new Hit(5, new Posn(3, -1), Direction.SOUTH); 
        AHint bigSat18 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(5, 0)); 
        AHint bigSat19 = new Hit(5, new Posn(1, -1), Direction.SOUTH); 
        AHint bigSat20 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(-1, 0)); 
        
        AHint bigUnsat1 = new Hit(5, new Posn(4, -1), Direction.SOUTH); 
        AHint bigUnsat2 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(5, 1)); 
        AHint bigUnsat3 = new Exit(5, new Posn(5, 2), Direction.WEST, new Posn(5, 2)); 
        AHint bigUnsat4 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(5, 3)); 
        AHint bigUnsat5 = new Hit(5, new Posn(4, 5), Direction.NORTH); 
        AHint bigUnsat6 = new Exit(5, new Posn(5, 4), Direction.WEST, new Posn(5, 4)); 
        AHint bigUnsat7 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(5, 0));   
        
        AHint[] unSatHints = {unsatHint1, unsatHint2, unsatHint3, unsatHint4}; 
        AHint[] satHints = {satHint1, satHint2, satHint3, satHint4, satHint5, satHint6, satHint7, satHint8, satHint9, satHint10, satHint11, satHint12};
        AHint[] bigBoard = {bigSat1, bigSat2, bigSat3, bigSat4, bigSat5, bigSat6, bigSat7, bigSat8, bigSat9, bigSat10, bigSat11, bigSat12, bigSat13, bigSat14, bigSat15, bigSat16, bigSat17, bigSat18, bigSat19, bigSat20};
        AHint[] bigUnsatBoard = {bigUnsat1, bigUnsat2, bigUnsat3, bigUnsat4, bigUnsat5, bigUnsat6, bigUnsat7};
        
        
        BBGame unsatEx1 = new BBGame("unsat 3x3", unSatHints); 
        BBGame satEx2 = new BBGame("sat 3x3", satHints);
        BBGame bigGame = new BBGame("sat 5x5", bigBoard); 
        BBGame bigUnsatGame = new BBGame("unsat 5x5", bigUnsatBoard); 
        
        unsatEx1.consistent(slow); 
        satEx2.consistent(slow);
        //bigGame.consistent(slow); 
        //bigUnsatGame.consistent(slow); 
        
        
        String booleanForm = bigSat8.generate(); 
        booleanForm = substitute(booleanForm, "P00", false);
        booleanForm = substitute(booleanForm, "P01", false);
        booleanForm = substitute(booleanForm, "P02", false);
        booleanForm = substitute(booleanForm, "P03", false);
        booleanForm = substitute(booleanForm, "P04", true);
        
        booleanForm = substitute(booleanForm, "P10", false);
        booleanForm = substitute(booleanForm, "P11", true);
        booleanForm = substitute(booleanForm, "P12", false);
        booleanForm = substitute(booleanForm, "P13", false);
        booleanForm = substitute(booleanForm, "P14", false);
        
        booleanForm = substitute(booleanForm, "P20", false);
        booleanForm = substitute(booleanForm, "P21", false);
        booleanForm = substitute(booleanForm, "P22", false);
        booleanForm = substitute(booleanForm, "P23", false);
        booleanForm = substitute(booleanForm, "P24", false);
        
        booleanForm = substitute(booleanForm, "P30", false);
        booleanForm = substitute(booleanForm, "P31", false);
        booleanForm = substitute(booleanForm, "P32", true);
        booleanForm = substitute(booleanForm, "P33", false);
        booleanForm = substitute(booleanForm, "P34", false);
        
        booleanForm = substitute(booleanForm, "P40", false);
        booleanForm = substitute(booleanForm, "P41", false);
        booleanForm = substitute(booleanForm, "P42", false);
        booleanForm = substitute(booleanForm, "P43", false);
        booleanForm = substitute(booleanForm, "P44", false);
    }

    @Test
    public void testSubstitute() {
        assertEquals(substitute("P01", "Pasd", true), "P01");
        assertEquals(substitute("P01", "P01", true), AHint.t);
        assertEquals(substitute("P01", "P01", false), AHint.nil);
    }
    
    @Test
    public void testDirectionChanges() {
    	assertEquals(Direction.EAST.nextClockwiseDirection(), Direction.SOUTH);
    	assertEquals(Direction.SOUTH.nextClockwiseDirection(), Direction.WEST);
    	assertEquals(Direction.WEST.nextClockwiseDirection(), Direction.NORTH);
    	assertEquals(Direction.NORTH.nextClockwiseDirection(), Direction.EAST);
    	assertEquals(Direction.EAST.nextCounterClockwiseDirection(), Direction.NORTH);
    	assertEquals(Direction.SOUTH.nextCounterClockwiseDirection(), Direction.EAST);
    	assertEquals(Direction.WEST.nextCounterClockwiseDirection(), Direction.SOUTH);
    	assertEquals(Direction.NORTH.nextCounterClockwiseDirection(), Direction.WEST);
    }
    
    @Property
    public void testDirectionProperties(Direction d) {
    	assertEquals(d.nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection(), d); 
    	assertEquals(d.nextCounterClockwiseDirection()
				  	  .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection(), d); 
    	assertEquals(d.nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection(), 
    				 d.nextCounterClockwiseDirection()
				  	  .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()); 
    }
    
    @Test
    public void testGetNextPosn() {
    	assertEquals(Direction.EAST.getNextPosn(new Posn(0, 0)), new Posn(1, 0)); 
    	assertEquals(Direction.WEST.getNextPosn(new Posn(0, 0)), new Posn(-1, 0)); 
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(0, 0)), new Posn(0, 1)); 
    	assertEquals(Direction.NORTH.getNextPosn(new Posn(0, 0)), new Posn(0, -1)); 
    	
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(-234, 12300)), new Posn(-234, 12301));
    	assertEquals(Direction.WEST.getNextPosn(new Posn (-234, 12300)), new Posn(-235, 12300)); 
    }
    
    @Property
    public void testGetNextPosnProperties(int x, int y) {
    	assertEquals(Direction.EAST.getNextPosn(new Posn(x, y)), new Posn(x + 1, y)); 
    	assertEquals(Direction.NORTH.getNextPosn(new Posn(x, y)), new Posn(x, y - 1));
    	assertEquals(Direction.WEST.getNextPosn(new Posn(x, y)), new Posn(x - 1, y));
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(x, y)), new Posn(x, y + 1));
    }
    
    @Test
    public void testBallCW() {
    	assertEquals(Direction.EAST.ballCW(new Posn(0, 0)), new Posn(1, -1)); 
    	assertEquals(Direction.WEST.ballCW(new Posn(0, 0)), new Posn(-1, 1)); 
    	assertEquals(Direction.SOUTH.ballCW(new Posn(0, 0)), new Posn(1, 1)); 
    	assertEquals(Direction.NORTH.ballCW(new Posn(0, 0)), new Posn(-1, -1)); 
    	
    	assertEquals(Direction.NORTH.ballCW(new Posn(-999, 123)), new Posn(-1000, 122)); 
    	assertEquals(Direction.SOUTH.ballCW(new Posn(100, -5987)), new Posn(101, -5986));
    	assertEquals(Direction.EAST.ballCW(new Posn(89652, 15)), new Posn(89653, 14));
    }
    
    @Property
    public void testBallCWProperties(int x, int y) {
    	assertEquals(Direction.EAST.ballCW(new Posn(x, y)), new Posn(x + 1, y - 1)); 
    	assertEquals(Direction.WEST.ballCW(new Posn(x, y)), new Posn(x - 1, y + 1)); 
    	assertEquals(Direction.SOUTH.ballCW(new Posn(x, y)), new Posn(x + 1, y + 1)); 
    	assertEquals(Direction.NORTH.ballCW(new Posn(x, y)), new Posn(x - 1, y - 1));
    }
    
    @Test
    public void testBallCCW() {
    	assertEquals(Direction.EAST.ballCCW(new Posn(0, 0)), new Posn(1, 1));
    	assertEquals(Direction.WEST.ballCCW(new Posn(0, 0)), new Posn(-1, -1));
    	assertEquals(Direction.NORTH.ballCCW(new Posn(0, 0)), new Posn(1, -1));
    	assertEquals(Direction.SOUTH.ballCCW(new Posn(0, 0)), new Posn(-1, 1));
    }
    
    @Property
    public void testBallCCWProperties(int x, int y) {
    	assertEquals(Direction.EAST.ballCCW(new Posn(x, y)), new Posn(x + 1, y + 1)); 
    	assertEquals(Direction.WEST.ballCCW(new Posn(x, y)), new Posn(x - 1, y - 1)); 
    	assertEquals(Direction.SOUTH.ballCCW(new Posn(x, y)), new Posn(x - 1, y + 1)); 
    	assertEquals(Direction.NORTH.ballCCW(new Posn(x, y)), new Posn(x + 1, y - 1));
    }
    
    @Property
    public void testBallProperties(Direction d, @From(PosnGenerator.class) Posn p) {
    	assertEquals(d.ballCW(p), d.nextCounterClockwiseDirection().ballCCW(p)); 
    	assertEquals(d.ballCCW(p), d.nextClockwiseDirection().ballCW(p)); 
    }
    
    @Test
    public void testEquals() {
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.WEST)), false); 
    	assertEquals(new Hit(4, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), true); 
    	assertEquals(new Hit(5, new Posn(1, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), false); 
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), true);
    	
    	assertEquals(new Exit(5, new Posn(0, 0), Direction.EAST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), false); 
    	assertEquals(new Exit(5, new Posn(0, 1), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), false); 
    	assertEquals(new Exit(3, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), true); 
    	assertEquals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
    				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), true); 
    }
    
    @Property
    public void testEqualsProperties(int bd, @From(PosnGenerator.class) Posn p, Direction d) {
    	assertEquals(new Hit(bd, p, d).equals(new Exit(bd, p, d, p)), true); 
    	assertEquals(new Hit(bd, p, d).equals(new Hit(bd, p, d)), true); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, p, d, p)), true); 
    	
    	assertEquals(new Hit(bd, p, d).equals(new Hit(bd, d.getNextPosn(p), d)), false); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, p, d, d.getNextPosn(p), d.getNextPosn(d.getNextPosn(p)))), true); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, d.getNextPosn(p), d, p, p)), false); 
    }
    
    @Test
    public void testValidStartPosition() {
    	assertEquals(new Hit(1, new Posn(-1, 3), Direction.NORTH).validStartPosition(), false); 
    	assertEquals(new Hit(1, new Posn(-1, 3), Direction.EAST).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, -1), Direction.NORTH).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, -1), Direction.SOUTH).validStartPosition(), true);
    	assertEquals(new Hit(3, new Posn(3, -1), Direction.WEST).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(3, 3), Direction.NORTH).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, 3), Direction.NORTH).validStartPosition(), true);
    }
    
    @Test 
    public void testOutOfBounds() {
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(3, -2)), true);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(5, 5)), true);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(3, 2)), false);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(0, 0)), false);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(4, -1)), true);
    }
    
    @Property
    public void testOutOfBoundsProperties(int bd, @From(PosnGenerator.class) Posn p, Direction d) {
    	assertEquals(new Hit(bd, p, d).outOfBounds(p), (p.getX() >= bd || p.getY() >= bd || p.getX() <= -1 || p.getY() <= -1)); 
    	
    	assertEquals(new Hit(bd, d.getNextPosn(p), d).outOfBounds(d.getNextPosn(p)), 
    				 new Hit(bd, d.ballCW(p), d).outOfBounds(d.ballCW(p))); 
    	assertEquals(new Hit(bd, d.getNextPosn(p), d).outOfBounds(d.getNextPosn(p)), 
				     new Hit(bd, d.ballCCW(p), d).outOfBounds(d.ballCCW(p))); 
    }

}
