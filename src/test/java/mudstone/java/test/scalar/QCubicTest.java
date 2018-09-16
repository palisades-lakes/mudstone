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
 * mvn -Dtest=mudstone/java/test/functions/scalar/QCubicTest test > QCubic.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-15
 */

strictfp
public final class QCubicTest {

  // TODO: test singular cases, all coefficients, ...
  //--------------------------------------------------------------

  @SuppressWarnings({ "static-method" })
  @Test
  public final void q0101 () {

    final QCubic f = QCubic.make(0.0,-1.0,0.0,1.0);

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

  //--------------------------------------------------------------

//  @SuppressWarnings({ "static-method" })
//  @Test
//  public final void q_7_24_9_1() {
//
//    final QCubic f = QCubic.get(7.0,24.0,-9.0,1.0);
//
//    Assertions.assertEquals(-29.0,f.doubleValue(-6.0));
//    Assertions.assertEquals(-6.0,f.doubleValue(-4.0));
//    Assertions.assertEquals(-13.0,f.doubleValue(-2.0));
//    Assertions.assertEquals(-9.0,f.doubleValue(-1.0));
//    Assertions.assertEquals(7.0,f.doubleValue(0.0));
//    Assertions.assertEquals(41.0,f.doubleValue(1.0));
//    Assertions.assertEquals(99.0,f.doubleValue(2.0));
//    Assertions.assertEquals(321.0,f.doubleValue(4.0));
//    Assertions.assertEquals(691.0,f.doubleValue(6.0));
//
//    Assertions.assertEquals(11.0,f.slope(-2.0));
//    Assertions.assertEquals(2.0,f.slope(-1.0));
//    final double delta = ulp(1.0+sqrt(3.0)/3.0);
//    System.out.println(delta);
//    Assertions.assertEquals(0.0,f.slope(-sqrt(3.0)/3.0),delta);
//    Assertions.assertEquals(-1.0,f.slope(0.0));
//    Assertions.assertEquals(0.0,f.slope(sqrt(3.0)/3.0),delta);
//    Assertions.assertEquals(2.0,f.slope(1.0));
//    Assertions.assertEquals(11.0,f.slope(2.0));
//
//    System.out.println(sqrt(3.0)/3.0);
//    System.out.println(f.doubleArgmin());
//    Assertions.assertEquals(sqrt(3.0)/3.0,f.doubleArgmin());
//  }


  @SuppressWarnings({ "static-method" })
  @Test
  public final void q1110 () {

    final QCubic f = QCubic.make(1.0,-1.0,1.0,0.0);

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
