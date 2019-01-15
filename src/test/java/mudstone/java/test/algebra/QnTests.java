package mudstone.java.test.algebra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import mudstone.java.algebra.OneSetTwoOperations;
import mudstone.java.algebra.TwoSetsTwoOperations;
import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.test.sets.SetTests;

//----------------------------------------------------------------
/** Testing rational vector spaces. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-14
 */

@SuppressWarnings("unchecked")
public final class QnTests {

  private static final int TRYS = 127;

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

  @SuppressWarnings({ "static-method" })
  @Test
  public final void bigFractions () {
    for (final int n : new int[] { 1, 3, 13, 127}) {
      final TwoSetsTwoOperations qn = 
        TwoSetsTwoOperations.getQn(n);
      linearspaceTests(qn); } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
