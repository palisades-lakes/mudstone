package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;
import static java.lang.StrictMath.sqrt;

import mudstone.java.functions.Domain;

/** A cubic function from <b>R</b> to <b>R</b> in monomial form.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-28
 */

public final class CubicMonomial extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _a0;
  private final double _a1;
  private final double _a2;
  private final double _a3;

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
      return fma(x,fma(x,fma(x,_a3,_a2),_a1),_a0);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      return fma(x,fma(x,3.0*_a3,2.0*_a2),_a1);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final double doubleArgmin (final Domain support) { 
    if (support.contains(_xmin)) { return _xmin; }
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
      _a1 + "*x + " +
      _a2 + "*x^2; " +
      _a3 + "*x^3; " +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: use BigFraction for argmin accuracy?

  private static final double 
  secondDerivative (final double a2,
                    final double a3,
                    final double q) {
    return (2.0*a2) +  (6.0*a3*q); }

  private static final double argmin (final double a1) {
    // assuming a3 and a2 are 0
    if (0.0 < a1) { return Double.NEGATIVE_INFINITY; }
    if (0.0 > a1) { return Double.POSITIVE_INFINITY; }
    return Double.NaN; } 

  private static final double argmin (final double a1,
                                      final double a2) {
    // assuming a3 is 0
    if (0.0 < a2) { return -0.5*a1/a2; } 
    if (0.0 > a2) { return Double.POSITIVE_INFINITY; } 
    return argmin(a1); } 

  // NOTE: a finite local minimum, if possible, rather than global
  // minimum at +/- infinity.

  private static final double argmin (final double a1,
                                      final double a2,
                                      final double a3) {

    if (0.0 == a3) { return argmin(a1,a2); }
    final double threea3 = 3.0*a3;

    if (0.0 == a2) {
      if (0.0 == a1) { 
        // ZERO is only critical point, not a min or max
        if (0.0 < a3) { return NEGATIVE_INFINITY; }
        if (0.0 > a3) { return POSITIVE_INFINITY; }
        // can't get here
        return NaN; }
      final double sq = -a1/threea3;
      if (0.0 >= sq) { // no critical points
        if (0.0 < a3) { return NEGATIVE_INFINITY; }
        if (0.0 > a3) { return POSITIVE_INFINITY; }
        // can't get here
        return NaN; }
      // critical points are +/- sqrt()
      // 2nd derivative is 6*a3*(critical point)
      // so choose the critical point with the same sign as a3
      if (0.0 < a3) { return sqrt(sq); }
      if (0.0 > a3) { return -sqrt(sq); }
      // can't get here
      return NaN; }

    if (0.0 == a1) { 
      // critical points are 0 and (-2*a2)/(3*a3) with
      // second derivatives 2*a2 and -2*a2\
      if (0.0 < a2) { return 0.0; }
      if (0.0 > a2) { return -2.0*a2/threea3; }
      // can't get here
      return NaN; }

    // a3, a2 and a1 all non-zero
    final double b2m4ac = (a2*a2) - (a1*threea3);
    // complex roots, no critical points
    if (0.0 > b2m4ac) {
      if (0.0 > a3) { return POSITIVE_INFINITY; }
      return NEGATIVE_INFINITY; }

    final double ma2 = -a2;
    final double sqrtb2m4ac = sqrt(b2m4ac);
    final double q0 = (ma2 - sqrtb2m4ac)/threea3;
    if (0.0 < secondDerivative(a2,a3,q0)) { return q0; }
    final double q1 = (ma2+sqrtb2m4ac)/threea3;
    if (0.0 < secondDerivative(a2,a3,q1)) { return q1; }
    if (0.0 > a3) { return POSITIVE_INFINITY; }
    return NEGATIVE_INFINITY; }

  //--------------------------------------------------------------

  private CubicMonomial (final double a0, 
                         final double a1,
                         final double a2,
                         final double a3) {
    _a0 = a0;
    _a1 = a1;
    _a2 = a2;
    _a3 = a3;
    _xmin = argmin(a1,a2,a3); 

    if (0.0 < a3) {
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a3) {
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY;  }
    else { // quadratic, so look at a2:
      if (0.0 < a2) {
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = POSITIVE_INFINITY; 
        _negativeLimitSlope = NEGATIVE_INFINITY; }
      else if (0.0 > a2) {
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = NEGATIVE_INFINITY; 
        _negativeLimitSlope = POSITIVE_INFINITY; }
      else { // affine, look at a1
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; 
        if (0.0 < a1) {
          _positiveLimitValue = POSITIVE_INFINITY; 
          _negativeLimitValue = NEGATIVE_INFINITY; }
        else if (0.0 > a1) {
          _positiveLimitValue = NEGATIVE_INFINITY; 
          _negativeLimitValue = POSITIVE_INFINITY; }
        else { // constant
          _positiveLimitValue = a0; 
          _negativeLimitValue = a0; } } } } 

  public static final CubicMonomial 
  make (final double a0, 
        final double a1,
        final double a2,
        final double a3) {
    return new CubicMonomial(a0,a1,a2,a3); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

