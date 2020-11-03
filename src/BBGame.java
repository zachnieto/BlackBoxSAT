import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

public class BBGame {
	private AHint[] hints; 
	private String name; 
	
	BBGame(String name, AHint[] hints) {
		this.hints = hints; 
		this.name = name; 
	}
	
	//generates the characteristic boolean expession for this game 
	public String generateAllHints() {
		String expr = ""; 
		for(int i = 0; i < this.hints.length; i++) {
			expr += "(" + this.hints[i].generate() + ") & "; 
		}
		expr += "(" + AHint.t + ")"; 
		
		return expr; 
	}
	
	public void consistent(boolean fast) throws ParserException {
		String expr = this.generateAllHints(); 
		
		System.out.println("Name: " + this.name);
        System.out.println("\tRaw Expression:\t\t\t" + expr);
		
		if(!fast) {
			final FormulaFactory f = new FormulaFactory();
	        final PropositionalParser p = new PropositionalParser(f);
	        final Formula formula = p.parse(expr);
	        System.out.println("\tSimplified Expression:\t\t" + formula.toString());
	        System.out.println("\tCNF Conversion:\t\t\t" + formula.cnf().toString()); 
		}
          
        System.out.println("\tIs Consistent?:\t\t" + BooleanToCNF.satSolve(expr));
        System.out.println();
	}
	
	public void consistent() throws ParserException {
		this.consistent(true); 
	}
}
