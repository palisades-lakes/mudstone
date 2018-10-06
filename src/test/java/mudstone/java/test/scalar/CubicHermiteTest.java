package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;
import static mudstone.java.test.scalar.Common.oldKnots;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.testFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

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
 * @version 2018-10-04
 */

public final class CubicHermiteTest {

  //--------------------------------------------------------------


  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final List<BiFunction> factories = 
      List.of(CubicHermite::interpolateXYD);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics, 
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[] kn : oldKnots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),4.0e5,4.0e6,4.0e7); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final List<BiFunction> factories = 
      List.of(CubicHermite::interpolateXYD);
    for (final BiFunction factory : factories) {
      for (final Function f : testFns) {
        for (final double[] kn : oldKnots) {
          general(f,factory,kn,kn,new double[0],
            expand(kn),1.0e0,1.0e0,1.0e0); } } } }


  //--------------------------------------------------------------
}
//--------------------------------------------------------------
