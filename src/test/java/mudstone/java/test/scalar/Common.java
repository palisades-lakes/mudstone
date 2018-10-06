package mudstone.java.test.scalar;

import static java.lang.Double.isFinite;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.fma;
import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Doubles;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.Interval;
import mudstone.java.test.functions.scalar.QCubic;
import mudstone.java.test.functions.scalar.QQuadratic;
import mudstone.java.test.functions.scalar.Square;

//----------------------------------------------------------------
/** Shared tests for scalar functions.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-06
 */

@SuppressWarnings("unchecked")
strictfp
public final class Common {

  public static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  private static final double[] 
    testPts (final List<double[][]> knots) {
    return knots
      .stream()
      .flatMapToDouble((final double[][] xi) -> { 
        return DoubleStream.concat(
          Arrays.stream(xi[0]),
          Arrays.stream(xi[1])); })
      .toArray(); }

  //--------------------------------------------------------------

  public static final List<double[][]> constantKnots =
    List.of(
      new double[][] {{-1.0e2},{}},
      new double[][] {{-1.0},{}},
      new double[][] {{-0.5},{}},
      new double[][] {{0.0},{}},
      new double[][] {{0.5},{}},
      new double[][] {{1.0},{}},
      new double[][] {{GOLDEN_RATIO},{}},
      new double[][] {{2.0},{}},
      new double[][] {{GOLDEN_RATIO*GOLDEN_RATIO},{}},
      new double[][] {{1.0e2},{}},
      new double[][] {{1.0e3},{}});

  public static final double[] constantTestPts =
    testPts(constantKnots);

  public static final List<double[][]> affineKnots =
    List.of(
      new double[][] {{0.999,1.000},{}},
      new double[][] {{0.999},{1.000}},
      new double[][] {{-1.0e2},{-1.0e2}},
      new double[][] {{0.0},{-1.0e2}},
      new double[][] {{0.0},{0.0}},
      new double[][] {{0.0},{1.0e2}},
      new double[][] {{1.0},{1.0}},
      new double[][] {{-1.0e2},{1.0e2}},
      new double[][] {{1.0e2},{-1.0e2}});

  public static final double[] affineTestPts =
    testPts(affineKnots);

  // Note: quadratic interpolation has an affine/quadratic
  // singularity when slope point is half way between value points.
  public static final List<double[][]> quadraticKnots =
    List.of(
      new double[][] {{-1.0e2,0.0,1.0e2},{}},
      new double[][] {{0.999,1.000,1.001},{}},
      new double[][] {{-1.0e2,0.99e2,1.0e2},{}},
      new double[][] {{0.999,1.000,},{1.001}},
      new double[][] {{0.998,1.001},{1.000}},
      new double[][] {{1.0e2,0.99e2},{1.0e2}},
      new double[][] {{-1.0e2,0.0},{1.0e2}},
      new double[][] {{-1.00e2,0.99e2},{0.0}},
      new double[][] {{-1.0e2,0.0},{0.0}},
      new double[][] {{-1.0e2,0.99e2},{1.0e2}},
      new double[][] {{-1.0e2,1.0e2},{0.99e2}},
      new double[][] {{-1.0e2,0.99e2},{0.99e2}},
      new double[][] {{0.999,},{1.000,1.001}},
      new double[][] {{-1.0e2},{0.0,-1.0e2}},
      new double[][] {{-1.0e2},{0.99e2,1.0e2}});

  public static final double[] quadraticTestPts =
    testPts(quadraticKnots);

  public static final Iterable<double[]> oldKnots =
    List.of(
      new double[] {-1.0,0.0,1.0,2.0},
      new double[] {0.0,1.0,GOLDEN_RATIO,GOLDEN_RATIO*GOLDEN_RATIO,},
      new double[] {0.99,1.0,1.01,1.02},
      new double[] {0.49,0.50,0.51,0.52},

      new double[] {-1.0e2,0.0,1.0e2,2.0e2,},
      new double[] {0.0,1.0e2,GOLDEN_RATIO*1.0e2,
                    GOLDEN_RATIO*GOLDEN_RATIO*1.0e2,}//,
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
      );

  //--------------------------------------------------------------
  /** Return a finite interval centered on the knots,
   * used to handle polynomial interpolants of affine functions
   * that end up with very small higher order coefficients.
   */

  public static final Interval expand (final double[] kn) {
    final double k0 = Doubles.min(kn);
    final double k1 = Doubles.max(kn);
    final double dk = max(1.0,k1-k0);
    final double a = 1.0e3;
    return Interval.closed(fma(-a,dk,k0),fma(a,dk,k1)); }

  public static final Interval expand (final double[][] kn) {
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

  public static final void assertLocalMin (final Function f,
                                           final double x,
                                           final Domain support, 
                                           final double step, 
                                           final double dulps) {
    if (isFinite(x)) {
      final double kappa = dulps*ulp(1.0);
      //System.out.println(dulps + "*" + ulp(1.0) + "=" + kappa);
      assertEquals(0.0,f.slope(x),kappa,
        () -> { 
          return 
            "\n" + f + "\n" +
            "0.0 != " + f.slope(x) + "\n" +
            "by " + (abs(f.slope(x))/kappa) + " dulps\n" +
            "at " + x + "\n";  });

      final double ymin = f.doubleValue(x);

      // TODO: test for a strict local minimum?
      final double delta = step*sqrt(ulp(1.0+abs(x)));
      final double x0 = x-delta;
      if (support.contains(x0)) {
        assertTrue(
          ymin <= f.doubleValue(x0),
          () -> { 
            return 
              "\n" + f + "\n" +
              ymin + ">=" + f.doubleValue(x-delta) + "\n" +
              "by " + (ymin-f.doubleValue(x-delta)) + "\n" +
              "at " + x + " : " + (x-delta) + "\n" +
              "delta= " + delta + "\n";  }); }
      final double x1 = x+delta;
      if (support.contains(x1)) {
        assertTrue(
          ymin <= f.doubleValue(x1),
          () -> { 
            return 
              "\n" + f + "\n" +
              ymin + ">=" + f.doubleValue(x+delta) + "\n" +
              "by " + (ymin-f.doubleValue(x+delta)) + "\n" +
              " at " + x + " : " + (x+delta) + "\n" +
              "delta= " + delta + "\n";  }); } } 
    //System.out.println("\n" + f + 
    //  "\nat " + x + "\nslope= " + f.slope(x)); 
  }

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
      assertLocalMin(f,xmin,support,step,dulps); } }

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
          "\n" + f +
          "\n" + g +
          "\nargmin: |" + xf + "-" + xg +"|=" +
          abs(xf-xg) + ">" + zeta + 
          "\n by " + abs(xf-xg)/zeta + " xulps" + "\n"; }); }

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
        "\nslope: " + dfi +
        "\n" + g +
        "\nslope: " + dgi +
        "\n" + abs(dfi-dgi) + ">" + epsilon + 
        "\n by " + abs(dfi-dgi)/epsilon  + " dulps" + 
        "\n at " + x + "\n"; }); }

  //--------------------------------------------------------------
  /** any input function; any model function. 
   * */

  public static final Function general (final Function f,
                                        final BiFunction factory,
                                        final double[] xs,
                                        final double[] matchY,
                                        final double[] matchD,
                                        final Domain support, 
                                        final double xulps, 
                                        final double yulps, 
                                        final double dulps) {
    //    System.out.println(f);
    checkArgmin(f,support,5.0e2*min(1.0e1,xulps),dulps);
    final Function g = (Function) factory.apply(f,xs);
    //    System.out.println(g);
    checkArgmin(g,support,5.0e2*min(1.0e1,xulps),dulps);
    //    System.out.println(Arrays.toString(xs));
    for (final double xi : matchY) {
      assertEqualValue(f,g,xi,yulps); }
    for (final double xi : matchD) {
      assertEqualSlope(f,g,xi,dulps); } 
    return g; }

  //--------------------------------------------------------------
  /** for cases where model should reproduce test function
   * 'exactly' (up to floating point precision).
   * @param support TODO
   */

  public static final void exact (final Function f,
                                  final BiFunction factory,
                                  final double[] xs,
                                  final Domain support,
                                  final double xulps,
                                  final double yulps, 
                                  final double dulps) {
    final Function g = 
      general(f,factory,xs,xs,xs,support, xulps, yulps, dulps);
    assertLocalMin(
      g,f.doubleArgmin(support),support,
      5.0e2*min(1.0e1,xulps),dulps);
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
  //--------------------------------------------------------------
  /** any input function; any model function. 
   * */

  public static final Function general (final Function f,
                                        final BiFunction factory,
                                        final double[][] knots,
                                        final Domain support, 
                                        final double xulps, 
                                        final double yulps, 
                                        final double dulps) {
    //    System.out.println(f);
    checkArgmin(f,support,5.0e2*min(1.0e1,xulps),dulps);
    final Function g = (Function) factory.apply(f,knots);
    //    System.out.println(g);
    checkArgmin(g,support,5.0e2*min(1.0e1,xulps),dulps);
    //    System.out.println(Arrays.toString(knots));
    for (final double xi : knots[0]) {
      assertEqualValue(f,g,xi,yulps); }
    for (final double xi : knots[1]) {
      assertEqualSlope(f,g,xi,dulps); } 
    return g; }

  //--------------------------------------------------------------
  /** for cases where model should reproduce test function
   * 'exactly' (up to floating point precision).
   * @param support TODO
   */

  public static final void exact (final Function f,
                                  final BiFunction factory,
                                  final double[][] knots,
                                  final double[] testPts,
                                  final Domain support,
                                  final double xulps,
                                  final double yulps, 
                                  final double dulps) {
    final Function g = 
      general(f,factory,knots,support, xulps, yulps, dulps);
    assertLocalMin(
      g,f.doubleArgmin(support),support,
      5.0e2*min(1.0e1,xulps),dulps);
    assertEqualArgmin(f,g,support,xulps);
    for (final double xi : testPts) {
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
