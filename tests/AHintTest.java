import org.junit.jupiter.api.Test;
import org.logicng.io.parsers.ParserException;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing class for AHints.
 */
@RunWith(JUnitQuickcheck.class)
public class AHintTest {
	

    /**
     * Substitutes each occurrence of var in the expression with the b value.
     * Used to make testing certain atom configurations more convenient, but has no value in the main code suite.  
     * 
     * @param expr expression to have replacements.
     * @param var variable to be replaced
     * @param b value to be inserted
     * @return the new substituted expression
     */
    public static String substitute(String expr, String var, boolean b) {
        if (b)
            return expr.replaceAll(var, AHint.t);
        else
            return expr.replaceAll(var, AHint.nil);
    }

    @Test
    void testLiveExample() throws ParserException {
        final boolean fast = true, slow = false;

        AHint hint1 = new Hit(5, new Posn(0, -1), Direction.SOUTH);
        AHint hint2 = new Exit(5, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1));
        AHint hint3 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(5, 1));
        AHint hint4 = new Hit(5, new Posn(3, -1), Direction.SOUTH);
        AHint hint5 = new Hit(5, new Posn(4, -1), Direction.SOUTH);
        AHint hint6 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint hint7 = new Exit(5, new Posn(5, 2), Direction.WEST, new Posn(5, 2));
        AHint hint8 = new Hit(5, new Posn(5, 3), Direction.WEST);
        AHint hint9 = new Exit(5, new Posn(5, 4), Direction.WEST, new Posn(5, 4));
        AHint hint10 = new Hit(5, new Posn(4, 5), Direction.NORTH);
        AHint hint11 = new Exit(5, new Posn(3, 5), Direction.NORTH, new Posn(-1, 4));
        AHint hint12 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint hint13 = new Hit(5, new Posn(1, 5), Direction.NORTH);
        AHint hint14 = new Exit(5, new Posn(0, 5), Direction.NORTH, new Posn(-1, 3));
        AHint hint15 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint hint16 = new Exit(5, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1));
        AHint hint17 = new Hit(5, new Posn(-1, 0), Direction.EAST);

        AHint[] hints = {   hint1, hint2, hint3, hint4,
                hint5, hint6, hint7, hint8,
                hint9, hint10, hint11, hint12,
                hint13, hint14, hint15, hint16, hint17};

        BBGame onlineGame = new BBGame("online 5x5", hints);

        onlineGame.consistent(slow);
    }

    @Test
    void testSingleBoard() throws ParserException {
        final boolean fast = true, slow = false;

        AHint hint1 = new Hit(5, new Posn(0, -1), Direction.SOUTH);
        AHint hint2 = new Exit(5, new Posn(1, -1), Direction.SOUTH, new Posn(5, 1));
        AHint hint3 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(5, 2));
        AHint hint4 = new Exit(5, new Posn(3, -1), Direction.SOUTH, new Posn(5, 3));
        AHint hint5 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(4, 5));
        AHint hint6 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(-1, 0));
        AHint hint7 = new Hit(5, new Posn(5, 4), Direction.WEST);
        AHint hint8 = new Exit(5, new Posn(3, 5), Direction.NORTH, new Posn(3, 5));
        AHint hint9 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint hint10 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(1, 5));
        AHint hint11 = new Exit(5, new Posn(0, 5), Direction.NORTH, new Posn(-1, 4));
        AHint hint12 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(-1, 3));
        AHint hint13 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint hint14 = new Exit(5, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1));

        AHint[] hints = {   hint1, hint2, hint3, hint4,
                hint5, hint6, hint7, hint8,
                hint9, hint10, hint11, hint12,
                hint13, hint14};

        BBGame onlineGame = new BBGame("online 5x5", hints);

        onlineGame.consistent(slow);
    }

    @Test
    void testSingleBoardMultipleSolutions() throws ParserException {
        final boolean fast = true, slow = false;

        AHint hint1 = new Hit(5, new Posn(0, -1), Direction.SOUTH);
        AHint hint2 = new Exit(5, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1));
        AHint hint3 = new Hit(5, new Posn(2, -1), Direction.SOUTH);
        AHint hint4 = new Exit(5, new Posn(3, -1), Direction.SOUTH, new Posn(3, -1));
        AHint hint5 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(5, 0));
        AHint hint6 = new Hit(5, new Posn(5, 1), Direction.WEST);
        AHint hint7 = new Exit(5, new Posn(5, 2), Direction.WEST, new Posn(4, 5));
        AHint hint8 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(1, 5));
        AHint hint9 = new Exit(5, new Posn(5, 4), Direction.WEST, new Posn(-1, 4));
        AHint hint10 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint hint11 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint hint12 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint hint13 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(-1, 3));
        AHint hint14 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint hint15 = new Exit(5, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1));
        AHint hint16 = new Hit(5, new Posn(-1, 0), Direction.EAST);

        AHint[] hints = {   hint1, hint2, hint3, hint4,
                            hint5, hint6, hint7, hint8,
                            hint9, hint10, hint11, hint12,
                            hint13, hint14, hint15, hint16  };

        BBGame onlineGame = new BBGame("online 5x5", hints);

        onlineGame.consistent(slow);
    }

    @Test
    void testBoards() throws ParserException {
    	final boolean fast = true;
    	final boolean slow = !fast;

    	//hint-set for "unsat 3x3"
    	AHint unsatHint1 = new Exit(3, new Posn(-1, 1), Direction.EAST, new Posn(1, -1));
		AHint unsatHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
		AHint unsatHint3 = new Hit(3, new Posn(0, -1), Direction.SOUTH);
		AHint unsatHint4 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(3, 2));

		//hint-set for "sat 3x3"
        AHint satHint1 = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(0, -1));
        AHint satHint2 = new Hit(3, new Posn(-1, 1), Direction.EAST);
        AHint satHint3 = new Exit(3, new Posn(-1, 2), Direction.EAST, new Posn(0, 3));
        AHint satHint4 = new Exit(3, new Posn(0, 3), Direction.NORTH, new Posn(-1, 2));
        AHint satHint5 = new Hit(3, new Posn(1, 3), Direction.NORTH);
        AHint satHint6 = new Exit(3, new Posn(2, 3), Direction.NORTH, new Posn(3, 2));
        AHint satHint7 = new Exit(3, new Posn(3, 2), Direction.WEST, new Posn(2, 3));
        AHint satHint8 = new Exit(3, new Posn(3, 1), Direction.WEST, new Posn(3, 1));
        AHint satHint9 = new Hit(3, new Posn(3, 0), Direction.WEST);
        AHint satHint10 = new Hit(3, new Posn(2, -1), Direction.SOUTH);
        AHint satHint11 = new Exit(3, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1));
        AHint satHint12 = new Exit(3, new Posn(0, -1), Direction.SOUTH, new Posn(-1, 0));

        //hint-set for "sat 5x5"
        AHint bigSat1 = new Exit(5, new Posn(-1, 0), Direction.EAST, new Posn(0, -1));
        AHint bigSat2 = new Hit(5, new Posn(-1, 1), Direction.EAST);
        AHint bigSat3 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint bigSat4 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(-1, 3));
        AHint bigSat5 = new Hit(5, new Posn(-1, 4), Direction.EAST);
        AHint bigSat6 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint bigSat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(1, 5));
        AHint bigSat8 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint bigSat9 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint bigSat10 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(5, 3));
        AHint bigSat11 = new Hit(5, new Posn(5, 4), Direction.WEST);
        AHint bigSat12 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(4, 5));
        AHint bigSat13 = new Hit(5, new Posn(5, 2), Direction.WEST);
        AHint bigSat14 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(4, -1));
        AHint bigSat15 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(2, -1));
        AHint bigSat16 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(5, 1));
        AHint bigSat17 = new Hit(5, new Posn(3, -1), Direction.SOUTH);
        AHint bigSat18 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(5, 0));
        AHint bigSat19 = new Hit(5, new Posn(1, -1), Direction.SOUTH);
        AHint bigSat20 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(-1, 0));

        //hint-set for "unsat 5x5"
        AHint bigUnsat1 = new Hit(5, new Posn(4, -1), Direction.SOUTH);
        AHint bigUnsat2 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(5, 1));
        AHint bigUnsat3 = new Exit(5, new Posn(5, 2), Direction.WEST, new Posn(5, 2));
        AHint bigUnsat4 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(5, 3));
        AHint bigUnsat5 = new Hit(5, new Posn(4, 5), Direction.NORTH);
        AHint bigUnsat6 = new Exit(5, new Posn(5, 4), Direction.WEST, new Posn(5, 4));
        AHint bigUnsat7 = new Exit(5, new Posn(5, 0), Direction.WEST, new Posn(5, 0));

        //hint-set for "online1 5x5"
        AHint online1Sat1 = new Exit(5, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0));
        AHint online1Sat2 = new Hit(5, new Posn(-1, 1), Direction.EAST);
        AHint online1Sat3 = new Exit(5, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2));
        AHint online1Sat4 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(3, -1));
        AHint online1Sat5 = new Hit(5, new Posn(-1, 4), Direction.EAST);
        AHint online1Sat6 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint online1Sat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(5, 2));
        AHint online1Sat8 = new Exit(5, new Posn(2, 5), Direction.NORTH, new Posn(5, 1));
        AHint online1Sat9 = new Exit(5, new Posn(3, 5), Direction.NORTH, new Posn(3, 5));
        AHint online1Sat10 = new Hit(5, new Posn(4, 5), Direction.NORTH);
        AHint online1Sat11 = new Hit(5, new Posn(5, 4), Direction.WEST);
        AHint online1Sat12 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(5, 3));
        AHint online1Sat13 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint online1Sat14 = new Hit(5, new Posn(4, -1), Direction.SOUTH);
        AHint online1Sat15 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(2, -1));
        AHint online1Sat16 = new Hit(5, new Posn(1, -1), Direction.SOUTH);
        AHint online1Sat17 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1));

        //hint-set for "online2"
        AHint online2Sat1 = new Hit(5, new Posn(-1, 0), Direction.EAST);
        AHint online2Sat2 = new Hit(5, new Posn(-1, 1), Direction.EAST);
        AHint online2Sat3 = new Exit(5, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2));
        AHint online2Sat4 = new Hit(5, new Posn(-1, 3), Direction.EAST);
        AHint online2Sat5 = new Exit(5, new Posn(-1, 4), Direction.EAST, new Posn(-1, 4));
        AHint online2Sat6 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint online2Sat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(5, 4));
        AHint online2Sat8 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint online2Sat9 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint online2Sat10 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(5, 3));
        AHint online2Sat11 = new Hit(5, new Posn(5, 2), Direction.WEST);
        AHint online2Sat12 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint online2Sat13 = new Hit(5, new Posn(3, -1), Direction.SOUTH);
        AHint online2Sat14 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(2, -1));
        AHint online2Sat15 = new Hit(5, new Posn(1, -1), Direction.SOUTH);
        AHint online2Sat16 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1));
        AHint online2Sat17 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(4, -1));

        //hintset for "online3"
        AHint online3Sat1 = new Hit(5, new Posn(-1, 0), Direction.EAST);
        AHint online3Sat2 = new Exit(5, new Posn(-1, 1), Direction.EAST, new Posn(-1, 1));
        AHint online3Sat3 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint online3Sat4 = new Hit(5, new Posn(-1, 3), Direction.EAST);
        AHint online3Sat5 = new Exit(5, new Posn(-1, 4), Direction.EAST, new Posn(2, 5));
        AHint online3Sat6 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint online3Sat7 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(1, 5));
        AHint online3Sat8 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint online3Sat9 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(5, 4));
        AHint online3Sat10 = new Hit(5, new Posn(5, 3), Direction.WEST);
        AHint online3Sat11 = new Exit(5, new Posn(5, 2), Direction.WEST, new Posn(4, -1));
        AHint online3Sat12 = new Hit(5, new Posn(5, 1), Direction.WEST);
        AHint online3Sat13 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint online3Sat14 = new Exit(5, new Posn(3, -1), Direction.SOUTH, new Posn(3, -1));
        AHint online3Sat15 = new Hit(5, new Posn(2, -1), Direction.SOUTH);
        AHint online3Sat16 = new Exit(5, new Posn(1, -1), Direction.SOUTH, new Posn(1, -1));
        AHint online3Sat17 = new Hit(5, new Posn(0, -1), Direction.SOUTH);

        //hintset for "online4"
        AHint online4Sat1 = new Hit(5, new Posn(-1, 0), Direction.EAST);
        AHint online4Sat2 = new Hit(5, new Posn(-1, 1), Direction.EAST);
        AHint online4Sat3 = new Exit(5, new Posn(-1, 2), Direction.EAST, new Posn(5, 2));
        AHint online4Sat4 = new Exit(5, new Posn(-1, 3), Direction.EAST, new Posn(0, -1));
        AHint online4Sat5 = new Hit(5, new Posn(-1, 4), Direction.EAST);
        AHint online4Sat6 = new Exit(5, new Posn(0, 5), Direction.NORTH, new Posn(0, 5));
        AHint online4Sat7 = new Exit(5, new Posn(2, 5), Direction.NORTH, new Posn(2, 5));
        AHint online4Sat8 = new Hit(5, new Posn(1, 5), Direction.NORTH);
        AHint online4Sat9 = new Exit(5, new Posn(4, 5), Direction.NORTH, new Posn(4, 5));
        AHint online4Sat10 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint online4Sat11 = new Hit(5, new Posn(5, 4), Direction.WEST);
        AHint online4Sat12 = new Hit(5, new Posn(5, 3), Direction.WEST);
        AHint online4Sat13 = new Exit(5, new Posn(5, 1), Direction.WEST, new Posn(5, 1));
        AHint online4Sat14 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint online4Sat15 = new Hit(5, new Posn(4, -1), Direction.SOUTH);
        AHint online4Sat16 = new Exit(5, new Posn(3, -1), Direction.SOUTH, new Posn(3, -1));
        AHint online4Sat17 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(2, -1));
        AHint online4Sat18 = new Hit(5, new Posn(1, -1), Direction.SOUTH);

        AHint online5Sat1 = new Exit(5, new Posn(0, -1), Direction.SOUTH, new Posn(0, -1));
        AHint online5Sat2 = new Hit(5, new Posn(1, -1), Direction.SOUTH);
        AHint online5Sat3 = new Exit(5, new Posn(2, -1), Direction.SOUTH, new Posn(2, -1));
        AHint online5Sat4 = new Exit(5, new Posn(3, -1), Direction.SOUTH, new Posn(5, 2));
        AHint online5Sat5 = new Hit(5, new Posn(4, -1), Direction.SOUTH);
        AHint online5Sat6 = new Hit(5, new Posn(5, 0), Direction.WEST);
        AHint online5Sat7 = new Hit(5, new Posn(5, 1), Direction.WEST);
        AHint online5Sat8 = new Exit(5, new Posn(5, 3), Direction.WEST, new Posn(5, 3));
        AHint online5Sat9 = new Hit(5, new Posn(5, 4), Direction.WEST);
        AHint online5Sat10 = new Hit(5, new Posn(4, 5), Direction.NORTH);
        AHint online5Sat11 = new Exit(5, new Posn(3, 5), Direction.NORTH, new Posn(3, 5));
        AHint online5Sat12 = new Hit(5, new Posn(2, 5), Direction.NORTH);
        AHint online5Sat13 = new Exit(5, new Posn(1, 5), Direction.NORTH, new Posn(-1, 4));
        AHint online5Sat14 = new Exit(5, new Posn(0, 5), Direction.NORTH, new Posn(-1, 1));
        AHint online5Sat15 = new Hit(5, new Posn(-1, 3), Direction.EAST);
        AHint online5Sat16 = new Hit(5, new Posn(-1, 2), Direction.EAST);
        AHint online5Sat17 = new Hit(5, new Posn(-1, 0), Direction.EAST);

        AHint online6Sat1 = new Exit(6, new Posn(0, -1), Direction.SOUTH, new Posn(0, 6));
        AHint online6Sat2 = new Exit(6, new Posn(1, -1), Direction.SOUTH, new Posn(-1, 2));
        AHint online6Sat3 = new Exit(6, new Posn(2, -1), Direction.SOUTH, new Posn(-1, 0));
        AHint online6Sat4 = new Exit(6, new Posn(3, -1), Direction.SOUTH, new Posn(3, -1));
        AHint online6Sat5 = new Hit(6, new Posn(4, -1), Direction.SOUTH);
        AHint online6Sat6 = new Exit(6, new Posn(5, -1), Direction.SOUTH, new Posn(3, -1));
        AHint online6Sat7 = new Hit(6, new Posn(6, 0), Direction.WEST);
        AHint online6Sat8 = new Hit(6, new Posn(6, 1), Direction.WEST);
        AHint online6Sat9 = new Exit(6, new Posn(6, 2), Direction.WEST, new Posn(5, 6));
        AHint online6Sat10 = new Hit(6, new Posn(6, 3), Direction.WEST);
        AHint online6Sat11 = new Exit(6, new Posn(6, 4), Direction.WEST, new Posn(3, 6));
        AHint online6Sat12 = new Exit(6, new Posn(6, 5), Direction.WEST, new Posn(-1, 5));
        AHint online6Sat13 = new Hit(6, new Posn(4, 6), Direction.NORTH);
        AHint online6Sat14 = new Hit(6, new Posn(2, 6), Direction.NORTH);
        AHint online6Sat15 = new Exit(6, new Posn(1, 6), Direction.NORTH, new Posn(-1, 4));
        AHint online6Sat16 = new Hit(6, new Posn(-1, 3), Direction.EAST);
        AHint online6Sat17 = new Hit(6, new Posn(-1, 1), Direction.EAST);
        AHint online6Sat18 = new Exit(6, new Posn(5, -1), Direction.SOUTH, new Posn(5, -1));

        AHint online7Sat1 = new Hit(5, new Posn(0, -1), Direction.SOUTH);
        AHint online7Sat2 = new Exit(5, new Posn(1, -1), Direction.SOUTH, new Posn(5, 0));
        AHint online7Sat3 = new Hit(5, new Posn(2, -1), Direction.SOUTH);
        AHint online7Sat4 = new Hit(5, new Posn(3, -1), Direction.SOUTH);
        AHint online7Sat5 = new Exit(5, new Posn(4, -1), Direction.SOUTH, new Posn(5, 1));
        AHint online7Sat6 = new Hit(5, new Posn(5, 2), Direction.WEST);
        AHint online7Sat7 = new Hit(5, new Posn(5, 3), Direction.WEST);
        AHint online7Sat8 = new Exit(5, new Posn(5, 4), Direction.WEST, new Posn(4, 5));
        AHint online7Sat9 = new Hit(5, new Posn(3, 5), Direction.NORTH);
        AHint online7Sat10 = new Exit(5, new Posn(2, 5), Direction.NORTH, new Posn(-1, 4));
        AHint online7Sat11 = new Hit(5, new Posn(1, 5), Direction.NORTH);
        AHint online7Sat12 = new Hit(5, new Posn(0, 5), Direction.NORTH);
        AHint online7Sat13 = new Hit(5, new Posn(-1, 3), Direction.EAST);
        AHint online7Sat14 = new Exit(5, new Posn(-1, 2), Direction.EAST, new Posn(-1, 2));
        AHint online7Sat15 = new Hit(5, new Posn(-1, 1), Direction.EAST);
        AHint online7Sat16 = new Exit(5, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0));

        AHint[] unSatHints = {unsatHint1, unsatHint2, unsatHint3, unsatHint4};
        AHint[] satHints = {satHint1, satHint2, satHint3, satHint4, satHint5, satHint6, satHint7, satHint8, satHint9, satHint10, satHint11, satHint12};
        AHint[] bigBoard = {bigSat1, bigSat2, bigSat3, bigSat4, bigSat5, bigSat6, bigSat7, bigSat8, bigSat9, bigSat10, bigSat11, bigSat12, bigSat13, bigSat14, bigSat15, bigSat16, bigSat17, bigSat18, bigSat19, bigSat20};
        AHint[] bigUnsatBoard = {bigUnsat1, bigUnsat2, bigUnsat3, bigUnsat4, bigUnsat5, bigUnsat6, bigUnsat7};
        AHint[] online1Board = {online1Sat1, online1Sat2, online1Sat3, online1Sat4, online1Sat5, online1Sat6, online1Sat7, online1Sat8, online1Sat9, online1Sat10, online1Sat11, online1Sat12, online1Sat13, online1Sat14, online1Sat15, online1Sat16, online1Sat17};
        AHint[] online2Board = {online2Sat1, online2Sat2, online2Sat3, online2Sat4, online2Sat5, online2Sat6, online2Sat7, online2Sat8, online2Sat9, online2Sat10, online2Sat11, online2Sat12, online2Sat13, online2Sat14, online2Sat15, online2Sat16, online2Sat17};
        AHint[] online3Board = {online3Sat1, online3Sat2, online3Sat3, online3Sat4, online3Sat5, online3Sat6, online3Sat7, online3Sat8, online3Sat9, online3Sat10, online3Sat11, online3Sat12, online3Sat13, online3Sat14, online3Sat15, online3Sat16, online3Sat17};
        AHint[] online4Board = {online4Sat1, online4Sat2, online4Sat3, online4Sat4, online4Sat5, online4Sat6, online4Sat7, online4Sat8, online4Sat9, online4Sat10, online4Sat11, online4Sat12, online4Sat13, online4Sat14, online4Sat15, online4Sat16, online4Sat17, online4Sat18};
        AHint[] online5Board= {online5Sat1, online5Sat2, online5Sat3, online5Sat4, online5Sat5, online5Sat6, online5Sat7, online5Sat8, online5Sat9, online5Sat10, online5Sat11, online5Sat12, online5Sat13, online5Sat14, online5Sat15, online5Sat15, online5Sat16, online5Sat17};
        AHint[] online6Board = {online6Sat1, online6Sat2, online6Sat3, online6Sat4, online6Sat5, online6Sat6, online6Sat7, online6Sat8, online6Sat9, online6Sat10, online6Sat11, online6Sat12, online6Sat13, online6Sat14, online6Sat15, online6Sat16, online6Sat17, online6Sat18};
        AHint[] online7Board = {online7Sat1, online7Sat2, online7Sat3, online7Sat4, online7Sat5, online7Sat6, online7Sat7, online7Sat8, online7Sat9, online7Sat10, online7Sat11, online7Sat12, online7Sat13, online7Sat14, online7Sat15, online7Sat16};

        BBGame unsatEx1 = new BBGame("unsat 3x3", unSatHints);
        BBGame satEx2 = new BBGame("sat 3x3", satHints);
        BBGame bigGame = new BBGame("sat 5x5", bigBoard);
        BBGame bigUnsatGame = new BBGame("unsat 5x5", bigUnsatBoard);
        BBGame online1 = new BBGame("online 1 5x5", online1Board);
        BBGame online2 = new BBGame("online 2 5x5", online2Board);
        BBGame online3 = new BBGame("online 3 5x5", online3Board);
        BBGame online4 = new BBGame("online 4 5x5", online4Board);
        BBGame online5 = new BBGame("online 5 5x5", online4Board);
        BBGame online6 = new BBGame("online 6 6x6", online6Board);
        BBGame online7 = new BBGame("online 7 5x5", online7Board);

        //unsatEx1.consistent(fast);
        //satEx2.consistent(fast);
        //bigGame.consistent(fast);
        //bigUnsatGame.consistent(fast);
        //online1.consistent(slow);
        //online2.consistent(fast);
        //online3.consistent(fast);
        //online4.consistent(slow);
        //online5.consistent(slow);
        online7.consistent(slow);



        //online6Board.consistent(fast);


        
        String booleanForm = online2.generateAllHints(); 
        booleanForm = substitute(booleanForm, "P00", false); 
        booleanForm = substitute(booleanForm, "P01", false); 
        booleanForm = substitute(booleanForm, "P02", false); 
        booleanForm = substitute(booleanForm, "P03", true); 
        booleanForm = substitute(booleanForm, "P04", false); 
        
        booleanForm = substitute(booleanForm, "P10", true); 
        booleanForm = substitute(booleanForm, "P11", false); 
        booleanForm = substitute(booleanForm, "P12", false); 
        booleanForm = substitute(booleanForm, "P13", false); 
        booleanForm = substitute(booleanForm, "P14", false); 
        
        booleanForm = substitute(booleanForm, "P20", false); 
        booleanForm = substitute(booleanForm, "P21", false); 
        booleanForm = substitute(booleanForm, "P22", false); 
        booleanForm = substitute(booleanForm, "P23", false); 
        booleanForm = substitute(booleanForm, "P24", false); 
        
        booleanForm = substitute(booleanForm, "P30", false); 
        booleanForm = substitute(booleanForm, "P31", false); 
        booleanForm = substitute(booleanForm, "P32", true); 
        booleanForm = substitute(booleanForm, "P33", false); 
        booleanForm = substitute(booleanForm, "P34", false); 
        
        booleanForm = substitute(booleanForm, "P40", false); 
        booleanForm = substitute(booleanForm, "P41", false); 
        booleanForm = substitute(booleanForm, "P42", false); 
        booleanForm = substitute(booleanForm, "P43", false); 
        booleanForm = substitute(booleanForm, "P44", false); 
        
        //System.out.println(BooleanToCNF.satSolve(booleanForm));
        
    }

    @Test
    public void testSubstitute() {
        assertEquals(substitute("P01", "Pasd", true), "P01");
        assertEquals(substitute("P01", "P01", true), AHint.t);
        assertEquals(substitute("P01", "P01", false), AHint.nil);
    }
    
    @Test
    public void testDirectionChanges() {
    	assertEquals(Direction.EAST.nextClockwiseDirection(), Direction.SOUTH);
    	assertEquals(Direction.SOUTH.nextClockwiseDirection(), Direction.WEST);
    	assertEquals(Direction.WEST.nextClockwiseDirection(), Direction.NORTH);
    	assertEquals(Direction.NORTH.nextClockwiseDirection(), Direction.EAST);
    	assertEquals(Direction.EAST.nextCounterClockwiseDirection(), Direction.NORTH);
    	assertEquals(Direction.SOUTH.nextCounterClockwiseDirection(), Direction.EAST);
    	assertEquals(Direction.WEST.nextCounterClockwiseDirection(), Direction.SOUTH);
    	assertEquals(Direction.NORTH.nextCounterClockwiseDirection(), Direction.WEST);
    }
    
    @Property
    public void testDirectionProperties(Direction d) {
    	assertEquals(d.nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection(), d); 
    	assertEquals(d.nextCounterClockwiseDirection()
				  	  .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection(), d); 
    	assertEquals(d.nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection()
    				  .nextClockwiseDirection(), 
    				 d.nextCounterClockwiseDirection()
				  	  .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()
				      .nextCounterClockwiseDirection()); 
    }
    
    @Test
    public void testGetNextPosn() {
    	assertEquals(Direction.EAST.getNextPosn(new Posn(0, 0)), new Posn(1, 0)); 
    	assertEquals(Direction.WEST.getNextPosn(new Posn(0, 0)), new Posn(-1, 0)); 
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(0, 0)), new Posn(0, 1)); 
    	assertEquals(Direction.NORTH.getNextPosn(new Posn(0, 0)), new Posn(0, -1)); 
    	
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(-234, 12300)), new Posn(-234, 12301));
    	assertEquals(Direction.WEST.getNextPosn(new Posn (-234, 12300)), new Posn(-235, 12300)); 
    }
    
    @Property
    public void testGetNextPosnProperties(int x, int y) {
    	assertEquals(Direction.EAST.getNextPosn(new Posn(x, y)), new Posn(x + 1, y));
    	assertEquals(Direction.NORTH.getNextPosn(new Posn(x, y)), new Posn(x, y - 1));
    	assertEquals(Direction.WEST.getNextPosn(new Posn(x, y)), new Posn(x - 1, y));
    	assertEquals(Direction.SOUTH.getNextPosn(new Posn(x, y)), new Posn(x, y + 1));
    }
    
    @Test
    public void testatomCW() {
    	assertEquals(Direction.EAST.atomCW(new Posn(0, 0)), new Posn(1, -1)); 
    	assertEquals(Direction.WEST.atomCW(new Posn(0, 0)), new Posn(-1, 1)); 
    	assertEquals(Direction.SOUTH.atomCW(new Posn(0, 0)), new Posn(1, 1)); 
    	assertEquals(Direction.NORTH.atomCW(new Posn(0, 0)), new Posn(-1, -1)); 
    	
    	assertEquals(Direction.NORTH.atomCW(new Posn(-999, 123)), new Posn(-1000, 122)); 
    	assertEquals(Direction.SOUTH.atomCW(new Posn(100, -5987)), new Posn(101, -5986));
    	assertEquals(Direction.EAST.atomCW(new Posn(89652, 15)), new Posn(89653, 14));
    }
    
    @Property
    public void testatomCWProperties(int x, int y) {
    	assertEquals(Direction.EAST.atomCW(new Posn(x, y)), new Posn(x + 1, y - 1)); 
    	assertEquals(Direction.WEST.atomCW(new Posn(x, y)), new Posn(x - 1, y + 1)); 
    	assertEquals(Direction.SOUTH.atomCW(new Posn(x, y)), new Posn(x + 1, y + 1)); 
    	assertEquals(Direction.NORTH.atomCW(new Posn(x, y)), new Posn(x - 1, y - 1));
    }
    
    @Test
    public void testatomCCW() {
    	assertEquals(Direction.EAST.atomCCW(new Posn(0, 0)), new Posn(1, 1));
    	assertEquals(Direction.WEST.atomCCW(new Posn(0, 0)), new Posn(-1, -1));
    	assertEquals(Direction.NORTH.atomCCW(new Posn(0, 0)), new Posn(1, -1));
    	assertEquals(Direction.SOUTH.atomCCW(new Posn(0, 0)), new Posn(-1, 1));
    }
    
    @Property
    public void testatomCCWProperties(int x, int y) {
    	assertEquals(Direction.EAST.atomCCW(new Posn(x, y)), new Posn(x + 1, y + 1)); 
    	assertEquals(Direction.WEST.atomCCW(new Posn(x, y)), new Posn(x - 1, y - 1)); 
    	assertEquals(Direction.SOUTH.atomCCW(new Posn(x, y)), new Posn(x - 1, y + 1)); 
    	assertEquals(Direction.NORTH.atomCCW(new Posn(x, y)), new Posn(x + 1, y - 1));
    }
    
    @Property
    public void testatomProperties(Direction d, @From(PosnGenerator.class) Posn p) {
    	assertEquals(d.atomCW(p), d.nextCounterClockwiseDirection().atomCCW(p)); 
    	assertEquals(d.atomCCW(p), d.nextClockwiseDirection().atomCW(p)); 
    }
    
    @Test
    public void testEquals() {
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.WEST)), false); 
    	assertEquals(new Hit(4, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), true); 
    	assertEquals(new Hit(5, new Posn(1, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), false); 
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.EAST).equals(new Hit(5, new Posn(0, 0), Direction.EAST)), true);
    	
    	assertEquals(new Exit(5, new Posn(0, 0), Direction.EAST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), false); 
    	assertEquals(new Exit(5, new Posn(0, 1), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), false); 
    	assertEquals(new Exit(3, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), true); 
    	assertEquals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1)) 
    				.equals(new Exit(5, new Posn(0, 0), Direction.WEST, new Posn(0, 0), new Posn(1, 1))), true); 
    }
    
    @Property
    public void testEqualsProperties(int bd, @From(PosnGenerator.class) Posn p, Direction d) {
    	assertEquals(new Hit(bd, p, d).equals(new Exit(bd, p, d, p)), true); 
    	assertEquals(new Hit(bd, p, d).equals(new Hit(bd, p, d)), true); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, p, d, p)), true); 
    	
    	assertEquals(new Hit(bd, p, d).equals(new Hit(bd, d.getNextPosn(p), d)), false); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, p, d, d.getNextPosn(p), d.getNextPosn(d.getNextPosn(p)))), true); 
    	assertEquals(new Exit(bd, p, d, p).equals(new Exit(bd, d.getNextPosn(p), d, p, p)), false); 
    }
    
    @Test
    public void testValidStartPosition() {
    	assertEquals(new Hit(1, new Posn(-1, 3), Direction.NORTH).validStartPosition(), false); 
    	assertEquals(new Hit(1, new Posn(-1, 3), Direction.EAST).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, -1), Direction.NORTH).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, -1), Direction.SOUTH).validStartPosition(), true);
    	assertEquals(new Hit(3, new Posn(3, -1), Direction.WEST).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(3, 3), Direction.NORTH).validStartPosition(), false);
    	assertEquals(new Hit(3, new Posn(2, 3), Direction.NORTH).validStartPosition(), true);
    }
    
    @Test 
    public void testOutOfBounds() {
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(3, -2)), true);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(5, 5)), true);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(3, 2)), false);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(0, 0)), false);
    	assertEquals(new Hit(5, new Posn(0, 0), Direction.SOUTH).outOfBounds(new Posn(4, -1)), true);
    }
    
    @Property
    public void testOutOfBoundsProperties(int bd, @From(PosnGenerator.class) Posn p, Direction d) {
    	assertEquals(new Hit(bd, p, d).outOfBounds(p), (p.getX() >= bd || p.getY() >= bd || p.getX() <= -1 || p.getY() <= -1)); 
    	
    	assertEquals(new Hit(bd, d.getNextPosn(p), d).outOfBounds(d.getNextPosn(p)), 
    				 new Hit(bd, d.atomCW(p), d).outOfBounds(d.atomCW(p))); 
    	assertEquals(new Hit(bd, d.getNextPosn(p), d).outOfBounds(d.getNextPosn(p)), 
				     new Hit(bd, d.atomCCW(p), d).outOfBounds(d.atomCCW(p))); 
    }
    
    @Test
    public void testBoardConsistency() throws ParserException {
    	AHint r = new Exit(3, new Posn(-1, 0), Direction.EAST, new Posn(-1, 0)); 
    	AHint e = new Exit(3, new Posn(-1, 1), Direction.EAST, new Posn(-1, 0));
    	assertEquals(BooleanToCNF.satSolve(new BBGame("blah", new AHint[] {r, e}).generateAllHints()).toString(), "FALSE");  
    	
    }

}
