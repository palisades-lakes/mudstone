package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;

/** A quadratic function from <b>R</b> to <b>R</b> in monomial 
 * form.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-05
 */

public final class QuadraticMonomialShifted extends Polynomial {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _a0;
  private final double _a1;
  private final double _a2;
  // TODO: compose with translation instead of explicit origin?
  private final double _xorigin;

  // TODO: space vs re-computing cost?
  private final double _xmin;

  private final double _positiveLimitValue;
  private final double _negativeLimitValue;
  private final double _positiveLimitSlope;
  private final double _negativeLimitSlope;

  //--------------------------------------------------------------
  // Polynomial methods
  //--------------------------------------------------------------

  @Override
  public final int degree () { return 2; }
  
  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------
  // Horner's algorithm

  @Override
  public final double doubleValue (final double x) {
    if (isFinite(x)) {
      final double u = x-_xorigin;
      return fma(u,fma(u,_a2,_a1),_a0);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double z = x-_xorigin;
      return fma(z,2.0*_a2,_a1);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final double doubleArgmin (final Domain support) { 
    final Interval bounds = (Interval) support;
    if (bounds.contains(_xmin)) { return _xmin; }
    return NaN; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------
  // TODO: too long for toString..

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _a0 + " + " +
      _a1 + "*(x-" + _xorigin + ") + " +
      _a2 + "*(x-" + _xorigin + ")^2; " +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private QuadraticMonomialShifted (final double a0, 
                                    final double a1,
                                    final double a2, 
                                    final double xorigin) {
    _a0 = a0;
    _a1 = a1;
    _a2 = a2;
    _xorigin = xorigin;
    if (0.0 < a2) {
      _xmin = xorigin - 0.5*a1/a2;
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
        _xmin = NaN;
        _positiveLimitValue = a0; 
        _negativeLimitValue = a0; 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  public static final ScalarFunctional 
  make (final double a0, 
        final double a1,
        final double a2, 
        final double xorigin) {
    if (0.0==a2) {
      if (0.0==a1) { 
        return ConstantFunction.make(a0); }
      return AffineFunctional1d.make(fma(-a1,xorigin,a0),a1); }
    return new QuadraticMonomialShifted(a0,a1,a2,xorigin); }

  //--------------------------------------------------------------

  public static final ScalarFunctional 
  interpolateXY2D1 (final double x0, 
                    final double y0,
                    final double x1, 
                    final double y1,
                    final double x2,
                    final double d2) {
    assert x0 != x1;
    // TODO: use BigFraction to compute monomial coefficients?
    final double u0 = x0-x2;
    final double u02 = u0*u0;
    final double u1 = x1-x2;
    final double u12 = u1*u1;
    final double du = u1-u0;
    final double du2 = u12-u02;
    final double a0 = ((fma(-d2,u0,y0)*u12)-(fma(-d2,u1,y1)*u02))/du2;
    final double a1 = d2;
    final double a2 = fma(-d2,du,y1-y0)/du2; 
    return make(a0,a1,a2,x2); }

  public static final ScalarFunctional 
  interpolateXY2D1 (final Function f,
                    final double[] x) {
    return interpolateXY2D1(
      x[0],f.doubleValue(x[0]),
      x[1],f.doubleValue(x[1]),
      x[2],f.slope(x[2])); }

  public static final ScalarFunctional 
  interpolateXY2D1 (final Object f,
                    final Object x) {
    return interpolateXY2D1((Function) f, (double[]) x); }

  //--------------------------------------------------------------

  public static final ScalarFunctional 
  interpolateXD2Y1 (final double x0, 
                    final double d0,
                    final double x1, 
                    final double d1,
                    final double x2,
                    final double y2) {
    assert x0 != x1;
    final double a0 = y2;
    final double z0 = x0-x2;
    final double z1 = x1-x2;
    final double dz = z1-z0; // =x1-x0
    final double dd = d1-d0;
    final double a1 = (d0*z1-d1*z0)/dz;
    final double a2 = 0.5*dd/dz;
    return make(a0,a1,a2,x2); }

  public static final ScalarFunctional
  interpolateXD2Y1 (final Function f,
                    final double[] x) {
    return interpolateXD2Y1(
      x[0],f.slope(x[0]),
      x[1],f.slope(x[1]),
      x[2],f.doubleValue(x[2])); }

  public static final ScalarFunctional 
  interpolateXD2Y1 (final Object f,
                    final Object x) {
    return interpolateXD2Y1((Function) f, (double[]) x); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

