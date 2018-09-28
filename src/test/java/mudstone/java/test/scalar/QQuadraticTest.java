package mudstone.java.test.scalar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test 'exact' (BigFraction) quadratic polynomial. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QQuadraticTest test > QQuadratic.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-23
 */

strictfp
public final class QQuadraticTest {

  // TODO: test singular cases, all coefficients, ...
  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q111 () {

    final QQuadratic f = QQuadratic.make(1.0,-1.0,1.0);
    Common.checkArgmin(f,1.0e0,1.0e1);

    assertEquals(7.0,f.doubleValue(-2.0));
    assertEquals(3.0,f.doubleValue(-1.0));
    assertEquals(1.0,f.doubleValue(0.0));
    assertEquals(0.75,f.doubleValue(0.5));
    assertEquals(1.0,f.doubleValue(1.0));
    assertEquals(3.0,f.doubleValue(2.0));

    assertEquals(-5.0,f.slope(-2.0));
    assertEquals(-3.0,f.slope(-1.0));
    assertEquals(-1.0,f.slope(0.0));
    assertEquals(0.0,f.slope(0.5));
    assertEquals(1.0,f.slope(1.0));
    assertEquals(3.0,f.slope(2.0));

    assertEquals(0.5,f.doubleArgmin(null));
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q011 () {

    final QQuadratic f = QQuadratic.make(0.0,-1.0,1.0);
    Common.checkArgmin(f,1.0e0,1.0e0);

    assertEquals(6.0,f.doubleValue(-2.0));
    assertEquals(2.0,f.doubleValue(-1.0));
    assertEquals(0.0,f.doubleValue(0.0));
    assertEquals(-0.25,f.doubleValue(0.5));
    assertEquals(0.0,f.doubleValue(1.0));
    assertEquals(2.0,f.doubleValue(2.0));

    assertEquals(-5.0,f.slope(-2.0));
    assertEquals(-3.0,f.slope(-1.0));
    assertEquals(-1.0,f.slope(0.0));
    assertEquals(0.0,f.slope(0.5));
    assertEquals(1.0,f.slope(1.0));
    assertEquals(3.0,f.slope(2.0));

    assertEquals(0.5,f.doubleArgmin(null));
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q110 () {

    final QQuadratic f = QQuadratic.make(1.0,1.0,0.0);
    Common.checkArgmin(f,1.0e0, 1.0e0);

    assertEquals(-1.0,f.doubleValue(-2.0));
    assertEquals(0.0,f.doubleValue(-1.0));
    assertEquals(1.0,f.doubleValue(0.0));
    assertEquals(2.0,f.doubleValue(1.0));
    assertEquals(3.0,f.doubleValue(2.0));

    assertEquals(1.0,f.slope(-2.0));
    assertEquals(1.0,f.slope(-1.0));
    assertEquals(1.0,f.slope(0.0));
    assertEquals(1.0,f.slope(0.5));
    assertEquals(1.0,f.slope(1.0));
    assertEquals(1.0,f.slope(2.0));

    assertEquals(Double.NEGATIVE_INFINITY,f.doubleArgmin(null));
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q100 () {

    final QQuadratic f = QQuadratic.make(1.0,0.0,0.0);
    // constant, so argmin is NaN
    Common.checkArgmin(f,1.0,1.0e0);

    assertEquals(1.0,f.doubleValue(-2.0));
    assertEquals(1.0,f.doubleValue(-1.0));
    assertEquals(1.0,f.doubleValue(0.0));
    assertEquals(1.0,f.doubleValue(1.0));
    assertEquals(1.0,f.doubleValue(2.0));

    assertEquals(0.0,f.slope(-2.0));
    assertEquals(0.0,f.slope(-1.0));
    assertEquals(0.0,f.slope(0.0));
    assertEquals(0.0,f.slope(0.5));
    assertEquals(0.0,f.slope(1.0));
    assertEquals(0.0,f.slope(2.0));

    assertEquals(Double.NaN,f.doubleArgmin(null));
  }

  //--------------------------------------------------------------

}
