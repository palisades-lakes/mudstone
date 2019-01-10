package mudstone.java.test.sets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.sets.Set;
import mudstone.java.sets.Sets;

//----------------------------------------------------------------
/** Common code for testing sets. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-09
 */

@SuppressWarnings("unchecked")
public final class SetTests {

  private static final int TRYS = 1000;
  
  private static final void testMembership (final Set set) {
    final Iterator it = 
      set.sampler( 
      PRNG.well44497b(
        Seeds.seed("seeds/Well44497b-2019-01-05.txt")));
    for (int i=0; i<TRYS; i++) {
      assertTrue(set.contains(it.next())); } }

  private static final void testEquivalence (final Set set) {
    final Iterator it = 
      set.sampler( 
      PRNG.well44497b(
        Seeds.seed("seeds/Well44497b-2019-01-07.txt")));
    for (int i=0; i<TRYS; i++) {
      assertTrue(Sets.isReflexive(set,it));
      assertTrue(Sets.isSymmetric(set,it)); } }

  public static final void tests (final Set set) {
    testMembership(set);
    testEquivalence(set); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
