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

        AHint exit = new Exit(3, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1), new Posn(1, -1));
		AHint hit1 = new Hit(3, new Posn(-1, 1), Direction.EAST);
		AHint hit2 = new Hit(3, new Posn(0, -1), Direction.SOUTH);
		AHint exit2 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2), new Posn(3, 2));
        AHint exit1 = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0), new Posn(0, -1));
        //AHint hit2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
        AHint exit3 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2), new Posn(0, 3));
        AHint exit4 = new Exit(3, new Posn(0, 3), Direction.NORTH, new Posn(0, 3), new Posn(-1, 2));
        AHint hit5 = new Hit(3, new Posn(1, 3), Direction.NORTH);
        AHint exit6 = new Exit(3, new Posn(2, 3), Direction.NORTH, new Posn(2, 3), new Posn(3, 2));
        AHint exit7 = new Exit(3, new Posn(3, 2), Direction.WEST, new Posn(3, 2), new Posn(2, 3));
        AHint reflect8 = new Exit(3, new Posn(3, 1), Direction.WEST, new Posn(3, 1), new Posn(3, 1));
        AHint hit9 = new Hit(3, new Posn(3, 0), Direction.WEST);
        AHint hit10 = new Hit(3, new Posn(2, -1), Direction.SOUTH);
        AHint reflect11 = new Exit(3, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1), new Posn(1, -1));
        AHint exit12 = new Exit(3, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1), new Posn(-1, 0));


        AHint[] hints = {exit1, hit2, exit3, exit4, hit5, exit6, exit7, reflect8, hit9, hit10, reflect11, exit12};

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
     *
     * @param expr expression to have replacements.
     * @param var variable to be replaced
     * @param b value to be inserted
     * @return the new substituted expression
     */
    public static String substitute(String expr, String var, boolean b) {
        if (b == true)
            return expr.replaceAll(var, AHint.t);
        else
            return expr.replaceAll(var, AHint.nil);
    }

}