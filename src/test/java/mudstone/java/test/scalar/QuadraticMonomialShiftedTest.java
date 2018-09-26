package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.InterpolateXD2XY1;
import mudstone.java.functions.scalar.InterpolateXY2XD1;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test monomial form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticMonomialShiftedTest test > QuadraticMonomialShiftedTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

strictfp
public final class QuadraticMonomialShiftedTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final Iterable<ModelFactory> factories = 
      List.of(
        InterpolateXY2XD1.get(),
        InterpolateXD2XY1.get());
    final Iterable<Function> functions = Iterables.concat(
      quadraticQuadratics
      , affineQuadratics
      , constantQuadratics
      );
    for (final ModelFactory factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          exact(f,factory,kn,1.0e7,5.0e8,2.0e7); } } } }
  //--------------------------------------------------------------
}
//--------------------------------------------------------------


