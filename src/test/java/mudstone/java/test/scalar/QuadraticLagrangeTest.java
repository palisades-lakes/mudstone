package mudstone.java.test.scalar;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.functions.scalar.QuadraticLagrange;
import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test Lagrange form parabolas. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/QuadraticLagrangeTest test > QuadraticLagrangeTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-22
 */

strictfp
public final class QuadraticLagrangeTest {

  private static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  public static final void qtests (final double a0,
                                   final double a1,
                                   final double a2,
                                   final double x0,
                                   final double x1,
                                   final double x2,
                                   final double xulps,
                                   final double yulps,
                                   final double dulps) {
    final QQuadratic f = QQuadratic.make(a0,a1,a2,0.0);
    System.out.println(f);
    Common.checkArgmin(f,1.0e0,dulps);
    final QuadraticLagrange g = QuadraticLagrange.make(f,x0,x1,x2);
    System.out.println(g);
    Common.checkArgmin(g,5.0e1,dulps);

    final double xf = f.doubleArgmin();
    final double xg = g.doubleArgmin();
    final double gamma = abs(xf)+abs(xg);
    final double zeta = 
      xulps*ulp(1.0+(Double.isNaN(gamma) ? 0.0 : gamma));
    assertEquals(xf,xg,zeta,
      () -> { 
        return 
          "\nargmin: |" + xf + "-" + xg +"|=" +
          abs(xf-xg) + ">" + zeta + 
          " by " + abs(xf-xg)/zeta + "\n"; });

    final double[] xx = 
      new double[] { x0,x1,x2,
                     0.5*(x0+x1),0.5*(x1+x2),0.5*(x2+x0),
                     (x0+x1),(x1+x2),(x2+x0),
                     (x0+x1+x2)/3.0,
                     x0+x1+x2,
                     x0 + (x1-x0)*GOLDEN_RATIO,
                     x1 + (x2-x1)*GOLDEN_RATIO,
                     x2 + (x0-x2)*GOLDEN_RATIO,
                     f.doubleArgmin(),
                     g.doubleArgmin(),};
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

  private static final void tests (final double a0,
                                   final double a1,
                                   final double a2) {
    qtests(a0,a1,a2,-1.0,0.0,1.0, 1.0e0,1.0e0,1.0e0);
    qtests(a0,a1,a2,0.0,1.0,GOLDEN_RATIO, 1.0e1,5.0e0,1.0e1);
    qtests(a0,a1,a2,0.0,1.0,1.01, 5.0e1,1.0e2,1.0e3); 
    qtests(a0,a1,a2,0.49,0.50,0.51, 2.0e3,1.0e4,5.0e4); }

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void quadratic () {
    tests(1.0,1.0,-1.0);
    tests(1.0,-1.0,-1.0);
    tests(1.0,1.0,1.0);
    tests(1.0,-1.0,1.0); }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void affine () {
    tests(1.0,1.0,0.0);
    tests(1.0,-1.0,0.0); }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void constant () {
    tests(1.0,0.0,0.0); }

  //--------------------------------------------------------------
}
