package mudstone.java.functions.scalar;

import static java.lang.Double.*;
import static java.lang.Math.fma;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ScalarFunctional;

/** A quadratic function from <b>R</b> to <b>R</b> interpolating
 * <code>(x0,d0=df(x0)), (x1,d1=df(x1), (x2,y2=f(x2))</code>.
 * The argmin of this parabola is at the zero of its derivative,
 * and is what's usually called the 'secant' step. 
 * <p>
 * Because this is primarily used for its
 * critical point (the zero of the derivative) the exact y values 
 * don't usually matter.
 * <p>
 * This is almost certainly overkill. It's intended to be used to 
 * calculate the secant step in scalar function minimization
 * (almost always in a line search), so there's a lot of mechanism
 * that's unlikely to ever be used. My excuse is that, by making 
 * the * various model functions explicit, the ideas underlying 
 * various 1d minimization algorithms should be more clearly 
 * expressed in the code.
 * The ideal here is that the code should be as easy to understand
 * as the descriptions in typical textbooks.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-19
 */

public final class InterpolantXD2XY1 extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // TODO: Monomial representation, maybe not the best choice. 
  // TODO: given monomial representation, only need single
  // Polynomial2 class?

  private final double _a0;
  private final double _a1;
  private final double _a2;
  private final double _x2;

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
      final double u = x-_x2;
      return fma(u,fma(u,_a2,_a1),_a0);  }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double z = x-_x2;
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
      _a0 + " + " + _a1 +"*(x-" + _x2 + ") + " +
      _a2 + "*(x-" + _x2 + ")^2; " +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private InterpolantXD2XY1 (final double x0, 
                             final double d0,
                             final double x1, 
                             final double d1,
                             final double x2,
                             final double y2) {
    _x2 = x2;
    _a0 = y2;
    final double z0 = x0-x2;
    final double z1 = x1-x2;
    final double dz = z1-z0; // =x1-x0
    final double dd = d1-d0;
    _a1 = (d0*z1-d1*z0)/dz;
    _a2 = 0.5*dd/dz;
    // limiting values:
    if (0.0 < _a2) {
      _xmin = x2 - 0.5*_a1/_a2;
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > _a2) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      if (0.0 < _a1) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = _a1; 
        _negativeLimitSlope = _a1; }
      else if (0.0 > _a1) {
        _xmin = POSITIVE_INFINITY;
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = _a1; 
        _negativeLimitSlope = _a1; }
      else { // constant
        _xmin = NaN;
        _positiveLimitValue = _a0; 
        _negativeLimitValue = _a0; 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  public static final InterpolantXD2XY1 
  make (final double x0, 
        final double d0,
        final double x1, 
        final double d1,
        final double x2,
        final double y2) { 
    if (x0 < x1) {
      return new InterpolantXD2XY1(x0,d0,x1,d1,x2,y2); }
    return new InterpolantXD2XY1(x1,d1,x0,d0,x2,y2); }

  public static final InterpolantXD2XY1 
  make (final Function f,
        final double x0, 
        final double x1, 
        final double x2) { 
    return make(
      x0,f.slope(x0),
      x1,f.slope(x1),
      x2,f.doubleValue(x2)); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

