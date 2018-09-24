package mudstone.java.test.scalar;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.InterpolateXD2XY1;
import mudstone.java.functions.scalar.InterpolateXY2XD1;
import mudstone.java.functions.scalar.InterpolateXY3;
import mudstone.java.functions.scalar.InterpolateXYD2;
import mudstone.java.functions.scalar.ModelFactory;
import mudstone.java.test.functions.scalar.QCubic;
import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/ModelFactoriesTest test > ModelFactoriesTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-23
 */

strictfp
public final class ModelFactoriesTest {

   

  //--------------------------------------------------------------

  private static final Iterable<Function> cubics = 
    List.of(
      QCubic.make(0.0,-1.0,0.0,1.0),
      QCubic.make(1.0,-1.0,1.0,0.0),
      QCubic.make(1.0,1.0,0.0,0.0),
      QCubic.make(1.0,0.0,0.0,0.0));

  // TODO: CubicHermite and QuadraticLagrange fail for 
  // affine/constant functions
  private static final Iterable<Function> quadratics = 
    List.of(
      QQuadratic.make(1.0,-1.0,1.0),
      QQuadratic.make(0.0,-1.0,1.0),
      QQuadratic.make(1.0,1.0,0.0),
      QQuadratic.make(1.0,0.0,0.0)
      );

  private static final Iterable<Function> functions = 
    Iterables.concat(cubics,quadratics); 

  private static final Iterable<ModelFactory> cubicFactories =
    List.of(InterpolateXYD2.get());

  private static final Iterable<ModelFactory> quadraticFactories =
    List.of(
      InterpolateXD2XY1.get(),
      InterpolateXY2XD1.get(),
      InterpolateXY3.get());

  private static final Iterable<ModelFactory> factories = 
    Iterables.concat(cubicFactories,quadraticFactories); 

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {

    for (final Function f : functions) {
      for (final ModelFactory factory : factories) {
        Common.general(f,factory,new double[]{-1.0,0.0,1.0},
          1.0e0,1.0e0,1.0e0);
        Common.general(f,factory,new double[]{0.0,1.0,Common.GOLDEN_RATIO},
          1.0e0,5.0e0,1.0e0);
        Common.general(f,factory,new double[]{0.0,1.0,1.01},
          1.0e0,1.0e0,1.0e0);
        Common.general(f,factory,new double[]{0.49,0.50,0.51},
          1.0e4,1.0e0,1.0e4); } } } 

  @SuppressWarnings({ "static-method" })
  @Test
  public final void quadraticTests () {

    for (final Function f : quadratics) {
      for (final ModelFactory factory : factories) {
        Common.exact(f,factory,new double[]{-1.0,0.0,1.0},
          1.0e0,2.0e1,2.0e1);
        Common.exact(f,factory,new double[]{0.0,1.0,Common.GOLDEN_RATIO},
          1.0e0,5.0e0,5.0e0);
        Common.exact(f,factory,new double[]{0.0,1.0,1.01},
          1.0e0,1.0e2,5.0e2);
        Common.exact(f,factory,new double[]{0.49,0.50,0.51},
          2.0e0,5.0e4,1.0e6); } } } 

  //    @SuppressWarnings({ "static-method" })
  //    @Test
  //    public final void cubicTest () {
  //      for (final Function f : cubics) {
  //        for (final ModelFactory factory : cubicFactories) {
  //          exact(f,factory,new double[]{-1.0,0.0,1.0},
  //            1.0e0,1.0e0,1.0e0);
  //          exact(f,factory,new double[]{0.0,1.0,GOLDEN_RATIO},
  //            1.0e0,1.0e0,1.0e0);
  //          exact(f,factory,new double[]{0.0,1.0,1.01},
  //            1.0e0,1.0e0,1.0e0);
  //          exact(f,factory,new double[]{0.49,0.50,0.51},
  //            1.0e0,1.0e0,1.0e0); } } } 

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
