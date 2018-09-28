package mudstone.java.test.scalar;

import static java.lang.Double.isFinite;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.fma;
import static java.lang.StrictMath.min;
import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Doubles;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.Interval;
import mudstone.java.functions.scalar.ModelFactory;
import mudstone.java.test.functions.scalar.QCubic;
import mudstone.java.test.functions.scalar.QQuadratic;
import mudstone.java.test.functions.scalar.Square;

//----------------------------------------------------------------
/** Shared tests for scalar functions
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-28
 */

strictfp
public final class Common {

  public static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  public static final Iterable<double[]> knots =
    List.of(
      new double[] {-1.0,0.0,1.0,},
      new double[] {0.0,1.0,GOLDEN_RATIO,},
      new double[] {0.99,1.0,1.01,},
      new double[] {0.49,0.50,0.51,},

      new double[] {-1.0e2,0.0,1.0e2,},
      new double[] {0.0,1.0e2,GOLDEN_RATIO*1.0e2,}//,
      //new double[] {0.999e2,1.000e2,1.001e2,},
      //new double[] {0.499e2,0.500e2,0.501e2,} 
      );

  public static final Iterable<Function> cubicCubics = 
    List.of(
      QCubic.make(1.0,-1.0,1.0,-1.0),
      QCubic.make(1.0,-1.0,-1.0,1.0),
      QCubic.make(1.0,1.0,1.0,1.0),
      QCubic.make(1.0,-1.0,1.0,1.0));

  public static final Iterable<Function> quadraticCubics = 
    List.of(
      QCubic.make(1.0,1.0,-1.0,0.0),
      QCubic.make(1.0,-1.0,-1.0,0.0),
      QCubic.make(1.0,1.0,1.0,0.0),
      QCubic.make(1.0,-1.0,1.0,0.0));

  public static final Iterable<Function> affineCubics = 
    List.of(
      QCubic.make(1.0,1.0,0.0,0.0),
      QCubic.make(1.0,-1.0,0.0,0.0));

  public static final Iterable<Function> constantCubics = 
    List.of(QCubic.make(1.0,0.0,0.0,0.0));

  public static final Iterable<Function> quadraticQuadratics = 
    List.of(
      QQuadratic.make(1.0,1.0,-1.0),
      QQuadratic.make(1.0,-1.0,-1.0),
      QQuadratic.make(1.0,1.0,1.0),
      QQuadratic.make(1.0,-1.0,1.0),
      Square.get());

  public static final Iterable<Function> affineQuadratics = 
    List.of(
      QQuadratic.make(1.0,1.0,0.0),
      QQuadratic.make(1.0,-1.0,0.0));

  public static final Iterable<Function> constantQuadratics = 
    List.of(QQuadratic.make(1.0,0.0,0.0));

  public static final Iterable<Function> testFns =
    List.of(
//      Math832.get(),
//      Quintic.get(),
//    Runge.get(),
//      SemiCubic.get(),
//      Sin.get(),
//      Square.get()
      );

  //--------------------------------------------------------------
  /** Return a finite interval centered on the knots,
   * used to handle polynomial interpolants of affine functions
   * that end up with very small higher order coefficients.
   */
  
  public static final Interval expand (final double[] kn) {
    final double k0 = Doubles.min(kn);
    final double k1 = Doubles.max(kn);
    final double dk = k1-k0;
    final double a = 1.0e3;
    return Interval.closed(fma(-a,dk,k0),fma(a,dk,k1)); }
  
  //--------------------------------------------------------------
  /** Check that the value of <code>f</code> actually a local
   * minimum, that is, it increases if we move a small amount
   * (<code>step*sqrt(ulp(1+abs(xmin)))</code>)s away from 
   * <code>f.doubleArgmin(support)</code> within 
   * <code>support</code>.
   * Only require this if <code>f.doubleArgmin()</code> is finite.
   */

  public static final void checkArgmin (final Function f,
                                        final Domain support, 
                                        final double step, 
                                        final double dulps) {
    final double xmin = f.doubleArgmin(support);
    if (isFinite(xmin)) {
      final double kappa = dulps*ulp(1.0);
      //System.out.println(dulps + "*" + ulp(1.0) + "=" + kappa);
      assertEquals(0.0,f.slope(xmin),kappa,
        () -> { 
          return 
            "\n" + f + "\n" +
            "0.0 != " + f.slope(xmin) + "\n" +
            "by " + (abs(f.slope(xmin))/kappa) + " dulps\n" +
            "at " + xmin + "\n";  });

      final double ymin = f.doubleValue(xmin);
      
      // TODO: test for a strict local minimum?
      final double delta = step*sqrt(ulp(1.0+abs(xmin)));
      final double x0 = xmin-delta;
      if (support.contains(x0)) {
        assertTrue(
          ymin <= f.doubleValue(x0),
          () -> { 
            return 
              "\n" + f + "\n" +
              ymin + ">=" + f.doubleValue(xmin-delta) + "\n" +
              "by " + (ymin-f.doubleValue(xmin-delta)) + "\n" +
              "at " + xmin + " : " + (xmin-delta) + "\n" +
              "delta= " + delta + "\n";  }); }
      final double x1 = xmin+delta;
      if (support.contains(x1)) {
        assertTrue(
          ymin <= f.doubleValue(x1),
          () -> { 
            return 
              "\n" + f + "\n" +
              ymin + ">=" + f.doubleValue(xmin+delta) + "\n" +
              "by " + (ymin-f.doubleValue(xmin+delta)) + "\n" +
              " at " + xmin + " : " + (xmin+delta) + "\n" +
              "delta= " + delta + "\n";  }); } } }

  //--------------------------------------------------------------

  private static final void assertEqualArgmin (final Function f,
                                               final Function g,
                                               final Domain support,
                                               final double xulps) {
    final double xf = f.doubleArgmin(support);
    final double xg = g.doubleArgmin(support);
    final double gamma = abs(xf)+abs(xg);
    final double zeta = 
      xulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
    assertEquals(xf,xg,zeta,
      () -> { 
        return 
          "\nargmin: |" + xf + "-" + xg +"|=" +
          abs(xf-xg) + ">" + zeta + 
          " by " + abs(xf-xg)/zeta + " xulps" + "\n"; }); }

  //--------------------------------------------------------------

  private static final void assertEqualValue (final Function f,
                                              final Function g,
                                              final double x,
                                              final double yulps) {
    final double fx = f.doubleValue(x);
    final double gx = g.doubleValue(x);
    final double alpha = abs(fx)+abs(gx);
    final double delta = 
      yulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
    assertEquals(fx,gx,delta,
      () -> { 
        return 
          "\n" + f +
          "\n" + g +
          "\n" + abs(fx-gx) + ">" + delta + 
          "\n by " + abs(fx-gx)/delta + " yulps" +
          "\n at " + x + "\n"; }); }

  //--------------------------------------------------------------

  private static final void assertEqualSlope (final Function f,
                                              final Function g,
                                              final double x,
                                              final double dulps) {
    final double dfi = f.slope(x);
    final double dgi = g.slope(x);
    final double beta = abs(dfi)+abs(dgi);
    final double epsilon = 
      dulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
    assertEquals(dfi,dgi,epsilon,
      () -> { return 
        "\n" + f +
        "\n" + g +
        "\n" + abs(dfi-dgi) + ">" + epsilon + 
        "\n by " + abs(dfi-dgi)/epsilon  + " dulps" + 
        "\n at " + x + "\n"; }); }

  //--------------------------------------------------------------
  /** any input function; any model function. 
   * @param support TODO*/

  public static final Function general (final Function f,
                                        final ModelFactory factory,
                                        final double[] xs,
                                        final Domain support,
                                        final double xulps,
                                        final double yulps, 
                                        final double dulps) {
    //    System.out.println(f);
    checkArgmin(f,support,1.0e2*min(1.0e1,xulps), dulps);
    final Function g = factory.model(f,xs);
    //    System.out.println(g);
    checkArgmin(g,support,1.0e2*min(1.0e1,xulps), dulps);
    //    System.out.println(Arrays.toString(xs));
    for (final double xi : factory.matchValueAt(xs)) {
      assertEqualValue(f,g,xi,yulps); }
    for (final double xi : factory.matchSlopeAt(xs)) {
      assertEqualSlope(f,g,xi,dulps); } 
    return g; }

  //--------------------------------------------------------------
  /** for cases where model should reproduce test function
   * 'exactly' (up to floating point precision).
   * @param support TODO
   */

  public static final void exact (final Function f,
                                  final ModelFactory factory,
                                  final double[] xs,
                                  final Domain support,
                                  final double xulps,
                                  final double yulps, 
                                  final double dulps) {
    final Function g = 
      general(f,factory,xs,support,xulps,yulps, dulps);
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
                     f.doubleArgmin(support),
                     g.doubleArgmin(support), };
    assertEqualArgmin(f,g,support,xulps);
    for (final double xi : xx) {
      assertEqualValue(f,g,xi,yulps);
      assertEqualSlope(f,g,xi,dulps); } }

  //--------------------------------------------------------------
  // utility class; disable constructor
  //--------------------------------------------------------------

  private Common () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
