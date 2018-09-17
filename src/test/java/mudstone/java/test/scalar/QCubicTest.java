package mudstone.java.test.scalar;

import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.ulp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mudstone.java.test.functions.scalar.QCubic;

//----------------------------------------------------------------
/** Test 'exact' (BigFraction) cubic polynomial. 
 * <p>
 * <pre>
 * mvn -Dtest=mudstone/java/test/scalar/QCubicTest test > QCubic.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-16
 */

strictfp
public final class QCubicTest {

  // TODO: test singular cases, all coefficients, ...
  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q0101 () {

    final QCubic f = QCubic.make(0.0,-1.0,0.0,1.0);
    Common.checkArgmin(f,1.0e0);

    Assertions.assertEquals(-6.0,f.doubleValue(-2.0));
    Assertions.assertEquals(0.0,f.doubleValue(-1.0));
    Assertions.assertEquals(0.0,f.doubleValue(0.0));
    Assertions.assertEquals(0.0,f.doubleValue(1.0));
    Assertions.assertEquals(6.0,f.doubleValue(2.0));

    Assertions.assertEquals(11.0,f.slope(-2.0));
    Assertions.assertEquals(2.0,f.slope(-1.0));
    final double delta = ulp(1.0+sqrt(3.0)/3.0);
    Assertions.assertEquals(0.0,f.slope(-sqrt(3.0)/3.0),delta);
    Assertions.assertEquals(-1.0,f.slope(0.0));
    Assertions.assertEquals(0.0,f.slope(sqrt(3.0)/3.0),delta);
    Assertions.assertEquals(2.0,f.slope(1.0));
    Assertions.assertEquals(11.0,f.slope(2.0));
    Assertions.assertEquals(sqrt(3.0)/3.0,f.doubleArgmin());
  }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q1110 () {

    final QCubic f = QCubic.make(1.0,-1.0,1.0,0.0);
    Common.checkArgmin(f,1.0e0);

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
  public final void q1100 () {

    final QCubic f = QCubic.make(1.0,1.0,0.0,0.0);
    Common.checkArgmin(f,1.0e0);

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
  public final void q1000 () {

    final QCubic f = QCubic.make(1.0,0.0,0.0,0.0);
    Common.checkArgmin(f,1.0e0);

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
//--------------------------------------------------------------
