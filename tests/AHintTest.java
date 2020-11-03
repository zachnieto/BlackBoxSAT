import org.junit.jupiter.api.Test;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for AHints.
 */
class AHintTest {
	
    @Test
    void testExamples() throws ParserException {
    	final boolean fast = true; 
    	
    	AHint unsatHint1 = new Exit(3, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1), new Posn(1, -1));
		AHint unsatHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
		AHint unsatHint3 = new Hit(3, new Posn(0, -1), Direction.SOUTH);
		AHint unsatHint4 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2), new Posn(3, 2));
    	
    	
        AHint satHint1 = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0), new Posn(0, -1));
        AHint satHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
        AHint satHint3 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2), new Posn(0, 3));
        AHint satHint4 = new Exit(3, new Posn(0, 3), Direction.NORTH, new Posn(0, 3), new Posn(-1, 2));
        AHint satHint5 = new Hit(3, new Posn(1, 3), Direction.NORTH);
        AHint satHint6 = new Exit(3, new Posn(2, 3), Direction.NORTH, new Posn(2, 3), new Posn(3, 2));
        AHint satHint7 = new Exit(3, new Posn(3, 2), Direction.WEST, new Posn(3, 2), new Posn(2, 3));
        AHint satHint8 = new Exit(3, new Posn(3, 1), Direction.WEST, new Posn(3, 1), new Posn(3, 1));
        AHint satHint9 = new Hit(3, new Posn(3, 0), Direction.WEST);
        AHint satHint10 = new Hit(3, new Posn(2, -1), Direction.SOUTH);
        AHint satHint11 = new Exit(3, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1), new Posn(1, -1));
        AHint satHint12 = new Exit(3, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1), new Posn(-1, 0));
        
        AHint bigSat1 = new Exit(5, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0), new Posn(0, -1)); //yes
        AHint bigSat2 = new Hit(5, new Posn(-1, 1), Direction.EAST); // yes
        AHint bigSat3 = new Hit(5, new Posn(-1, 2), Direction.EAST); // yes
        AHint bigSat4 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(-1, 3), new Posn(-1, 3)); // yes
        AHint bigSat5 = new Hit(5, new Posn(-1, 4), Direction.EAST); // yes
        AHint bigSat6 = new Hit(5, new Posn(0, 5), Direction.NORTH); // yes
        AHint bigSat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(1, 5), new Posn(1, 5));  // yes
        AHint bigSat8 = new Hit(5, new Posn(2, 5), Direction.NORTH); // yes
        AHint bigSat9 = new Hit(5, new Posn(3, 5), Direction.NORTH); // yes
        AHint bigSat10 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(4, 5), new Posn(5, 3)); // yes
        AHint bigSat11 = new Hit(5, new Posn(5, 4), Direction.WEST); // yes
        AHint bigSat12 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(5, 3), new Posn(4, 5)); // yes
        AHint bigSat13 = new Hit(5, new Posn(5, 2), Direction.WEST); 
        AHint bigSat14 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(5, 1), new Posn(4, -1)); 
        AHint bigSat15 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(5, 0), new Posn(2, -1)); 
        AHint bigSat16 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(4, -1), new Posn(5, 1)); 
        AHint bigSat17 = new Hit(5, new Posn(3, -1), Direction.SOUTH); 
        AHint bigSat18 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(2, -1), new Posn(5, 0)); 
        AHint bigSat19 = new Hit(5, new Posn(1, -1), Direction.SOUTH); 

        AHint bigSat20 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1), new Posn(-1, 0));  

        
        
        AHint[] unSatHints = {unsatHint1, unsatHint2, unsatHint3, unsatHint4}; 
        AHint[] satHints = {satHint1, satHint2, satHint3, satHint4, satHint5, satHint6, satHint7, satHint8, satHint9, satHint10, satHint11, satHint12};
        AHint[] bigBoard = {bigSat1, bigSat2, bigSat3, bigSat4, bigSat5, bigSat6, bigSat7, bigSat8, bigSat9, bigSat10, bigSat11, bigSat12, bigSat13, bigSat14, bigSat15, bigSat16, bigSat17, bigSat18, bigSat19, bigSat20};
       
        BBGame unsatEx1 = new BBGame("unsat 3x3", unSatHints); 
        BBGame satEx2 = new BBGame("sat 3x3", satHints);
        BBGame bigGame = new BBGame("sat 5x5", bigBoard); 
        
        unsatEx1.consistent(!fast); 
        satEx2.consistent(!fast); 
        bigGame.consistent(fast);
        
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
        
        //System.out.println(BooleanToCNF.satSolve(booleanForm)); 

        //unsatEx1.consistent();
        //satEx2.consistent();
        //satEx2.display();
        bigGame.consistent();
        bigGame.display();



    }

    @Test
    public void testSubstitute() {
        assertEquals(substitute("P01", "Pasd", true), "P01");
        assertEquals(substitute("P01", "P01", true), AHint.t);
        assertEquals(substitute("P01", "P01", false), AHint.nil);
    }

    /**
     * Substitutes each occurrence of var in the expression with the b value.
     * Used to make testing easier, but has no value in the main code suite.  
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

}
