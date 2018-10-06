package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineKnots;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.affineTestPts;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.cubicCubics;
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
import mudstone.java.functions.scalar.AffineFunctional1d;

//----------------------------------------------------------------
/** Test affine interpolants. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/AffineFunctional1dTest test > AffineFunctional1dTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-06
 */

public final class AffineFunctional1dTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Domain support = expand(affineTestPts);
    final List<BiFunction> factories = 
      List.of(AffineFunctional1d::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      affineCubics, constantCubics, 
      affineQuadratics, constantQuadratics);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[][] kn : affineKnots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,affineTestPts,support,
            1.0e0,4.0e2,3.0e2); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final Domain support = expand(affineTestPts);
    final List<BiFunction> factories = 
      List.of(AffineFunctional1d::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, quadraticQuadratics, testFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[][] kn : affineKnots) {
          general(f,factory,kn,support,1.0e0,2.0e2,1.0e0); } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
