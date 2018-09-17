package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.abs;
import static java.lang.Math.fma;
import static java.lang.Math.sqrt;

import mudstone.java.functions.Function;

/** A cubic function from <b>R</b> to <b>R</b> interpolating
 * 2 (x,y=f(x),d=df(x)) triples (Hermite interpolant).
 * <p>
 * Only argmin implemented for now.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-17
 */

public final class InterpolantXYD2 extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _x0;
  private final double _dx10;
  private final double _y0;
  private final double _y1;
  private final double _d0;
  private final double _d1;

  // TODO: space vs re-computing cost?
  private final double _xmin;

  private final double _positiveLimitValue;
  private final double _negativeLimitValue;
  private final double _positiveLimitSlope;
  private final double _negativeLimitSlope;

  //--------------------------------------------------------------
  // hermite basis
  //--------------------------------------------------------------
  // TODO: what's the most accurate/efficient representation?
  // see wikipedia Cubic Hermite Spline

  // factored form
  //  private static final double h00 (final double t) {
  //    return (1.0+2.0*t)*(1.0-t)*(1.0-t); }
  // 
  //  private static final double h01 (final double t) {
  //    return t*t*(3.0-2.0*t); }
  //  
  //  private static final double h10 (final double t) {
  //    return t*(1.0-t)*(1.0-t); }
  //  
  //  private static final double h11 (final double t) {
  //    return t*t*(t-1.0); }

  // monomial form using fma/Horner for evaluation
  private static final double h00 (final double t) {
    return fma(fma(2.0,t,-3),t*t,1.0); }

  private static final double h01 (final double t) {
    return t*t*fma(-2.0,t,3.0); }

  private static final double h10 (final double t) {
    return t*fma(t,t-2.0,1); }

  private static final double h11 (final double t) {
    return t*fma(t,t,-t); }

  private static final double dh00 (final double t) {
    return 6.0*fma(t,t,-t); }

  private static final double dh01 (final double t) {
    return -6.0*fma(t,t,-t); }

  private static final double dh10 (final double t) {
    return fma(t,fma(3.0,t,-4.0),1); }

  private static final double dh11 (final double t) {
    return t*fma(3.0,t,-2.0); }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {

    final double t = (x-_x0)/_dx10;
    if (isFinite(x)) {
      return 
        _y0*h00(t) +
        _y1*h01(t) +
        _d0*_dx10*h10(t) +
        _d1*_dx10*h11(t); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double t = (x-_x0)/_dx10;
      return 
        _y0*dh00(t)/_dx10 +
        _y1*dh01(t)/_dx10 +
        _d0*dh10(t) +
        _d1*dh11(t); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  // Note: a local minimum when it exists; 
  // global minimum is either +/- infinity.

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _x0 + "," + _y0 + "," + _d0 + ";" +
      (_dx10+_x0) + "," + _y1 + "," + _d1 + ";" +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private static final double a3 (final double x0,
                                  final double y0,
                                  final double d0,
                                  final double x1,
                                  final double y1,
                                  final double d1) {
    final double dx = x1-x0;
    return fma(2.0,(y0-y1),d0+d1)/(dx*dx*dx); }

  private static final double a2 (final double x0,
                                  final double y0,
                                  final double d0,
                                  final double x1,
                                  final double y1,
                                  final double d1) {
    final double dx = x1-x0;
    return fma(3.0,(y1-y0),fma(-2.0,d0,d1))/(dx*dx); }

  private static final double argmin (final double x0,
                                      final double y0,
                                      final double d0,
                                      final double x1,
                                      final double y1,
                                      final double d1) {
    final double dx = x1-x0;
    final double v = (d0+d1) - ((3.0*(y1-y0)) / dx);
    final double t = (v*v) - (d0*d1);
    // complex roots, no critical points
    // could return global min at +/- infinity.
    if (t < 0.0) { 
      final double a3 = a3(x0,y0,d0,x1,y1,d1);
      if (0.0 > a3) { return POSITIVE_INFINITY; }
      return NEGATIVE_INFINITY; }

    final double w0 = sqrt(t);
    final double w1 = (d0+v)-w0;
    final double w2 = (d1+v)+w0;
    if ((w1 == 0.0) && (w2 == 0.0)) { return NaN; }
    if (abs(w1) >= abs(w2)) { return  x0 + ((dx*d0) / w1); }
    return x1 - ((dx*d1)/w2); } 

  private InterpolantXYD2 (final double x0, 
                           final double y0,
                           final double d0,
                           final double x1, 
                           final double y1,
                           final double d1) {
    assert x0 < x1  : "Fail: " + x0 + " < " + x1 ;
    _x0 = x0;
    _dx10 = x1-x0;
    _y0 = y0;
    _y1 = y1;
    _d0 = d0;
    _d1 = d1;
    _xmin = argmin(x0,y0,d0,x1,y1,d1); 
    // limiting values:
    // sign of limit depends on monomial 3
    if (0.0 < a3(x0,y0,d0,x1,y1,d1)) {
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a3(x0,y0,d0,x1,y1,d1)) {  
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else // quadratic, so look at a2:
      if (0.0 < a2(x0,y0,d0,x1,y1,d1)) {
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = POSITIVE_INFINITY; 
        _negativeLimitSlope = NEGATIVE_INFINITY; }
      else if (0.0 > a2(x0,y0,d0,x1,y1,d1)) {
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = NEGATIVE_INFINITY; 
        _negativeLimitSlope = POSITIVE_INFINITY; }
      else { // affine, look at a1
        if (0.0 < d0) {
          _positiveLimitValue = POSITIVE_INFINITY; 
          _negativeLimitValue = NEGATIVE_INFINITY; 
          _positiveLimitSlope = d0; 
          _negativeLimitSlope = d0; }
        else if (0.0 > d0) {
          _positiveLimitValue = NEGATIVE_INFINITY; 
          _negativeLimitValue = POSITIVE_INFINITY; 
          _positiveLimitSlope = d0; 
          _negativeLimitSlope = d0; }
        else {
          _positiveLimitValue = y0; 
          _negativeLimitValue = y0; 
          _positiveLimitSlope = 0.0; 
          _negativeLimitSlope = 0.0; } } } 

  public static final InterpolantXYD2 
  make (final double x0, final double y0, final double d0,
        final double x1, final double y1, final double d1) {
    if (x0 < x1) {
      return new InterpolantXYD2(x0,y0,d0,x1,y1,d1); }
    return new InterpolantXYD2(x1,y1,d1,x0,y0,d0); }

  public static final InterpolantXYD2 
  make (final Function f, final double x0, final double x1) {
    final double y0 = f.doubleValue(x0);
    final double d0 = f.slope(x0);
    final double y1 = f.doubleValue(x1);
    final double d1 = f.slope(x1);
    return InterpolantXYD2.make(x0,y0,d0,x1,y1,d1); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

