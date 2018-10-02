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

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.CubicMonomialFactory;
import mudstone.java.functions.scalar.InterpolateXD2XY1;
import mudstone.java.functions.scalar.InterpolateXY2XD1;
import mudstone.java.functions.scalar.InterpolateXY3;
import mudstone.java.functions.scalar.InterpolateXYD2;
import mudstone.java.functions.scalar.ModelFactory;

//----------------------------------------------------------------
/** <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/ModelFactoriesTest test > ModelFactoriesTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-01
 */

strictfp
public final class ModelFactoriesTest {

  //--------------------------------------------------------------

  private static final Iterable<ModelFactory> cubicFactories =
    List.of(InterpolateXYD2.get(),CubicMonomialFactory.get());

  private static final Iterable<ModelFactory> quadraticFactories =
    List.of(
      InterpolateXD2XY1.get(),
      InterpolateXY2XD1.get(),
      InterpolateXY3.get());

  private static final Iterable<ModelFactory> factories = 
    Iterables.concat(cubicFactories,quadraticFactories); 

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void quadraticTests () {
    final Iterable<Function> functions = Iterables.concat(
      quadraticCubics, affineCubics, constantCubics, 
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final ModelFactory factory : quadraticFactories) {
      //System.out.println(factory);
      for (final Function f : functions) {
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),5.0e2,5.0e4,5.0e4); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void cubicTests () {
    final Iterable<Function> functions = Iterables.concat(
      cubicCubics, quadraticCubics, affineCubics, constantCubics, 
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final ModelFactory factory : cubicFactories) {
      //System.out.println(factory);
      for (final Function f : functions) {
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),5.0e5,1.0e7,5.0e7); } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    for (final ModelFactory factory : factories) {
      //System.out.println(factory);
      for (final Function f : testFns) {
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          general(
            f,factory,kn,expand(kn),2.0e2,2.0e2, 2.0e7); } } } } 
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
