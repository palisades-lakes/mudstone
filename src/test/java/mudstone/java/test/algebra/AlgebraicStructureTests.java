package mudstone.java.test.algebra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.function.BiPredicate;

import org.junit.jupiter.api.Test;

import mudstone.java.algebra.GroupLike;
import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.test.sets.SetTests;

//----------------------------------------------------------------
/** Common code for testing sets. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-09
 */

@SuppressWarnings("unchecked")
public final class AlgebraicStructureTests {

  private static final int TRYS = 1000;

  private static final void magmaTests (final GroupLike magma) {
    SetTests.tests(magma);
    final Iterator it = 
      magma.sampler( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-09.txt")));

    for(final BiPredicate law : GroupLike.MAGMA_LAWS) {
      for (int i=0; i<TRYS; i++) {
        assertTrue(law.test(magma,it)); } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void bigFractions () {
    magmaTests(GroupLike.BIGFRACTIONS_ADD);
    magmaTests(GroupLike.BIGFRACTIONS_MULTIPLY); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
