package mudstone.java.test.algebra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import mudstone.java.algebra.Magma;
import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.test.sets.SetTests;

//----------------------------------------------------------------
/** Common code for testing sets. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-07
 */

@SuppressWarnings("unchecked")
public final class MagmaTest {

  private static final int TRYS = 1000;
  
  private static final void isClosed (final Magma magma) {
    final Iterator it = 
      magma.sampler( 
      PRNG.well44497b(
        Seeds.seed(
          "seeds/Well44497b-2019-01-05.txt")));
    
    for (int i=0; i<TRYS; i++) {
      final Object a = it.next();
      final Object b = it.next();
      final Object c = magma.operation().apply(a,b);
      assertTrue(magma.contains(c)); } }

  private static final void magmaTests (final Magma magma) {
    SetTests.testMembership(magma);
    SetTests.testEquivalence(magma);
    isClosed(magma); }
  
  @SuppressWarnings({ "static-method" })
  @Test
  public final void bigFractions () {
    magmaTests(Magma.BIGFRACTIONS_ADD);
    magmaTests(Magma.BIGFRACTIONS_MULTIPLY); }
  
 //--------------------------------------------------------------
}
//--------------------------------------------------------------
