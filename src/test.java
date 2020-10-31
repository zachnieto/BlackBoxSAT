import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

public class test {

	public static void main(String[] args) {
		AHint exit = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0), new Posn(-1, 0));
		
		try {
			String booleanForm = exit.generate();

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
