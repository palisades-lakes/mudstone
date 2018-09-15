package mudstone.java.test.functions.scalar;

import org.apache.commons.math3.fraction.BigFraction;
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
 * @version 2018-09-14
 */

public final class QQuadratic extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // Monomial basis coefficients

  private final BigFraction _a0;
  private final BigFraction _a1;
  private final BigFraction _a2;
  
  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    final BigFraction q = new BigFraction(x); 
    return 
      _a2
      .multiply(q).add(_a1)
      .multiply(q).add(_a0)
      .doubleValue(); }

  @Override
  public final double slope (final double x) {
    final BigFraction q = new BigFraction(x); 
    return 
      _a2.multiply(2)
      .multiply(q).add(_a1)
      .doubleValue(); }

  // TODO: ScalarFunctional parent class?
  
  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }
  
  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private static final BigFraction MINUS2 = TWO.negate();
  
  private static final double argmin (final BigFraction a1,
                                      final BigFraction a2) {
    switch (ZERO.compareTo(a2)) {
    case -1 : return a1.divide(MINUS2.multiply(a2)).doubleValue(); 
    case 1 : return Double.POSITIVE_INFINITY;
    default : 
      switch (ZERO.compareTo(a1)) {
      case -1 : return Double.NEGATIVE_INFINITY;
      case 1 : return Double.POSITIVE_INFINITY;
      default : return Double.NaN; } } }
  
  private QQuadratic (final BigFraction a0,
                  final BigFraction a1,
                  final BigFraction a2) { 
    super(); 
    _a0 = a0; _a1 = a1; _a2 = a2; 
    _xmin =argmin(a1,a2); }

  //--------------------------------------------------------------

  public static final QQuadratic get (final BigFraction a0,
                                  final BigFraction a1,
                                  final BigFraction a2) { 
    return new QQuadratic(a0,a1,a2); }
  
  public static final QQuadratic get (final double a0,
                                  final double a1,
                                  final double a2) { 
    return new QQuadratic(
      new BigFraction(a0),
      new BigFraction(a1),
      new BigFraction(a2)); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
