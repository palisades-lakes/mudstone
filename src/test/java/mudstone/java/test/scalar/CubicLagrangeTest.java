package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.cubicKnots;
import static mudstone.java.test.scalar.Common.cubicTestPts;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.testFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Domain;
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
 * @version 2018-10-06
 */

public final class CubicLagrangeTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Domain support = expand(cubicTestPts);
    final List<BiFunction> factories = 
      List.of(CubicLagrange::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics,
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
//        System.out.println(f);
        for (final double[][] kn : cubicKnots) {
          if (CubicLagrange.validKnots(kn)) {
//            System.out.println(
//              Arrays.toString(kn[0]) + 
//              ", " + 
//              Arrays.toString(kn[1]));
            exact(f,factory,kn,cubicTestPts,support,
              5.0e6,4.0e10,6.0e10); } } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final Domain support = expand(cubicTestPts);
    final List<BiFunction> factories = 
      List.of(CubicLagrange::interpolate);
    final Iterable<Function> functions = Iterables.concat(testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[][] kn : cubicKnots) {
          if (CubicLagrange.validKnots(kn)) {
            general(f,factory,kn,support,
              1.0e0,1.0e0,1.0e0); } } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------


