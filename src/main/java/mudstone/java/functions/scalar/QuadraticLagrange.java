package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

import org.apache.commons.math3.fraction.BigFraction;

import mudstone.java.functions.Function;

/** An quadratic function from <b>R</b> to <b>R</b> in Lagrange 
 * form.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-22
 */

public final class QuadraticLagrange extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _x0;
  private final double _x1;
  private final double _x2; 

  private final double _b0;
  private final double _b1;
  private final double _b2;

  // TODO: space vs re-computing cost?
  private final double _xmin;

  private final double _positiveLimitValue;
  private final double _negativeLimitValue;
  private final double _positiveLimitSlope;
  private final double _negativeLimitSlope;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      return fma(_b0,dx1*dx2,fma (_b1,dx2*dx0,_b2*dx0*dx1)); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      return fma(_b0,dx1+dx2,fma(_b1,dx2+dx0,_b2*(dx0+dx1))); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _x0 + "," + _x1 + "," + _x2 + ";" +
      _b0 + "," + _b1 + "," + _b2 + ";" +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // monomial coefficients for limiting values.
  // use BigFraction to be better at detecting affine and constant
  // functions

  private static final double a0 (final double x0, 
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
    final BigFraction z0 = 
      qy0.multiply(qx1).multiply(qx2)
      .divide(dx01.multiply(dx20));
    final BigFraction z1 = 
      qy1.multiply(qx2).multiply(qx0)
      .divide(dx12.multiply(dx01));
    final BigFraction z2 = 
      qy2.multiply(qx0).multiply(qx1)
      .divide(dx20.multiply(dx12));
    return z0.add(z1).add(z2).negate().doubleValue(); }

  private static final double a1 (final double x0, 
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
    final BigFraction z0 = 
      qy0.multiply(qx1.add(qx2))
      .divide(dx01.multiply(dx20));
    final BigFraction z1 = 
      qy1.multiply(qx2.add(qx0))
      .divide(dx12.multiply(dx01));
    final BigFraction z2 = 
      qy2.multiply(qx0.add(qx1))
      .divide(dx20.multiply(dx12));
    return z0.add(z1).add(z2).doubleValue(); }

  private static final double a2 (final double x0, 
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
    final BigFraction z0 = qy0.divide(dx01.multiply(dx20));
    final BigFraction z1 = qy1.divide(dx12.multiply(dx01));
    final BigFraction z2 = qy2.divide(dx20.multiply(dx12));
    return z0.add(z1).add(z2).negate().doubleValue(); }

  //--------------------------------------------------------------

  // symmetric argmin formula
  private QuadraticLagrange (final double x0, final double y0,
                             final double x1, final double y1,
                             final double x2, final double y2) {
    assert x0 != x1;
    assert x1 != x2;
    assert x2 != x0;
    _x0 = x0;
    _x1 = x1;
    _x2 = x2;
    _b0 = y0/((x0-x1)*(x0-x2));
    _b1 = y1/((x1-x2)*(x1-x0));
    _b2 = y2/((x2-x0)*(x2-x1));

    System.out.println("QL[" + 
      a0(x0,y0,x1,y1,x2,y2) + " + " + 
      a1(x0,y0,x1,y1,x2,y2) + "*x + " + 
      a2(x0,y0,x1,y1,x2,y2) + "*x^2]");
    // TODO: accurate 1st and 2nd derivative sign calculation?
    final double a2 = a2(x0,y0,x1,y1,x2,y2);
    if (0.0 < a2) {
      final double a = (x1-x2)*(y0-y1);
      final double b = (x0-x1)*(y1-y2);
      final double numer = ((x1+x0)*b)-((x1+x2)*a); 
      _xmin = numer/(2.0*(b-a)); 
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a2) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      final double a1 = a1(x0,y0,x1,y1,x2,y2);
      if (0.0 < a1) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; }
      else if (0.0 > a1) {
        _xmin = POSITIVE_INFINITY;
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; }
      else { // constant
        final double a0 = a0(x0,y0,x1,y1,x2,y2);
        _xmin = NaN;
        _positiveLimitValue = a0; 
        _negativeLimitValue = a0; 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  public static final QuadraticLagrange 
  make (final double x0, final double y0,
        final double x1, final double y1,
        final double x2, final double y2) {

    // TODO: not necessary to sort?
    if (x0 < x1) {
      if (x1 < x2) {
        return new QuadraticLagrange(x0,y0,x1,y1,x2,y2); }
      if (x0 < x2) {
        return new QuadraticLagrange(x0,y0,x2,y2,x1,y1); } 
      if (x2 < x0) {
        return new QuadraticLagrange(x2,y2,x0,y0,x1,y1); } }

    if (x1 < x0) {
      if (x0 < x2) {
        return new QuadraticLagrange(x1,y1,x0,y0,x2,y2); }
      if (x1 < x2) {
        return new QuadraticLagrange(x1,y1,x2,y2,x0,y0); } 
      if (x2 < x1) {
        return new QuadraticLagrange(x2,y2,x1,y1,x0,y0); } }

    throw new IllegalArgumentException(
      "Not distinct: " + x0 + ", " + x1 + ", " + x2); }

  public static final QuadraticLagrange 
  make (final Function f, 
        final double x0, 
        final double x1, 
        final double x2) {
    return make(
      x0,f.doubleValue(x0),
      x1,f.doubleValue(x1),
      x2,f.doubleValue(x2));}

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

