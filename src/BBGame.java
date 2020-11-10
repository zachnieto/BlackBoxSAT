import org.logicng.datastructures.Assignment;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;

import java.util.SortedSet;

/**
 * This class represents a game of BlackBox. In generates all hints
 * and can tell if the board is consistent.
 */
public class BBGame {
	private final AHint[] hints;
	private final String name;
	private String cnfString;

	/**
	 * Constructs a BBGame using the title of the game and all the hints.
	 *
	 * @param name title for game
	 * @param hints list of hints
	 */
	BBGame(String name, AHint[] hints) {
		int boardDim = hints[0].getBoardDim();
		for(AHint h : hints) {
			if (h.getBoardDim() <= 0 || h.getBoardDim() != boardDim) {
				throw new IllegalArgumentException("Illegal board dimension - make sure every hint is initialized with the same board dimensions.");
			}
			if (!h.validStartPosition()) {
				throw new IllegalArgumentException("Illegal start position - start positions have to be one square outside the board"); 
			}
		}
		this.hints = hints; 
		this.name = name; 
	}

	/**
	 * Generates the characteristic boolean expression for the game.
	 * @return All hints as a string
	 */
	public String generateAllHints() {
		String expr = "";
		for(int i = 0; i < this.hints.length; i++) {
			expr += "(" + this.hints[i].generate() + ") & "; 
		}

		expr += "(" + AHint.t + ")"; 

		
		return expr;
	}
	
  	/**
	 * Prints all information, including the characteristic expression and the satisfiability of the game.
	 * @param fast determines whether all information is displayed
	 * @throws ParserException if the expression cannot be parsed
	 */
	public void consistent(boolean fast) throws ParserException {
		long startTime = System.nanoTime(); 
		
		String expr = this.generateAllHints(); 
		
		System.out.println("Name: " + this.name);
        System.out.println("\tRaw Expression:\t\t\t" + expr);
		if(!fast) {
			final FormulaFactory f = new FormulaFactory();
	        final PropositionalParser p = new PropositionalParser(f);
	        final Formula formula = p.parse(expr);
	        
	        this.cnfString = formula.cnf().toString(); 
	        
	        System.out.println("\tSimplified Expression:\t\t" + formula.toString());
	        System.out.println("\tCNF Conversion:\t\t\t" + cnfString); 
		}
          
        System.out.println("\tIs Consistent?:\t\t" + BooleanToCNF.satSolve(expr));
        System.out.println();
        
        if(!fast) {
        	try {this.display(); }
        	catch(Exception e) { System.out.println("\tNo model found: board is possibly unsatisfiable");}
        }
        else
        	System.out.println("\tTo see any potential models, try running with consistent(slow) - however, this will negatively affect runtime");
        
        long endTime = System.nanoTime(); 
        
        System.out.print("\tTime to check consistency: " + (endTime - startTime) / 1000000 + " ms\t - \t" );
        System.out.print( (fast) ? "Fast flag enabled" : "Slow flag enabled" );
        System.out.println();
	}

	/**
	 * Prints all information, including the characteristic expression and the satisfiability of the game.
	 * @throws ParserException if the expression cannot be parsed
	 */
	public void consistent() throws ParserException {
		this.consistent(true); 
    }  

	/**
	 * Displays a solution to the board based on the given hints 
	 * @throws IllegalStateException if the board hasn't been checked
	 */
	private void display() {
		if (cnfString == null) {
			throw new IllegalStateException("Board must be checked with consistent() first");
		}

		Assignment model = BooleanToCNF.getModel();
		if(model == null) {
			throw new NullPointerException("No model found - board is possibly unsatisfiable. ");
		}
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
