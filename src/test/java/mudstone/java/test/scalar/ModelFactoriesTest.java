package mudstone.java.test.scalar;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
 * @version 2018-09-22
 */

strictfp
public final class ModelFactoriesTest {

  private static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  //--------------------------------------------------------------
  /** any input function; any model function. */

  private static final void general (final Function f,
                                     final ModelFactory factory,
                                     final double[] xs,
                                     final double xulps,
                                     final double yulps,
                                     final double dulps) {
    Common.checkArgmin(f,1.0e0,1.0e0);
    //System.out.println(f);
    final Function g = factory.model(f,xs);
    Common.checkArgmin(g,4.0e0,xulps);
    //System.out.println(g);

    for (final double xi : factory.matchValueAt(xs)) {
      final double fi = f.doubleValue(xi);
      final double gi = g.doubleValue(xi);
      final double alpha = abs(fi)+abs(gi);
      final double delta = 
        yulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
      assertEquals(fi,gi,delta,
        () -> { 
          return abs(fi-gi) + ">" + delta + 
            " by " + abs(fi-gi)/delta + "\n"; }); } 

    for (final double xi : factory.matchSlopeAt(xs)) {
      final double dfi = f.slope(xi);
      final double dgi = g.slope(xi);
      final double beta = abs(dfi)+abs(dgi);
      final double epsilon = 
        dulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
      assertEquals(dfi,dgi,epsilon,
        () -> { return abs(dfi-dgi) + ">" + epsilon + 
          " by " + abs(dfi-dgi)/epsilon + "\n"; }); } } 

  //--------------------------------------------------------------
  /** for cases where model should reproduce test function
   * 'exactly' (up to floating point precision).
   */

  private static final void exact (final Function f,
                                   final ModelFactory factory,
                                   final double[] xs,
                                   final double xulps,
                                   final double yulps,
                                   final double dulps) {

    final double xf = f.doubleArgmin();
    final Function g = factory.model(f,xs);
    final double xg = g.doubleArgmin();
    final double gamma = abs(xf)+abs(xg);
    final double zeta = 
      xulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
    assertEquals(xf,xg,zeta,
      () -> { 
        return 
          "\n" + f +
          "\n" + g +
          "\nargmin: |" + xf + "-" + xg +"|=" +
          abs(xf-xg) + ">" + zeta + 
          " by " + abs(xf-xg)/zeta + "\n"; });
    final double x0 = xs[0];
    final double x1 = xs[1];
    final double x2 = xs[2];
    final double[] xx = 
      new double[] { x0, x1, x2,
                     0.5*(x0+x1), 0.5*(x1+x2), 0.5*(x2+x0),
                     (x0+x1), (x1+x2), (x2+x0), 
                     (x0+x1+x2)/3.0,
                     x0+x1+x2,
                     x0 + (x1-x0)*GOLDEN_RATIO,
                     x1 + (x2-x1)*GOLDEN_RATIO,
                     x2 + (x0-x2)*GOLDEN_RATIO,
                     f.doubleArgmin(),
                     g.doubleArgmin(), };
    for (final double xi : xx) {
      final double fi = f.doubleValue(xi);
      final double gi = g.doubleValue(xi);
      final double alpha = abs(fi)+abs(gi);
      final double delta = 
        yulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
      assertEquals(fi,gi,delta,
        () -> { 
          return 
            "\n" + f +
            "\n" + g +
            "\n" + abs(fi-gi) + ">" + delta + 
            "\n by " + abs(fi-gi)/delta + 
            "\n at " + xi + "\n"; });
      final double dfi = f.slope(xi);
      final double dgi = g.slope(xi);
      final double beta = abs(dfi)+abs(dgi);
      final double epsilon = 
        dulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
      assertEquals(dfi,dgi,epsilon,
        () -> { return 
          "\n" + f +
          "\n" + g +
          "\n" + abs(dfi-dgi) + ">" + epsilon + 
          " by " + abs(dfi-dgi)/epsilon + 
          "\n at " + xi + "\n"; });
    } } 

  //--------------------------------------------------------------

  private static final Iterable<Function> cubics = 
    List.of(
      QCubic.make(0.0,-1.0,0.0,1.0),
      QCubic.make(1.0,-1.0,1.0,0.0),
      QCubic.make(1.0,1.0,0.0,0.0),
      QCubic.make(1.0,0.0,0.0,0.0));

  // TODO: CubicHermite and QuadraticLagrange fail for affine/constant fns
  private static final Iterable<Function> quadratics = 
    List.of(
      QQuadratic.make(1.0,-1.0,1.0,0.0),
      QQuadratic.make(0.0,-1.0,1.0,0.0),
      QQuadratic.make(1.0,1.0,0.0,0.0),
      QQuadratic.make(1.0,0.0,0.0,0.0)
      );

  private static final Iterable<Function> functions = 
    Iterables.concat(cubics,quadratics); 

  private static final Iterable<ModelFactory> cubicFactories =
    List.of(InterpolateXYD2.make());

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
        general(f,factory,new double[]{-1.0,0.0,1.0},
          1.0e0,1.0e0,1.0e0);
        general(f,factory,new double[]{0.0,1.0,GOLDEN_RATIO},
          1.0e0,5.0e0,1.0e0);
        general(f,factory,new double[]{0.0,1.0,1.01},
          1.0e0,1.0e0,1.0e0);
        general(f,factory,new double[]{0.49,0.50,0.51},
          1.0e4,1.0e0,1.0e0); } } } 

  @SuppressWarnings({ "static-method" })
  @Test
  public final void quadraticTests () {

    for (final Function f : quadratics) {
      for (final ModelFactory factory : factories) {
        exact(f,factory,new double[]{-1.0,0.0,1.0},
          1.0e0,2.0e1,2.0e1);
        exact(f,factory,new double[]{0.0,1.0,GOLDEN_RATIO},
          1.0e0,5.0e0,5.0e0);
        exact(f,factory,new double[]{0.0,1.0,1.01},
          1.0e0,2.0e1,2.0e1);
        exact(f,factory,new double[]{0.49,0.50,0.51},
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
