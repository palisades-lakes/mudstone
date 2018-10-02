package mudstone.java.functions.scalar;

import static java.lang.Math.*;
import org.apache.commons.math3.fraction.BigFraction;
import static org.apache.commons.math3.fraction.BigFraction.*;

/** Utilities related to quadratic polynomials.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-01
 */

public final class QuadraticUtils  {

  //--------------------------------------------------------------
  /** Return a <code>BigFraction[]</code> containing the real 
   * zeros of <code>a0+a1*x+a2*x^2</code>.
   * If there is onyl 1, return an array of length 1.
   * If there are no real roots, return an empty array.
   * <p>
   * Calculations are exact except for square root, which rounds 
   * to <code>double</code> twice. Could implement an arbitrary
   * precision rational sqrt to get enough accuracy so that the 
   * final result is the exact answer rounded to 
   * <code>double</code>.
   */

  public static final BigFraction[] roots (final BigFraction a0,
                                           final BigFraction a1,
                                           final BigFraction a2) {
    final BigFraction b2m4ac = 
      a1.multiply(a1).subtract(a0.multiply(a2).multiply(4));
    final int sgn = b2m4ac.compareTo(ZERO);
    if (sgn < 0) { return new BigFraction[0]; }
    final BigFraction m2a2 = a2.multiply(-2);
    if (sgn == 0) { 
      return new BigFraction[] { a1.divide(m2a2), }; }
    final BigFraction sqrtb2m4ac = 
      new BigFraction(sqrt(b2m4ac.doubleValue())); 
    
    return new BigFraction [] 
      { a1.add(sqrtb2m4ac).divide(m2a2),
        a1.subtract(sqrtb2m4ac).divide(m2a2), }; }

  //--------------------------------------------------------------
  /** return a <code>double[]</code> containing the real zeros
   * of <code>a0+a1*x+a2*x^2</code>.
   * If there is onyl 1, return an array of length 1.
   * If there are no real roots, return an empty array.
   */

  public static final double[] roots (final double a0,
                                      final double a1,
                                      final double a2) {
    final double b2m4ac = (a1*a1) - (4.0*a0*a2);
    if (b2m4ac < 0.0) { return new double[0]; }
    if (b2m4ac == 0.0) { return new double[] { -0.5*a1/a2, }; }
    final double sqrtb2m4ac = sqrt(b2m4ac);  
    return new double[] 
      { 0.5*(-a1 - sqrtb2m4ac)/a2,
        0.5*(-a1 + sqrtb2m4ac)/a2, }; }

  //--------------------------------------------------------------
  // use BigFraction to be better at detecting affine and constant
  // functions

  /** Return the monomial coefficients for a parabola that 
   * interpolates <code>(x0,y0), (x1,y1), x2,y2)</code>.
   * Use {@link BigFraction} internally for accuracy.
   */

  public static final double[] 
    interpolatingMonomialCoefficients (final double x0, 
                                       final double y0,
                                       final double x1, 
                                       final double y1,
                                       final double x2, 
                                       final double y2) {
    final BigFraction qx0 = new BigFraction(x0);
    final BigFraction qy0 = new BigFraction(y0);
    final BigFraction qx1 = new BigFraction(x1);
    final BigFraction qy1 = new BigFraction(y1);
    final BigFraction qx2 = new BigFraction(x2);
    final BigFraction qy2 = new BigFraction(y2);
    final BigFraction dx01 = qx0.subtract(qx1);
    final BigFraction dx12 = qx1.subtract(qx2);
    final BigFraction dx20 = qx2.subtract(qx0);
    final BigFraction dx0120 = dx01.multiply(dx20);
    final BigFraction dx1201 = dx12.multiply(dx01);
    final BigFraction dx2012 = dx20.multiply(dx12);
    final BigFraction a00 = qy0.multiply(qx1).multiply(qx2)
      .divide(dx0120);
    final BigFraction a01 = qy1.multiply(qx2).multiply(qx0)
      .divide(dx1201);
    final BigFraction a02 = qy2.multiply(qx0).multiply(qx1)
      .divide(dx2012);
    final BigFraction a10 = qy0.multiply(qx1.add(qx2))
      .divide(dx0120);
    final BigFraction a11 = qy1.multiply(qx2.add(qx0))
      .divide(dx1201);
    final BigFraction a12 = qy2.multiply(qx0.add(qx1))
      .divide(dx2012);
    final BigFraction a20 = qy0.divide(dx0120);
    final BigFraction a21 = qy1.divide(dx1201);
    final BigFraction a22 = qy2.divide(dx2012);

    return new double[] 
      { a00.add(a01).add(a02).negate().doubleValue(), 
        a10.add(a11).add(a12).doubleValue(),
        a20.add(a21).add(a22).negate().doubleValue(), }; }

  //--------------------------------------------------------------
  // disabled constructor
  //--------------------------------------------------------------

  private QuadraticUtils () {
    throw new UnsupportedOperationException(
      "can't instantiate" + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

