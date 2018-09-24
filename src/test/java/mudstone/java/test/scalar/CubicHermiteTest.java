package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.knots;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.InterpolateXYD2;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test 2 point cubic hermite interpolation. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/CubicHermiteTest test > CubicHermiteTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-23
 */

strictfp
public final class CubicHermiteTest {

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final ModelFactory factory = InterpolateXYD2.get();
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics
      //      , quadraticCubics
      //      , affineCubics
      //      , constantCubics
      );
    for (final Function f : functions) {
      for (final double[] kn : knots) {
        exact(f,factory,kn,2.0e5,5.0e5,5.0e6); } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
