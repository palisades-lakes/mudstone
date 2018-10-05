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

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.QuadraticMonomialShifted;

//----------------------------------------------------------------
/** Test monomial form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticMonomialShiftedTest test > QuadraticMonomialShiftedTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-05
 */

public final class QuadraticMonomialShiftedTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final List<BiFunction> factories = 
      List.of(
        QuadraticMonomialShifted::interpolateXY2D1,
        QuadraticMonomialShifted::interpolateXD2Y1);
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics, affineCubics, constantCubics,
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),2.0e2,4.0e4,6.0e2); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTestsXD2Y1 () {
    final List<BiFunction> factories = 
      List.of(
        QuadraticMonomialShifted::interpolateXD2Y1);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          general(f,factory,
            kn,
            Arrays.copyOfRange(kn,2,3),
            Arrays.copyOf(kn,2),
            expand(kn),1.0e0,5.0e5,3.0e4); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTestsXY2D1 () {
    final List<BiFunction> factories = 
      List.of(
        QuadraticMonomialShifted::interpolateXY2D1);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics, 
      testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          general(f,factory,
            kn,
            Arrays.copyOf(kn,2),
            Arrays.copyOfRange(kn,2,3),
            expand(kn),1.0e0,6.0e6,2.0e5); } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------


