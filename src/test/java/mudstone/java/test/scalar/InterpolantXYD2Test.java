package mudstone.java.test.scalar;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.functions.scalar.InterpolantXYD2;
import mudstone.java.test.functions.scalar.QCubic;

//----------------------------------------------------------------
/** Test 3 point quadratic interpolation. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/InterpolantXYD2Test test > InterpolantXYD2Test.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-17
 */

strictfp
public final class InterpolantXYD2Test {

  private static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  public static final void cubic (final double a0,
                                  final double a1,
                                  final double a2,
                                  final double a3,
                                  final double x0,
                                  final double x1,
                                  final double ulps) {
    final QCubic f = QCubic.make(a0,a1,a2,a3);
    Common.checkArgmin(f,1.0e0);
    //System.out.println(f);
    final InterpolantXYD2 g = InterpolantXYD2.make(f,x0,x1);
    Common.checkArgmin(g,1.0e1);
    //System.out.println(g);

    assertEquals(
      f.doubleArgmin(),g.doubleArgmin(),
      () -> { 
        return "\n" +
          f.getClass().getSimpleName() + ":" +
          f.doubleArgmin() + " != " + 
          g.getClass().getSimpleName() + ":" +
          g.doubleArgmin() + "\n"; });

    final double[] xx = 
      new double[] { x0, x1, 
                     0.5*(x0+x1),
                     (x0+x1), 
                     x0 + (x1-x0)*GOLDEN_RATIO,
                     x1 + (x0-x1)*GOLDEN_RATIO,
                     f.doubleArgmin(),
                     g.doubleArgmin(), 
                     Double.POSITIVE_INFINITY,
                     Double.NEGATIVE_INFINITY,
                     Double.NaN, };
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
          return "\n|" + fi + " - " + gi + "| = " +
            abs(fi-gi) + " > " + delta + 
            "\n by " + abs(fi-gi)/delta + 
            "\n at " + xi + "\n"; });
      final double dfi = f.slope(xi);
      final double dgi = g.slope(xi);
      final double beta = abs(dfi)+abs(dgi);
      final double epsilon = 
        ulps*ulp(1.0+(Double.isNaN(beta) ? 0.0 : beta));
      assertEquals(dfi,dgi,epsilon,
        () -> { return "\n|" + dfi + " - " + dgi + "| = " +
          abs(dfi-dgi) + ">" + epsilon + 
          "\n by " + abs(dfi-dgi)/epsilon + 
          "\n at " + xi + "\n"; });
    } } 

  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q1111 () {
    cubic(1.0,-1.0,1.0,-1.0,0.0,1.0,5.0e0);
    cubic(1.0,-1.0,1.0,-1.0,1.0,GOLDEN_RATIO,1.0e1);
    cubic(1.0,-1.0,1.0,-1.0,1.0,1.01,1.0e4); 
    cubic(1.0,-1.0,1.0,-1.0,0.49,0.51,1.0e4); 
  }

  //--------------------------------------------------------------
}
