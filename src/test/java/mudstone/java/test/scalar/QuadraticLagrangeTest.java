package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ModelFactory;
import mudstone.java.functions.scalar.QuadraticLagrangeFactory;

//----------------------------------------------------------------
/** Test Lagrange form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticLagrangeTest test > QuadraticLagrangeTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-01
 */

strictfp
public final class QuadraticLagrangeTest {

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final ModelFactory factory = QuadraticLagrangeFactory.get();
    final Iterable<Function> functions = Iterables.concat(
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final Function f : functions) {
      //System.out.println();
      //System.out.println(f);
      for (final double[] kn : knots) {
        //System.out.println(Arrays.toString(kn));
        exact(f,factory,kn,expand(kn),5.0e2,5.0e4,5.0e4); } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
