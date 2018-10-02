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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicNewtonFactory;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test monomial form cubics. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/CubicNewtonTest test > CubicNewtonTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-01
 */

strictfp
public final class CubicNewtonTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Iterable<ModelFactory> factories = 
      List.of(
        CubicNewtonFactory.get());
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics
      , affineCubics
      , constantCubics
      , quadraticQuadratics
      , affineQuadratics
      , constantQuadratics);
    for (final ModelFactory factory : factories) {
      for (final Function f : functions) {
        System.out.println();
        System.out.println(f);
        for (final double[] kn : knots) {
          System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),1.0e0,2.0e4,2.0e4); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final ModelFactory factory = CubicNewtonFactory.get();
      for (final Function f : testFns) {
        for (final double[] kn : knots) {
          general(f,factory,kn,expand(kn),1.0e0,2.0e0,1.0e0); } } } 
  //--------------------------------------------------------------
}
//--------------------------------------------------------------


