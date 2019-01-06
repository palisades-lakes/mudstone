package mudstone.java.test.sets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.sets.BigFractions;
import mudstone.java.sets.Set;

//----------------------------------------------------------------
/** Test <code>BigFractions</code> set. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/sets/BigFractionsTest test > BigFractionsTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-05
 */

public final class BigFractionsTest {

  private static final int NTRYS = 1000;
  
  @SuppressWarnings({ "static-method" })
  @Test
  public final void membership () {
    final Set set = BigFractions.get();
    final Iterator it = 
      set.sampler( 
      PRNG.well44497b(
        Seeds.seed(
          "seeds/Well44497b-2019-01-05.txt")));
    
    for (int i=0; i<NTRYS; i++) {
      assertTrue(set.contains(it.next())); }
    }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
