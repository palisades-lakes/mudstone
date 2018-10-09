package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.hermiteKnots;
import static mudstone.java.test.scalar.Common.hermiteTestPts;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.otherFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicHermite;

//----------------------------------------------------------------
/** Test 2 point cubic hermite interpolation. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/CubicHermiteTest test > CubicHermiteTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-06
 */

public final class CubicHermiteTest {

   @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Domain support = expand(hermiteTestPts);
    final List<BiFunction> factories = 
      List.of(CubicHermite::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics,
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[][] kn : hermiteKnots) {
          if (CubicHermite.validKnots(kn)) {
            //System.out.println(
            //  Arrays.toString(kn[0]) + 
            //  ", " + 
            //  Arrays.toString(kn[1]));
            exact(f,factory,kn,hermiteTestPts,support,
              4.0e5,2.0e9,3.0e9); } } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final Domain support = expand(hermiteTestPts);
    final List<BiFunction> factories = 
      List.of(CubicHermite::interpolate);
    final Iterable<Function> functions = Iterables.concat(otherFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[][] kn : hermiteKnots) {
          if (CubicHermite.validKnots(kn)) {
            general(f,factory,kn,support,
              1.0e0,1.0e0,1.0e0); } } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
