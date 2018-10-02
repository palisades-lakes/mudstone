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

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicLagrangeFactory;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test monomial form cubics. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/CubicLagrangeTest test > CubicLagrangeTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-01
 */

strictfp
public final class CubicLagrangeTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Iterable<ModelFactory> factories = 
      List.of(
        CubicLagrangeFactory.get());
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics
      , affineCubics
      , constantCubics
      , quadraticQuadratics
      , affineQuadratics
      , constantQuadratics);
    for (final ModelFactory factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          exact(f,factory,kn,expand(kn),2.0e2,2.0e2, 1.0e3); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final ModelFactory factory = CubicLagrangeFactory.get();
      for (final Function f : testFns) {
        for (final double[] kn : knots) {
          general(f,factory,kn,expand(kn),2.0e2,2.0e2, 2.0e7); } } } 
  //--------------------------------------------------------------
}
//--------------------------------------------------------------


