import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

public class test {

	public static void main(String[] args) {
		AHint hit = new Hit(3, new Posn(0, 1), Direction.EAST);

		try {
			String booleanForm = hit.generate();

			System.out.println(booleanForm);

			final FormulaFactory f = new FormulaFactory();
			final PropositionalParser p = new PropositionalParser(f);
			final Formula formula = p.parse(booleanForm);

			System.out.println(BooleanToCNF.satSolve(booleanForm));

			System.out.println(formula.toString());
			System.out.println(formula.cnf().toString());
		} catch (ParserException e) {
			e.printStackTrace();
		}

	}
}
