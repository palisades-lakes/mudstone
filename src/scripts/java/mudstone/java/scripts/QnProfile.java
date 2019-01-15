package mudstone.java.scripts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import mudstone.java.algebra.OneSetTwoOperations;
import mudstone.java.algebra.TwoSetsTwoOperations;
import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.test.algebra.AlgebraicStructureTests;
import mudstone.java.test.sets.SetTests;

//----------------------------------------------------------------
/** Profiling rational vector spaces. 
 *
 * jy ----source 11 src/scripts/java/mudstone/java/scripts/QnProfile.java
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-14
 */

@SuppressWarnings("unchecked")
public final class QnProfile {

  private static final int TRYS = 1023;

  private static final void 
  linearspaceTests (final TwoSetsTwoOperations space) {

    final OneSetTwoOperations scalars = 
      (OneSetTwoOperations) space.scalars();
    AlgebraicStructureTests.fieldTests(scalars);

    SetTests.tests(space);

    final Supplier sg = 
      space.scalars().generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-11.txt")));
    final Supplier vg = 
      space.generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-09.txt")));

    for(final Object law : space.linearspaceLaws()) {
      for (int i=0; i<TRYS; i++) {
        if (law instanceof Predicate) {
          assertTrue(((Predicate) law).test(vg)); }
        else if (law instanceof BiPredicate) {
          assertTrue(((BiPredicate) law).test(vg,sg));} } } }

  //--------------------------------------------------------------

  public static final void main (final String[] args) {
    for (final int n : new int[] { 1, 3, 13, 127, 1023}) {
      System.out.println(n);
      final TwoSetsTwoOperations qn = 
        TwoSetsTwoOperations.getQn(n);
      linearspaceTests(qn); } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
