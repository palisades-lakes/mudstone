package mudstone.java.test.scalar;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.functions.scalar.CubicHermite;
import mudstone.java.test.functions.scalar.QCubic;

//----------------------------------------------------------------
/** Test 2 point cubic hermite interpolation. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/CubicHermiteTest test > CubicHermiteTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-22
 */

strictfp
public final class CubicHermiteTest {

  private static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  public static final void cubicTests (final double a0,
                                       final double a1,
                                       final double a2,
                                       final double a3,
                                       final double x0,
                                       final double x1,
                                       final double xulps,
                                       final double yulps,
                                       final double dulps) {
    final QCubic f = QCubic.make(a0,a1,a2,a3);
    System.out.println(f);
    final CubicHermite g = 
      CubicHermite.make(
        x0,f.doubleValue(x0),f.slope(x0),
        x1,f.doubleValue(x1),f.slope(x1));
    System.out.println(g);

    Common.checkArgmin(f,1.0e0,dulps);
    Common.checkArgmin(g,1.0e2,dulps);

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
      new double[] { x0,x1,
                     0.5*(x0+x1),
                     (x0+x1),
                     x0 + (x1-x0)*GOLDEN_RATIO,
                     x1 + (x0-x1)*GOLDEN_RATIO,
                     f.doubleArgmin(),
                     g.doubleArgmin(),
                     Double.POSITIVE_INFINITY,
                     Double.NEGATIVE_INFINITY,
                     Double.NaN,};
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
          return "\n|" + fi + " - " + gi + "| = " +
            abs(fi-gi) + " > " + delta + 
            "\n by " + abs(fi-gi)/delta + 
            "\n at " + xi + "\n"; });
      final double dfi = f.slope(xi);
      final double dgi = g.slope(xi);
      final double beta = abs(dfi)+abs(dgi);
      final double epsilon = 
        dulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
      assertEquals(dfi,dgi,epsilon,
        () -> { return "\n|" + dfi + " - " + dgi + "| = " +
          abs(dfi-dgi) + ">" + epsilon + 
          "\n by " + abs(dfi-dgi)/epsilon + 
          "\n at " + xi + "\n"; });
    } } 

  //--------------------------------------------------------------

  private static final void tests (final double a0,
                                   final double a1,
                                   final double a2,
                                   final double a3) {
    cubicTests(a0,a1,a2,a3,0.0,1.0, 5.0e0,2.0e0,1.0e1);
    cubicTests(a0,a1,a2,a3,1.0,GOLDEN_RATIO, 1.0e1,1.0e1,5.0e2);
    cubicTests(a0,a1,a2,a3,1.0,1.01, 1.0e6,5.0e5,2.0e6); 
    cubicTests(a0,a1,a2,a3,0.49,0.51, 5.0e3,1.0e4,1.0e5); }

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void cubic () {
    tests(1.0,-1.0,1.0,-1.0);
    tests(1.0,-1.0,-1.0,1.0);
    tests(1.0,1.0,1.0,1.0);
    tests(1.0,-1.0,1.0,1.0); }

  //    @SuppressWarnings({ "static-method" })
  //    @Test
  //    public final void quadratic () {
  //      tests(1.0,1.0,-1.0,0.0);
  //      tests(1.0,-1.0,-1.0,0.0);
  //      tests(1.0,1.0,1.0,0.0);
  //      tests(1.0,-1.0,1.0,0.0); }

//  @SuppressWarnings({ "static-method" })
//  @Test
//  public final void affine () {
//    tests(1.0,1.0,0.0,0.0);
//    tests(1.0,-1.0,0.0,0.0); }

  //  @SuppressWarnings({ "static-method" })
  //  @Test
  //  public final void constant () {
  //    tests(1.0,0.0,0.0,0.0); }

  //--------------------------------------------------------------
}
