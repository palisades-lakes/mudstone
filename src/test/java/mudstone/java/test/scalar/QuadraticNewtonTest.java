package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticKnots;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.quadraticTestPts;
import static mudstone.java.test.scalar.Common.testFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.QuadraticNewton;

//----------------------------------------------------------------
/** Test Newton form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticNewtonTest test > QuadraticNewtonTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-06
 */

public final class QuadraticNewtonTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Domain support = expand(quadraticTestPts);
    final List<BiFunction> factories = 
      List.of(QuadraticNewton::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics, affineCubics, constantCubics,
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[][] kn : quadraticKnots) {
          if (QuadraticNewton.supportedKnots(kn)) {
//            System.out.println(
//              Arrays.toString(kn[0]) + 
//              ", " + 
//              Arrays.toString(kn[1]));
             exact(f,factory,kn,quadraticTestPts,support,
              6.0e5,2.0e7,3.0e7); } } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final Domain support = expand(quadraticTestPts);
    final List<BiFunction> factories = 
      List.of(QuadraticNewton::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[][] kn : quadraticKnots) {
          if (QuadraticNewton.supportedKnots(kn)) {
            general(f,factory,kn,support,
              1.0e0,1.0e0,2.0e0); } } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
