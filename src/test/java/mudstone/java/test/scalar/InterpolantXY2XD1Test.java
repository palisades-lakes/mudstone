package mudstone.java.test.scalar;

import static java.lang.Double.*;
import static java.lang.StrictMath.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.InterpolantXY2XD1;
import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test 'golden' quadratic interpolation. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/InterpolantXY2XD1Test test > InterpolantXY2XD1.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-21
 */

strictfp
public final class InterpolantXY2XD1Test {

  private static final double EXPAND = 0.5*(sqrt(5.0)+1.0);
  private static final double CONTRACT0 = 0.5*(sqrt(5.0)-1.0);
  private static final double CONTRACT1 = 1.0-CONTRACT0;

  //--------------------------------------------------------------
  // TODO: move to Common, with interpolating method as arg

  private static final void quadratic (final double a0,
                                       final double a1,
                                       final double a2,
                                       final double x0,
                                       final double x1,
                                       final double x2,
                                       final double ulps) {
    final QQuadratic f = QQuadratic.make(a0,a1,a2,0.0);
    //System.out.println(f);
    final InterpolantXY2XD1 g = InterpolantXY2XD1.make(f,x0,x1,x2);
    //System.out.println(g);

    final double xf = f.doubleArgmin();
    final double xg = g.doubleArgmin();
    final double gamma = abs(xf)+abs(xg);
    final double zeta = 
      ulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
    assertEquals(xf,xg,zeta,
      () -> { 
        return 
          "\nargmin: |" + xf + "-" + xg +"|=" +
          abs(xf-xg) + ">" + zeta + 
          " by " + abs(xf-xg)/zeta + "\n"; });
    Common.checkArgmin(f,1.0e0,1.0e0);
    Common.checkArgmin(g,4.0e0,2.0e0);

    final double[] xx = 
      new double[] { x0, x1, x2,
                     0.5*(x0+x1), 0.5*(x1+x2), 0.5*(x2+x0),
                     (x0+x1), (x1+x2), (x2+x0), 
                     (x0+x1+x2)/3.0,
                     x0+x1+x2,
                     x0 + (x1-x0)*EXPAND,
                     x1 + (x2-x1)*EXPAND,
                     x2 + (x0-x2)*EXPAND,
                     f.doubleArgmin(),
                     g.doubleArgmin(), };
    //    for (final double xi : xx) {
    //      System.out.println(xi);
    //      System.out.println(
    //        f.doubleValue(xi) + " = " + g.doubleValue(xi));
    //      System.out.println(f.slope(xi) + " = " + g.slope(xi)); }
    for (final double xi : xx) {
      final double fi = f.doubleValue(xi);
      final double gi = g.doubleValue(xi);
      final double alpha = abs(fi)+abs(gi);
      final double delta = 
        ulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
      assertEquals(fi,gi,delta,
        () -> { 
          return abs(fi-gi) + ">" + delta + 
            " by " + abs(fi-gi)/delta + "\n" +
            " at " + xi + "\n"; });
      final double dfi = f.slope(xi);
      final double dgi = g.slope(xi);
      final double beta = abs(dfi)+abs(dgi);
      final double epsilon = 
        ulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
      assertEquals(dfi,dgi,epsilon,
        () -> { return abs(dfi-dgi) + ">" + epsilon + 
          " by " + abs(dfi-dgi)/epsilon + "\n"; });
    } } 

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q111 () {
    quadratic(1.0,-1.0,1.0,-1.0,0.0,1.0,1.0e0);
    quadratic(1.0,-1.0,1.0,0.0,1.0,0.0,1.0e0);
    quadratic(1.0,-1.0,1.0,0.0,1.0,1.0,1.0e0);
    quadratic(1.0,-1.0,1.0,0.0,1.0,EXPAND,1.0e1);
    quadratic(1.0,-1.0,1.0,0.0,1.0,1.01,5.0e1); 
    quadratic(1.0,-1.0,1.0,0.49,0.50,0.51,2.0e3); }

  //--------------------------------------------------------------
  // check that interpolating function matches a golden expansion 
  // step 

  private static final double expand (final Function f,
                                      final double x0, 
                                      final double x1) {
    final double y0 = f.doubleValue(x0);
    final double y1 = f.doubleValue(x1);
    if (y0<y1) {
      return fma(EXPAND,x0-x1,x0); }
    return fma(EXPAND,x1-x0,x1); }

  private static final void expansion (final Function f,
                                       final double x0,
                                       final double x1,
                                       final double ulps) {
    final double x2 = expand(f,x0,x1);
    final InterpolantXY2XD1 g = 
      InterpolantXY2XD1.make(
        x0,f.doubleValue(x0),
        x1,f.doubleValue(x1),
        x2,0.0);
    //    System.out.println(x0 + ", " + g.doubleValue(x0));
    //    System.out.println(x1 + ", " + g.doubleValue(x1));
    //    System.out.println(x2 + ", " + g.doubleValue(x2));
    //    System.out.println(g);

    final double xmin = g.doubleArgmin();
    // interpolant might be constant function if y0==y1.
    if ((f.doubleValue(x0) == f.doubleValue(x1))
      && (0 > (x2-x0)*(x1-x2))
      && isFinite(xmin)) {
      final double gamma = abs(x2)+abs(xmin);
      final double zeta = 
        ulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
      assertEquals(x2,xmin,zeta,
        () -> { 
          return 
            "\nargmin: |" + x2 + "-" + xmin +"|=" +
            abs(x2-xmin) + ">" + zeta + 
            " by " + abs(x2-xmin)/zeta + "\n"; });
      Common.checkArgmin(g,x2, 1.0e0);
      Common.checkArgmin(g,xmin, 1.0e0); }

    final double[] xx = 
      new double[] { x0, x1 };
    for (final double xi : xx) {
      final double fi = f.doubleValue(xi);
      final double gi = g.doubleValue(xi);
      final double alpha = abs(fi)+abs(gi);
      final double delta = 
        ulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
      assertEquals(fi,gi,delta,
        () -> { 
          return abs(fi-gi) + ">" + delta + 
            " by " + abs(fi-gi)/delta + "\n" +
            " at " + xi + "\n"; });
    } } 

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void expandTest () {
    final QQuadratic f = QQuadratic.make(1.0,-1.0,1.0,0.0);

    expansion(f,-1.0,0.0,1.0e0);
    expansion(f,0.0,1.0,1.0e0);
    expansion(f,0.0,EXPAND,1.0e0);
    expansion(f,1.0,EXPAND,1.0e1);
    expansion(f,1.0,1.01,5.0e1); 
    expansion(f,0.49,0.50,2.0e3); 
  }

  //--------------------------------------------------------------
  // check that interpolating function matches a golden section 
  // step 

  private static final double contract (final Function f,
                                        final double x0, 
                                        final double x1) {
    final double y0 = f.doubleValue(x0);
    final double y1 = f.doubleValue(x1);
    if (y0<y1) {
      return fma(CONTRACT0,x0,CONTRACT1*x1); }
    return fma(CONTRACT0,x1,CONTRACT1*x0); }

  private static final void contraction (final Function f,
                                         final double x0,
                                         final double x1,
                                         final double ulps) {
    final double x2 = contract(f,x0,x1);
    final InterpolantXY2XD1 g = 
      InterpolantXY2XD1.make(
        x0,f.doubleValue(x0),
        x1,f.doubleValue(x1),
        x2,0.0);
    //    System.out.println(x0 + ", " + g.doubleValue(x0));
    //    System.out.println(x1 + ", " + g.doubleValue(x1));
    //    System.out.println(x2 + ", " + g.doubleValue(x2));
    //    System.out.println(g);

    final double xmin = g.doubleArgmin();

    // g might be constant if y0 = y1, so no xmin
    if (isFinite(xmin) && (f.doubleValue(x0)==f.doubleValue(x1))) {
      final double gamma = abs(x2)+abs(xmin);
      final double zeta = 
        ulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
      assertEquals(x2,xmin,zeta,
        () -> { 
          return 
            "\nargmin: |" + x2 + "-" + xmin +"|=" +
            abs(x2-xmin) + ">" + zeta + 
            " by " + abs(x2-xmin)/zeta + "\n"; });
      Common.checkArgmin(g,x2, 1.0e0);
      Common.checkArgmin(g,xmin, 1.0e0); } 

    final double[] xx = new double[] { x0, x1 };
    for (final double xi : xx) {
      final double fi = f.doubleValue(xi);
      final double gi = g.doubleValue(xi);
      final double alpha = abs(fi)+abs(gi);
      final double delta = 
        ulps*ulp(1.0+(Double.isNaN(alpha) ? 0.0 : alpha));
      assertEquals(fi,gi,delta,
        () -> { 
          return abs(fi-gi) + ">" + delta + 
            " by " + abs(fi-gi)/delta + "\n" +
            " at " + xi + "\n"; });
    } } 

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void contractTest () {
    final QQuadratic f = QQuadratic.make(1.0,-1.0,1.0,0.0);
    contraction(f,-1.0,0.0,1.0e0);
    contraction(f,0.0,1.0,1.0e0);
    contraction(f,0.0,EXPAND,1.0e0);
    contraction(f,1.0,EXPAND,1.0e1);
    contraction(f,1.0,1.01,5.0e1); 
    contraction(f,0.49,0.50,2.0e3); 
  }

  //--------------------------------------------------------------
}
