package mudstone.java.test.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.doubleToLongBits;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sqrt;
import static org.apache.commons.math3.fraction.BigFraction.TWO;
import static org.apache.commons.math3.fraction.BigFraction.ZERO;

import org.apache.commons.math3.fraction.BigFraction;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional;
import mudstone.java.functions.scalar.Polynomial;

//----------------------------------------------------------------
/** 'Exact' cubic polynomial implemented with rational numbers.
 * <p>
 * Note: argmin cannot be exact, since it's an irrational number.
 * <p>
 * Immutable.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-09
 */

strictfp
public final class QCubic extends Polynomial {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // Monomial basis coefficients

  private final BigFraction _a0;
  private final BigFraction _a1;
  private final BigFraction _a2;
  private final BigFraction _a3;

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
  public final int degree () { return 3; }
  
  //--------------------------------------------------------------
  // ScalarFunctional methods
  //--------------------------------------------------------------
  
  private static final String safeString (final BigFraction bf) {
    final BigFraction q = bf.reduce();
    final long n = q.getNumeratorAsLong();
    final long d = q.getDenominatorAsLong();
    if (0L == n) { return "0"; }
    if (d == n) { return "1"; }
    if (d == -n) { return "m1"; }
    if (0L > n) { return "m" + abs(n) + "_" + d; }
    return n + "_" + d; }
  
  @Override
  public final String safeName () {
    return "Q3." + 
      safeString(_a0) + "." +
      safeString(_a1) + "." +
      safeString(_a2) + "." +
      safeString(_a3); }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    if (isFinite(x)) {
      final BigFraction q = new BigFraction(x); 
      return 
        _a3.multiply(q).add(_a2)
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
        _a3.multiply(3).multiply(q).add(_a2.multiply(2))
        .multiply(q).add(_a1)
        .doubleValue(); } 
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional.make(doubleValue(x),slope(x)); }

  @Override
  public final double doubleArgmin (final Domain support) { 
    if (support.contains(_xmin)) { return _xmin; }
    return NaN; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public int hashCode () {
    final int prime = 31;
    int result = 1;
    result = prime * result + _a0.hashCode();
    result = prime * result + _a1.hashCode();
    result = prime * result + _a2.hashCode();
    result = prime * result + _a3.hashCode();
    final long temp = doubleToLongBits(_xmin);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result; }

  @Override
  public final boolean equals (Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof QCubic)) { return false; }
    final QCubic other = (QCubic) obj;
    if (!_a0.equals(other._a0)) { return false; }
    if (!_a1.equals(other._a1)) { return false; }
    if (!_a2.equals(other._a2)) { return false; }
    if (!_a3.equals(other._a3)) { return false; }
    if (doubleToLongBits(_xmin) != Double
      .doubleToLongBits(other._xmin)) { return false; }
    return true; }

  @Override
  public final String toString () {
    return 
      "Q3[" + _a0 + " + " + _a1 + "*x + "
      + _a2 + "*x^2 + " + _a3 + "*x^3; " 
      + _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private static final BigFraction 
  secondDerivative (final BigFraction a2,
                    final BigFraction a3,
                    final BigFraction q) {
    return a2.multiply(2).add(a3.multiply(6).multiply(q)); }

  //--------------------------------------------------------------

  private static final BigFraction MINUS2 = TWO.negate();

  private static final double argmin (final BigFraction a1) {
    // assuming a3 and a2 are 0
    switch (ZERO.compareTo(a1)) {
    case -1 : return Double.NEGATIVE_INFINITY;
    case 1 : return Double.POSITIVE_INFINITY;
    default : return Double.NaN; } }

  private static final double argmin (final BigFraction a1,
                                      final BigFraction a2) {
    // assuming a3 is 0
    switch (ZERO.compareTo(a2)) {
    case -1 : return a1.divide(MINUS2.multiply(a2)).doubleValue(); 
    case 1 : return Double.POSITIVE_INFINITY;
    default : return argmin(a1); } }

  // NOTE: a finite local minimum, if possible, rather than global
  // minimum at +/- infinity.

  private static final double argmin (final BigFraction a1,
                                      final BigFraction a2,
                                      final BigFraction a3) {

    if (ZERO.equals(a3)) { return argmin(a1,a2); }
    final int a3sign = a3.compareTo(ZERO);
    final BigFraction threea3 = a3.multiply(3);

    if (ZERO.equals(a2)) {
      if (ZERO.equals(a1)) { 
        // ZERO is only critical point, not a min or max
        if (0 < a3sign) { return NEGATIVE_INFINITY; }
        if (0 > a3sign) { return POSITIVE_INFINITY; }
        // can't get here
        return NaN; }
      final BigFraction sq = a1.negate().divide(threea3);
      final int sqsign = sq.compareTo(ZERO);
      if (0 >= sqsign) { // no critical points
        if (0 < a3sign) { return NEGATIVE_INFINITY; }
        if (0 > a3sign) { return POSITIVE_INFINITY; }
        // can't get here
        return NaN; }
      // critical points are +/- sqrt()
      // 2nd derivative is 6*a3*(critical point)
      // so choose the critical point with the same sign as
      return a3sign*sqrt(sq.doubleValue()); }

    if (ZERO.equals(a1)) { 
      // critical points are 0 and (-2*a2)/(3*a3) with
      // second derivatives 2*a2 and -2*a2\
      final int a2sign = a2.compareTo(ZERO);
      if (0 < a2sign) { return 0.0; }
      if (0 > a2sign) { 
        return 
          a2.negate().multiply(2)
          .divide(threea3).doubleValue(); }
      // can't get here
      return NaN; }

    // a3, a2 and a1 all non-zero
    final BigFraction b2m4ac =
      a2.multiply(a2).subtract(a1.multiply(threea3));
    // complex roots, no critical points
    if (1 == ZERO.compareTo(b2m4ac)) {
      if (1 == ZERO.compareTo(a3)) { return POSITIVE_INFINITY; }
      return NEGATIVE_INFINITY; }

    final BigFraction ma2 = a2.negate();
    // !!!WARNING!!! not exact any more!
    // TODO: something more precise?
    final BigFraction sqrtb2m4ac = 
      new BigFraction(sqrt(b2m4ac.doubleValue()));
    final BigFraction q0 = 
      ma2.subtract(sqrtb2m4ac).divide(threea3);
    // TODO: don't rely on compareTo returning -1,0,1.
    if (-1 == ZERO.compareTo(secondDerivative(a2,a3,q0))) {
      return q0.doubleValue(); }
    final BigFraction q1 = 
      ma2.add(sqrtb2m4ac).divide(threea3);
    if (-1 == ZERO.compareTo(secondDerivative(a2,a3,q1))) {
      return q1.doubleValue(); }
    if (1 == ZERO.compareTo(a3)) {
      return POSITIVE_INFINITY; }
    return NEGATIVE_INFINITY; }

  //--------------------------------------------------------------

  private QCubic (final BigFraction a0,
                  final BigFraction a1,
                  final BigFraction a2,
                  final BigFraction a3) { 
    super(); 
    _a0 = a0; _a1 = a1; _a2 = a2; _a3 = a3; 
    _xmin = argmin(a1,a2,a3); 
    // limiting values:
    final int a3sign = a3.compareTo(ZERO);
    if (0 < a3sign) {
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0 > a3sign) {
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY;  }
    else { // quadratic, so look at a2:
      final int a2sign = a2.compareTo(ZERO);
      if (0 < a2sign) {
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = POSITIVE_INFINITY; 
        _negativeLimitSlope = NEGATIVE_INFINITY; }
      else if (0 > a2sign) {
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = NEGATIVE_INFINITY; 
        _negativeLimitSlope = POSITIVE_INFINITY; }
      else { // affine, look at a1
        _positiveLimitSlope = a1.doubleValue(); 
        _negativeLimitSlope = a1.doubleValue(); 
        final int a1sign = a1.compareTo(ZERO);
        if (0 < a1sign) {
          _positiveLimitValue = POSITIVE_INFINITY; 
          _negativeLimitValue = NEGATIVE_INFINITY; }
        else if (0 > a1sign) {
          _positiveLimitValue = NEGATIVE_INFINITY; 
          _negativeLimitValue = POSITIVE_INFINITY; }
        else { // constant
          _positiveLimitValue = a0.doubleValue(); 
          _negativeLimitValue = a0.doubleValue(); } } } } 

  //--------------------------------------------------------------

  public static final QCubic make (final BigFraction a0,
                                   final BigFraction a1,
                                   final BigFraction a2,
                                   final BigFraction a3) { 
    return new QCubic(a0,a1,a2,a3); }

  public static final QCubic make (final double a0,
                                   final double a1,
                                   final double a2,
                                   final double a3) { 
    return new QCubic(
      new BigFraction(a0),
      new BigFraction(a1),
      new BigFraction(a2),
      new BigFraction(a3)); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

