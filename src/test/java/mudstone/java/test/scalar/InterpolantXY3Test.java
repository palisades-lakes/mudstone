package mudstone.java.test.scalar;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.ulp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.functions.scalar.InterpolantXY3;
import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test 3 point quadratic interpolation. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/InterpolantXY3Test test > InterpolantXY3Test.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-17
 */

strictfp
public final class InterpolantXY3Test {

  private static final double GOLDEN_RATIO = 
    0.5*(1.0+Math.sqrt(5.0));

  public static final void quadratic (final double a0,
                                      final double a1,
                                      final double a2,
                                      final double x0,
                                      final double x1,
                                      final double x2,
                                      final double ulps) {
    final QQuadratic f = QQuadratic.make(a0,a1,a2);
    Common.checkArgmin(f,1.0e0, 1.0e0);
    //System.out.println(f);
    final InterpolantXY3 g = InterpolantXY3.make(f,x0,x1,x2);
    Common.checkArgmin(g,4.0e0, 1.0e0);
   //System.out.println(g);

    assertEquals(f.doubleArgmin(),g.doubleArgmin());

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
            " by " + abs(fi-gi)/delta + "\n"; });
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
    quadratic(1.0,-1.0,1.0,0.0,1.0,GOLDEN_RATIO,1.0e1);
    quadratic(1.0,-1.0,1.0,0.0,1.0,1.01,5.0e1); 
    quadratic(1.0,-1.0,1.0,0.49,0.50,0.51,2.0e3); 
  }

  //--------------------------------------------------------------
}
