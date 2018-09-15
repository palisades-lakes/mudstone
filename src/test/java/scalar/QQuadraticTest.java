package scalar;

import static java.lang.StrictMath.ulp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test 'exact' (BigFraction) cubic polynomial. 
 * <p>
 * <pre>
 * mvn -Dtest=palisades/lakes/cghzj/test/MathTest test > Math.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-14
 */

strictfp
public final class QQuadraticTest {

  // TODO: test singular cases, all coefficients, ...
  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q011 () {

    final QQuadratic f = QQuadratic.get(0.0,-1.0,1.0);

    Assertions.assertEquals(6.0,f.doubleValue(-2.0));
    Assertions.assertEquals(2.0,f.doubleValue(-1.0));
    Assertions.assertEquals(0.0,f.doubleValue(0.0));
    Assertions.assertEquals(-0.25,f.doubleValue(0.5));
    Assertions.assertEquals(0.0,f.doubleValue(1.0));
    Assertions.assertEquals(2.0,f.doubleValue(2.0));

    Assertions.assertEquals(-5.0,f.slope(-2.0));
    Assertions.assertEquals(-3.0,f.slope(-1.0));
    Assertions.assertEquals(-1.0,f.slope(0.0));
    final double delta = ulp(1.5);
    System.out.println(delta);
    Assertions.assertEquals(0.0,f.slope(0.5));
    Assertions.assertEquals(1.0,f.slope(1.0));
    Assertions.assertEquals(3.0,f.slope(2.0));

    Assertions.assertEquals(0.5,f.doubleArgmin());
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q110 () {

    final QQuadratic f = QQuadratic.get(1.0,1.0,0.0);

    Assertions.assertEquals(-1.0,f.doubleValue(-2.0));
    Assertions.assertEquals(0.0,f.doubleValue(-1.0));
    Assertions.assertEquals(1.0,f.doubleValue(0.0));
    Assertions.assertEquals(2.0,f.doubleValue(1.0));
    Assertions.assertEquals(3.0,f.doubleValue(2.0));

    Assertions.assertEquals(1.0,f.slope(-2.0));
    Assertions.assertEquals(1.0,f.slope(-1.0));
    Assertions.assertEquals(1.0,f.slope(0.0));
    Assertions.assertEquals(1.0,f.slope(0.5));
    Assertions.assertEquals(1.0,f.slope(1.0));
    Assertions.assertEquals(1.0,f.slope(2.0));

    Assertions.assertEquals(Double.NEGATIVE_INFINITY,f.doubleArgmin());
  }

  //--------------------------------------------------------------

}
