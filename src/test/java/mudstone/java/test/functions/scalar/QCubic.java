package mudstone.java.test.functions.scalar;

import static java.lang.StrictMath.sqrt;

import org.apache.commons.math3.fraction.BigFraction;
import static org.apache.commons.math3.fraction.BigFraction.ZERO;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** 'Exact' cubic polynomial implemented with rational numbers.
 * <p>
 * Note: argmin cannot be exact, since it's an irrational number.
 * <p>
 * Immutable.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-14
 */

strictfp
public final class QCubic extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // Monomial basis coefficients

  private final BigFraction _a0;
  private final BigFraction _a1;
  private final BigFraction _a2;
  private final BigFraction _a3;

  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {

    if (Double.isFinite(x)) {
      final BigFraction q = new BigFraction(x); 
      return 
        _a3.multiply(q).add(_a2)
        .multiply(q).add(_a1)
        .multiply(q).add(_a0)
        .doubleValue(); } 

    // TODO: could throw an exception...
    if (Double.isNaN(x)) { return Double.NaN; }
    
    // otherwise x is +/- infinity; sign of value depends on a3
    switch (ZERO.compareTo(_a3)) {
    case -1 : return x; 
    case 1 : return -x; 
    case 0 : // quadratic, so look at a2:
      switch (ZERO.compareTo(_a2)) {
      case -1 : return Double.POSITIVE_INFINITY; 
      case 1 : return Double.NEGATIVE_INFINITY; 
      case 0 : // affine, look at a1
        switch (ZERO.compareTo(_a1)) {
        case -1 : return Double.POSITIVE_INFINITY; 
        case 1 : return Double.NEGATIVE_INFINITY; 
        case 0 : // constant
          return _a0.doubleValue();
        default : 
          throw new IllegalStateException("can't get here"); } 
      default : 
        throw new IllegalStateException("can't get here"); } 
    default : 
      throw new IllegalStateException("can't get here"); } }

  @Override
  public final double slope (final double x) {
    if (Double.isFinite(x)) {
      final BigFraction q = new BigFraction(x); 
      return 
        _a3.multiply(3).multiply(q).add(_a2.multiply(2))
        .multiply(q).add(_a1)
        .doubleValue(); } 
    
    // TODO: could throw an exception...
    if (Double.isNaN(x)) { return Double.NaN; }

    // otherwise x is +/- infinity; sign of value depends on a3
    switch (ZERO.compareTo(_a3)) {
    case -1 : return Double.POSITIVE_INFINITY; 
    case 1 : return Double.NEGATIVE_INFINITY; 
    case 0 : // quadratic, so look at a2:
      switch (ZERO.compareTo(_a2)) {
      case -1 : return x; 
      case 1 : return -x; 
      case 0 : // affine
        return _a1.doubleValue();
      default : 
        throw new IllegalStateException("can't get here"); } 
    default : 
      throw new IllegalStateException("can't get here"); } }



  // TODO: ScalarFunctional parent class?

  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  @Override
  public final double doubleArgmin () { return _xmin; }

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
    final long temp = Double.doubleToLongBits(_xmin);
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
    if (Double.doubleToLongBits(_xmin) != Double
      .doubleToLongBits(other._xmin)) { return false; }
    return true; }

  @Override
  public final String toString () {
    return 
      "Q[" + _a0 + " + " + _a1 + "*x + "
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
  // NOTE: a finite local minimum, if possible, rather than global
  // minimum at +/- infinity.

  private static final double argmin (final BigFraction a1,
                                      final BigFraction a2,
                                      final BigFraction a3) {

    if (ZERO.equals(a3)) { return QQuadratic.argmin(a1,a2); }

    final BigFraction threea3 = a3.multiply(3);
    final BigFraction b2m4ac =
      a2.multiply(a2).subtract(a1.multiply(threea3));
    // complex roots, no critical points
    if (1 == ZERO.compareTo(b2m4ac)) {
      if (1 == ZERO.compareTo(a3)) {
        return Double.POSITIVE_INFINITY; }
      return Double.NEGATIVE_INFINITY; }

    final BigFraction ma2 = a2.negate();
    // !!!WARNING!!! not exact any more!
    // TODO: something more precise?
    final BigFraction sqrtb2m4ac = 
      new BigFraction(sqrt(b2m4ac.doubleValue()));
    final BigFraction q0 = 
      ma2.subtract(sqrtb2m4ac).divide(threea3);
    if (-1 == ZERO.compareTo(secondDerivative(a2,a3,q0))) {
      return q0.doubleValue(); }
    final BigFraction q1 = 
      ma2.add(sqrtb2m4ac).divide(threea3);
    if (-1 == ZERO.compareTo(secondDerivative(a2,a3,q1))) {
      return q1.doubleValue(); }
    if (1 == ZERO.compareTo(a3)) {
      return Double.POSITIVE_INFINITY; }
    return Double.NEGATIVE_INFINITY; }

  //--------------------------------------------------------------

  private QCubic (final BigFraction a0,
                  final BigFraction a1,
                  final BigFraction a2,
                  final BigFraction a3) { 
    super(); 
    _a0 = a0; _a1 = a1; _a2 = a2; _a3 = a3; 
    _xmin = argmin(a1,a2,a3); }

  //--------------------------------------------------------------

  public static final QCubic get (final BigFraction a0,
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

