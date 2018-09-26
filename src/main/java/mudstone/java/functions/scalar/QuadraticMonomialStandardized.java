package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

/** A quadratic function from <b>R</b> to <b>R</b> in monomial 
 * form composed implicitly with standardizing affine functions
 * that map a domain interval to [0,1] and [0,1] codomain values
 * to a given codomain interval.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class QuadraticMonomialStandardized 
extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // returned value is ay*f(ax*x+bx)+by where
  // f(u) = a0 + a1*u + a2*u^2

  // TODO: compare to composition with standardizing transforms?
  

  private final double _a0;
  private final double _a1;
  private final double _a2;
  
  private final double _ax;
  private final double _bx;
  
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
    final double u = fma(_ax,x,_bx);
    if (isFinite(u)) {
      return fma(_ay,fma(u,fma(u,_a2,_a1),_a0),_by);  }
    if (isNaN(u)) { return NaN; }
    if (POSITIVE_INFINITY == u) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    final double u = fma(_ax,x,_bx);
    if (isFinite(u)) {
      return _ax*_ay*fma(2.0*_a2,u,_a1);  }
    if (isNaN(u)) { return NaN; }
    if (POSITIVE_INFINITY == u) { return _positiveLimitSlope; }
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
      "u= "+ _ax + "*x + " + _bx + "; " + 
      "v= " + _a0 + " + " +
      _a1 + "*u + " +
      _a2 + "*u^2; " +
      "y= " + _ay + "*v + " + _by + "; " +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private QuadraticMonomialStandardized (final double a0, 
                                         final double a1,
                                         final double a2, 
                                         final double ax,
                                         final double bx,
                                         final double ay,
                                         final double by) {
    _a0 = a0;
    _a1 = a1;
    _a2 = a2;
    _bx = bx;
    _ax = ax;
    _by = by;
    _ay = ay;
    if (0.0 < a2*ay) {
      _xmin = fma(ax,-0.5*a1/a2,bx);
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a2*ay) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      if (0.0 < a1*ax*ay) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; }
      else if (0.0 > a1*ax*ay) {
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
        final DoubleInterval xInterval,
        final DoubleInterval yInterval) {
    final double x0 = xInterval.lower();
    final double x1 = xInterval.upper();
    final double dx = x1 - x0;
    final double y0 = yInterval.lower();
    final double y1 = yInterval.upper();
    final double dy = y1 - y0;
    
    return new QuadraticMonomialStandardized(
      a0,a1,a2,1.0/dx,-x0/dx,dy,y0); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

