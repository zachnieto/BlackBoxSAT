import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

public class test {

	public static void main(String[] args) {
		IHint hit = new Hit(3, 0, 1, 1);




		try {
			String booleanForm = hit.generate();
			//booleanForm = "P01 | (~P10 & ~P12 => ((P11 | (~P20 & ~P22 => (P21 & P20 & ~P22 => (P12 & ~P20 & P22 => (P10 & P20 & P22 => 0))))) & P10 & ~P12 => (P02 & ~P10 & P12 => (P00 & P10 & P12 => 0))))";
			//booleanForm = "(D & ~A & B => (C & A & B => 0))";
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
