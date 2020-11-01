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
    void testExamples() {

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

        AHint[] hints = {unsatHint1, unsatHint2, unsatHint3, unsatHint4}; 
        //AHint[] hints = {satHint1, satHint2, satHint3, satHint4, satHint5, satHint6, satHint7, satHint8, satHint9, satHint10, satHint11, satHint12};

        String megaExpr = "";
        for (int i = 0; i < hints.length; i++) {
            megaExpr += hints[i].generate() + " & ";
        }
        megaExpr += AHint.t;

        //System.out.println(substitute("P01", "P01", true));
        String booleanForm = substitute(megaExpr, "P01", false);
        booleanForm = substitute(booleanForm, "P00", false);
        booleanForm = substitute(booleanForm, "P02", false);
        booleanForm = substitute(booleanForm, "P10", false);
        booleanForm = substitute(booleanForm, "P11", true);
        booleanForm = substitute(booleanForm, "P12", false);
        booleanForm = substitute(booleanForm, "P20", true);
        booleanForm = substitute(booleanForm, "P21", false);
        booleanForm = substitute(booleanForm, "P22", false);


        try {
            //String booleanForm = substitute(hit1.generate(), "P01", true);
            //System.out.println(exit.generate());
            //System.out.println(booleanForm);
            //System.out.println(megaExpr);

            booleanForm = megaExpr;

            final FormulaFactory f = new FormulaFactory();
            final PropositionalParser p = new PropositionalParser(f);
            final Formula formula = p.parse(booleanForm);

            System.out.println(BooleanToCNF.satSolve(booleanForm));

            System.out.println(formula.toString());
            System.out.println(formula.cnf().toString());
        } catch (
                ParserException e) {
            e.printStackTrace();
        }

    }


	/*
	public void testSubstitute(Tester t) {
		t.checkExpect(substitute("P01", "Pasd", true), "P01");
		t.checkExpect(substitute("P01", "P01", true), AHint.t);
		t.checkExpect(substitute("P01", "P01", false), AHint.nil);
	}*/

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
