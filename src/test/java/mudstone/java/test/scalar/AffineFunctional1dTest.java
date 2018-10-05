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
import mudstone.java.functions.scalar.AffineFunctional1d;

//----------------------------------------------------------------
/** Test Newton form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/AffineFunctional1dTest test > AffineFunctional1dTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-05
 */

public final class AffineFunctional1dTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final List<BiFunction> factories = 
      List.of(
        AffineFunctional1d::interpolateXY,
        AffineFunctional1d::interpolateXYD);
    final Iterable<Function> functions = Iterables.concat(
      affineCubics, constantCubics, 
      affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),1.0e0,1.0e0,1.0e0); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTestsXY () {
    final List<BiFunction> factories = 
      List.of(AffineFunctional1d::interpolateXY);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, quadraticQuadratics, testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          final double[] kn2 = Arrays.copyOf(kn,2);
          general(f,factory,kn2,kn2,new double[0],
            expand(kn),1.0e0,3.0e1,1.0e0); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTestsXYD () {
    final List<BiFunction> factories = 
      List.of(AffineFunctional1d::interpolateXYD);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, quadraticQuadratics, testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[] kn : knots) {
          final double[] kn1 = Arrays.copyOf(kn,1);
          general(f,factory,kn1,kn1,new double[0],
            expand(kn),1.0e0,1.0e0,1.0e0); } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
