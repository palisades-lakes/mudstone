package mudstone.java.test.sets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.function.BiPredicate;

import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.sets.Set;

//----------------------------------------------------------------
/** Common code for testing sets. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-07
 */

public final class SetTests {

  private static final int TRYS = 1000;
  
  public static final void testMembership (final Set set) {
    final Iterator it = 
      set.sampler( 
      PRNG.well44497b(
        Seeds.seed(
          "seeds/Well44497b-2019-01-05.txt")));
    for (int i=0; i<TRYS; i++) {
      assertTrue(set.contains(it.next())); } }

  public static final void testEquivalence (final Set set) {
    final Iterator it = 
      set.sampler( 
      PRNG.well44497b(
        Seeds.seed(
          "seeds/Well44497b-2019-01-07.txt")));
    final BiPredicate equal = set.equivalence();
    // TODO: sampling the generates more equal but not eq pairs?
    for (int i=0; i<TRYS; i++) {
      final Object a = it.next();
      final Object b = it.next();
      assertTrue(equal.test(a,a));
      assertTrue(equal.test(b,b));
      assertTrue(equal.test(a,b) == equal.test(b,a)); } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
