package mudstone.java.test.scalar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mudstone.java.test.functions.scalar.QQuadratic;

//----------------------------------------------------------------
/** Test 'exact' (BigFraction) cubic polynomial. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/QQuadraticTest test > QQuadratic.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-16
 */

strictfp
public final class QQuadraticTest {

  // TODO: test singular cases, all coefficients, ...
  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q111 () {

    final QQuadratic f = QQuadratic.make(1.0,-1.0,1.0);
    Common.checkArgmin(f,1.0e0, 1.0e0);

    Assertions.assertEquals(7.0,f.doubleValue(-2.0));
    Assertions.assertEquals(3.0,f.doubleValue(-1.0));
    Assertions.assertEquals(1.0,f.doubleValue(0.0));
    Assertions.assertEquals(0.75,f.doubleValue(0.5));
    Assertions.assertEquals(1.0,f.doubleValue(1.0));
    Assertions.assertEquals(3.0,f.doubleValue(2.0));

    Assertions.assertEquals(-5.0,f.slope(-2.0));
    Assertions.assertEquals(-3.0,f.slope(-1.0));
    Assertions.assertEquals(-1.0,f.slope(0.0));
    Assertions.assertEquals(0.0,f.slope(0.5));
    Assertions.assertEquals(1.0,f.slope(1.0));
    Assertions.assertEquals(3.0,f.slope(2.0));

    Assertions.assertEquals(0.5,f.doubleArgmin());
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q011 () {

    final QQuadratic f = QQuadratic.make(0.0,-1.0,1.0);
    Common.checkArgmin(f,1.0e0, 1.0e0);

    Assertions.assertEquals(6.0,f.doubleValue(-2.0));
    Assertions.assertEquals(2.0,f.doubleValue(-1.0));
    Assertions.assertEquals(0.0,f.doubleValue(0.0));
    Assertions.assertEquals(-0.25,f.doubleValue(0.5));
    Assertions.assertEquals(0.0,f.doubleValue(1.0));
    Assertions.assertEquals(2.0,f.doubleValue(2.0));

    Assertions.assertEquals(-5.0,f.slope(-2.0));
    Assertions.assertEquals(-3.0,f.slope(-1.0));
    Assertions.assertEquals(-1.0,f.slope(0.0));
    Assertions.assertEquals(0.0,f.slope(0.5));
    Assertions.assertEquals(1.0,f.slope(1.0));
    Assertions.assertEquals(3.0,f.slope(2.0));

    Assertions.assertEquals(0.5,f.doubleArgmin());
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q110 () {

    final QQuadratic f = QQuadratic.make(1.0,1.0,0.0);
    Common.checkArgmin(f,1.0e0, 1.0e0);

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

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q100 () {

    final QQuadratic f = QQuadratic.make(1.0,0.0,0.0);
    Common.checkArgmin(f,1.0e0, 1.0e0);

    Assertions.assertEquals(1.0,f.doubleValue(-2.0));
    Assertions.assertEquals(1.0,f.doubleValue(-1.0));
    Assertions.assertEquals(1.0,f.doubleValue(0.0));
    Assertions.assertEquals(1.0,f.doubleValue(1.0));
    Assertions.assertEquals(1.0,f.doubleValue(2.0));

    Assertions.assertEquals(0.0,f.slope(-2.0));
    Assertions.assertEquals(0.0,f.slope(-1.0));
    Assertions.assertEquals(0.0,f.slope(0.0));
    Assertions.assertEquals(0.0,f.slope(0.5));
    Assertions.assertEquals(0.0,f.slope(1.0));
    Assertions.assertEquals(0.0,f.slope(2.0));

    Assertions.assertEquals(Double.NaN,f.doubleArgmin());
  }

  //--------------------------------------------------------------

}
