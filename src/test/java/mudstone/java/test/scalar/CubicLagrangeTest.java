package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.testFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicLagrange;

//----------------------------------------------------------------
/** Test monomial form cubics. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/CubicLagrangeTest test > CubicLagrangeTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-04
 */

public final class CubicLagrangeTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final List<BiFunction> factories = 
      List.of(CubicLagrange::interpolateXY);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics, 
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),5.0e6,5.0e6,1.0e8); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final List<BiFunction> factories = 
      List.of(CubicLagrange::interpolateXY);
    for (final BiFunction factory : factories) {
      for (final Function f : testFns) {
        for (final double[] kn : knots) {
          general(f,factory,kn,kn,new double[0],
            expand(kn),1.0e0, 1.0e0, 1.0e0); } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------


