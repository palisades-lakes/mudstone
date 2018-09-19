package mudstone.java.test.functions.scalar;

import org.apache.commons.math3.fraction.BigFraction;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static org.apache.commons.math3.fraction.BigFraction.*;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** 'Exact' quadratic polynomial, implemented with rational 
 * numbers.
 * <p>
 * Immutable.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-18
 */

public final class QQuadratic extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // Monomial basis coefficients

  private final BigFraction _a0;
  private final BigFraction _a1;
  private final BigFraction _a2;

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
      final BigFraction q = new BigFraction(x); 
      return 
        _a2
        .multiply(q).add(_a1)
        .multiply(q).add(_a0)
        .doubleValue(); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final BigFraction q = new BigFraction(x); 
      return 
        _a2.multiply(2)
        .multiply(q).add(_a1)
        .doubleValue(); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + _a0.hashCode();
    result = prime * result + _a1.hashCode();
    result = prime * result + _a2.hashCode();
    final long temp = Double.doubleToLongBits(_xmin);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result; }

  @Override
  public final boolean equals (Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof QQuadratic)) { return false; }
    final QQuadratic other = (QQuadratic) obj;
    if (!_a0.equals(other._a0)) { return false; }
    if (!_a1.equals(other._a1)) { return false; }
    if (!_a2.equals(other._a2)) { return false; }
    // handles NaN
    if (Double.doubleToLongBits(_xmin) != 
      Double.doubleToLongBits(other._xmin)) { 
      return false; }
    return true; }

  @Override
  public String toString () {
    return 
      "Q[" + _a0 + " + " + _a1 + "*x + "
      + _a2 + "*x^2; " + _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private static final BigFraction MINUS2 = TWO.negate();

  private QQuadratic (final BigFraction a0,
                      final BigFraction a1,
                      final BigFraction a2) { 
    super(); 
    _a0 = a0; _a1 = a1; _a2 = a2; 
    // limiting values:
    final int a2sign = a2.compareTo(ZERO);
    if (0 < a2sign) {
      _xmin = a1.divide(MINUS2.multiply(a2)).doubleValue(); 
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0 > a2sign) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      final int a1sign = a1.compareTo(ZERO);
      if (0 < a1sign) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = a1.doubleValue(); 
        _negativeLimitSlope = a1.doubleValue(); }
      else if (0 > a1sign) {
        _xmin = POSITIVE_INFINITY;
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = a1.doubleValue(); 
        _negativeLimitSlope = a1.doubleValue(); }
      else { // constant
        _xmin = NaN;
        _positiveLimitValue = a0.doubleValue(); 
        _negativeLimitValue = a0.doubleValue(); 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  //--------------------------------------------------------------

  public static final QQuadratic get (final BigFraction a0,
                                      final BigFraction a1,
                                      final BigFraction a2) { 
    return new QQuadratic(a0,a1,a2); }

  public static final QQuadratic make (final double a0,
                                       final double a1,
                                       final double a2) { 
    return new QQuadratic(
      new BigFraction(a0),
      new BigFraction(a1),
      new BigFraction(a2)); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
