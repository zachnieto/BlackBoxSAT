import org.logicng.datastructures.Assignment;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

import java.util.SortedSet;

public class BBGame {
	private final AHint[] hints;
	private final String name;
	private String cnfString;
	
	BBGame(String name, AHint[] hints) {
		this.hints = hints; 
		this.name = name; 
	}

	/**
	 * Generates the characteristic boolean expression for the game.
	 * @return All hints as a string
	 */
	public String generateAllHints() {
		StringBuilder expr = new StringBuilder();
		for(AHint hint : this.hints) {
			expr.append(hint.generate()).append(" & ");
		}
		expr.append(AHint.t);
		
		return expr.toString();
	}

	/**
	 * Prints all information expression and the satisfiability of the game.
	 * @throws ParserException if the expression cannot be parsed
	 */
	public void consistent() throws ParserException {
		String expr = this.generateAllHints(); 

		final FormulaFactory f = new FormulaFactory();
        final PropositionalParser p = new PropositionalParser(f);
        final Formula formula = p.parse(expr);
            
        System.out.println("Name: " + this.name);
        System.out.println("\tRaw Expression:\t\t\t" + expr);
        System.out.println("\tSimplified Expression:\t\t" + formula.toString());
        cnfString = formula.cnf().toString();
        System.out.println("\tCNF Conversion:\t\t\t" + cnfString);
        System.out.println("\tIs Consistent?:\t\t" + BooleanToCNF.satSolve(expr));
        System.out.println();
	}

	/**
	 * Displays the solution to the board based on the given hints.
	 * @throws IllegalStateException if the board hasn't been checked
	 */
	public void display() {
		if (cnfString == null) {
			throw new IllegalStateException("Board must be checked with consistent() first");
		}

		Assignment model = BooleanToCNF.getModel();
		SortedSet<Literal> literals = model.literals();

		int sideLength = (int) Math.sqrt(literals.size());
		char[][] grid = new char[sideLength][sideLength];

		for (Literal lit : literals) {
			String litStr = lit.toString();

			char trueFalse = 'O';
			if (litStr.length() > 3) {
				trueFalse = ' ';
				litStr = litStr.substring(1);
			}
			int column = Character.getNumericValue(litStr.charAt(1));
			int row = Character.getNumericValue(litStr.charAt(2));
			grid[row][column] = trueFalse;
		}

		StringBuilder disp = new StringBuilder();

		for (int r = 0; r < sideLength; r++) {
			disp.append("-".repeat(sideLength * 4)).append("-\n| ");
			for (int c = 0; c < sideLength; c++) {
				disp.append(grid[r][c]).append(" | ");
			}
			disp.append("\n");
		}
		disp.append("-".repeat(sideLength * 4)).append("-\n");
		System.out.println(disp);
	}
}
