import org.logicng.datastructures.Assignment;
import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;
import java.util.*; 

/**
 * This class can convert boolean strings to CNF expression, and also SatSolve expressions.
 */
public class BooleanToCNF {

    /**
     * Converts the given expression to a CNF formula.
     *
     * @param booleanFormula boolean expression
     * @return the CNF of the expression
     * @throws ParserException if the expression is invalid
     */
    static Formula convertToCNF(String booleanFormula) throws ParserException {

        final FormulaFactory f = new FormulaFactory();
        final PropositionalParser p = new PropositionalParser(f);
        final Formula formula = p.parse(booleanFormula);

        return formula.cnf();
    }

    /**
     * Sat Solves the given boolean formula.
     *
     * @param booleanFormula boolean expression
     * @return whether the formula is SAT or not.
     * @throws ParserException if the expression is invalid
     */
    static Tristate satSolve(String booleanFormula) throws ParserException {
        final FormulaFactory f = new FormulaFactory();
        final PropositionalParser p = new PropositionalParser(f);
        final Formula formula = p.parse(booleanFormula);

        final Formula cnf = formula.cnf();
        final SATSolver miniSat = MiniSat.miniSat(f);
        miniSat.add(cnf);
        Tristate result = miniSat.sat();
        
        List<Assignment> assignments = miniSat.enumerateAllModels();
        for (int i = 0; i < assignments.size(); i++) {
        	System.out.println(assignments.get(i).literals());
        }
        
        return result;
    }


}
