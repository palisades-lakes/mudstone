package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

/** A quadratic function from <b>R</b> to <b>R</b> in monomial 
 * form.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-21
 */

public final class QuadraticMonomialShifted extends ScalarFunctional {

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
  public final double doubleArgmin (final DoubleInterval support) { 
    return _xmin; }

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

  public static final QuadraticMonomialShifted 
  make (final double a0, 
        final double a1,
        final double a2, 
        final double xorigin) {
    return new QuadraticMonomialShifted(a0,a1,a2,xorigin); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

