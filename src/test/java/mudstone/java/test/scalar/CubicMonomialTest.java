package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineCubics;
import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantCubics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticCubics;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;
import static mudstone.java.test.scalar.Common.testFns;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicMonomialFactory;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** Test monomial form cubics. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/CubicMonomialTest test > CubicMonomialTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

strictfp
public final class CubicMonomialTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Iterable<ModelFactory> factories = 
      List.of(
        CubicMonomialFactory.get());
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
          exact(f,factory,kn,2.0e2,2.0e2,1.0e3); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final ModelFactory factory = CubicMonomialFactory.get();
      for (final Function f : testFns) {
        for (final double[] kn : knots) {
          exact(f,factory,kn,2.0e2,2.0e2,2.0e7); } } } 
  //--------------------------------------------------------------
}
//--------------------------------------------------------------


