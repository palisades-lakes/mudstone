package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;
import static java.lang.Math.*;

/** A quadratic function from <b>R</b> to <b>R</b> in monomial 
 * form composed implicitly with standardizing affine functions
 * that map a domain interval to [0,1] and [0,1] codomain values
 * to a given codomain interval.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-24
 */

public final class QuadraticMonomialStandardized extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _a0;
  private final double _a1;
  private final double _a2;
  // TODO: compose with translation instead of explicit origin?
  private final double _bx;
  private final double _ax;
  private final double _by;
  private final double _ay;

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
      final double u = (x-_bx)/_ax;
      return fma(_ay,fma(u,fma(u,_a2,_a1),_a0),_by);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double z = x-_bx;
      return fma(z,2.0*_a2,_a1);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------
  // TODO: too long for toString..

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _a0 + " + " +
      _a1 + "*(x-" + _bx + ") + " +
      _a2 + "*(x-" + _bx + ")^2; " +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private QuadraticMonomialStandardized (final double a0, 
                                         final double a1,
                                         final double a2, 
                                         final double bx,
                                         final double ax,
                                         final double by,
                                         final double ay) {
    _a0 = a0;
    _a1 = a1;
    _a2 = a2;
    _bx = bx;
    _ax = ax;
    _by = by;
    _ay = ay;
    if (0.0 < a2) {
      _xmin = bx + ax*-0.5*a1/a2;
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

  public static final QuadraticMonomialStandardized 
  make (final double a0, 
        final double a1,
        final double a2, 
        final double x0,
        final double x1,
        final double y0,
        final double y1) {
    final double bx = min(x0,x1);
    final double ax = 1.0/(max(x0,x1) - bx);
    final double by = min(y0,y1);
    final double ay = max(y0,y1) - by;
    
    return new QuadraticMonomialStandardized(
      a0,a1,a2,bx,ax,by,ay); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

