package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.testFns;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ModelFactory;
import mudstone.java.functions.scalar.QuadraticNewtonFactory;

//----------------------------------------------------------------
/** Test Newton form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticNewtonTest test > QuadraticNewtonTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-02
 */

strictfp
public final class QuadraticNewtonTest {

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final ModelFactory factory = QuadraticNewtonFactory.get();
    final Iterable<Function> functions = Iterables.concat(
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final Function f : functions) {
      for (final double[] kn : knots) {
        exact(f,factory,kn,expand(kn),1.0e3,5.0e3, 2.0e4); } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final ModelFactory factory = QuadraticNewtonFactory.get();
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics, affineCubics, constantCubics, testFns);
    for (final Function f : functions) {
      for (final double[] kn : knots) {
        general(f,factory,kn,expand(kn),5.0e2,5.0e3, 2.0e4); } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
