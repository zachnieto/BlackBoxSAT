import org.sat4j.*;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

public class test {

	public static void main(String[] args) {
		IHint hit = new Hit(3, 0, 1, 1); 
		
		System.out.println(hit.generate());
		
		
//		ISolver solver = SolverFactory.newDefault();
//		solver.newVar(3); //CNF 
//		solver.setExpectedNumberOfClauses(8);
//		
//		//int makeUnsat = 1; 
//		
//		int[][] clauses = { {1, 2, 3},  // (x or y or z) and
//							{1, 2, -3}, // (x or y or -z) and
//							{1, -2, 3}, // (x or -y or z)
//							{1, -2, -3}, // (x or -y or -z)
//							{-1, 2, 3},  // (-x or y or z)
//							{-1, 2, -3}, // ...
//							{-1, -2, 3},
//							{-1, -2, -3} }; 
//		
//		for(int i = 0; i < clauses.length; i++) {
//			try {
//			solver.addClause(new VecInt(clauses[i])); 
//			} catch(Exception e) {e.printStackTrace(); }
//			
//		}
//		
//		IProblem problem = solver; 
//		
//		boolean sat = false;
//		try {sat = problem.isSatisfiable(); }
//		catch(Exception e) { e.printStackTrace(); }
//		
//		if(sat) {
//			System.out.println("is sat");
//		}
//		else 
//			System.out.println("not sat");
	}
}
