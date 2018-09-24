package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.InterpolateXY3;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test Lagrange form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticLagrangeTest test > QuadraticLagrangeTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-23
 */

strictfp
public final class QuadraticLagrangeTest {

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final ModelFactory factory = InterpolateXY3.get();
    final Iterable<Function> functions = Iterables.concat(
      quadraticQuadratics
      , affineQuadratics
      , constantQuadratics
      );
    for (final Function f : functions) {
      for (final double[] kn : knots) {
        exact(f,factory,kn,1.0e3,5.0e4,5.0e4); } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
