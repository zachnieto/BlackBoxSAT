import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;

public class BooleanToCNF {


    static Formula convertToCNF(String booleanFormula) throws ParserException {

        final FormulaFactory f = new FormulaFactory();
        final PropositionalParser p = new PropositionalParser(f);
        final Formula formula = p.parse(booleanFormula);

        return formula.cnf();
    }

    static Tristate satSolve(String booleanFormula) throws ParserException {
        final FormulaFactory f = new FormulaFactory();
        final PropositionalParser p = new PropositionalParser(f);
        final Formula formula = p.parse(booleanFormula);

        final Formula cnf = formula.cnf();
        final SATSolver miniSat = MiniSat.miniSat(f);
        miniSat.add(cnf);
        return miniSat.sat();

    }

    public static void main(String[] args)  {


        AHint hit = new Hit(5, new Posn(0, 3), Direction.EAST);
        String booleanForm = hit.generate();
        Formula f = null;


        try {
            f = convertToCNF(booleanForm);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        System.out.println(f.toString());
    }






}
