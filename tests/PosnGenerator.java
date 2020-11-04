import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * 
 * 
 * Class used by quickcheck to generate random Posns
 */
public class PosnGenerator extends Generator<Posn> {
	public PosnGenerator() {
		super(Posn.class); 
	}

	@Override
	public Posn generate(SourceOfRandomness random, GenerationStatus status) {
		return new Posn(random.nextInt(), random.nextInt()); 
	}
}
