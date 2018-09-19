package mudstone.java.functions.scalar;

import static java.lang.Double.*;
import static java.lang.Math.fma;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ScalarFunctional;

/** A quadratic function from <b>R</b> to <b>R</b> interpolating
 * <code>(x0,y0=f(x0)), (x1,y1=f(x1), (x2,d2=df(x2))</code>.
 * <p>
 * This will primarily be used in the context of golden ratio
 * expansion/contraction steps in scalar function
 * minimization (almost always in a line search).
 * In that context, the argmin of the model function is already 
 * known, and passed to the constructor as <code>(x2,d2=0)</code>. 
 * <p>
 * Creating an (unnecessary) explicit model function puts golden 
 * steps into a common framework with secant, lagrange, and 
 * hermite steps. Although the model function isn't necessary in
 * any of those cases, thinking about which model function is a
 * better approximation to the function to be minimized gives us
 * some idea about which step might work better when.
 * At least, it makes it easier to generate figures comparing
 * the true function to its approximations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-19
 */

public final class InterpolantXY2XD1 extends ScalarFunctional {

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
      final double u = x-_x2;
      return fma(u,2.0*_a2,_a1);  }
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

  private InterpolantXY2XD1 (final double x0, 
                             final double y0,
                             final double x1, 
                             final double y1,
                             final double x2,
                             final double d2) {
    assert x0 != x1;
    // TODO: use BigFraction to compute monomial coefficients?
    final double u0 = x0-x2;
    final double u02 = u0*u0;
    final double u1 = x1-x2;
    final double u12 = u1*u1;
    final double du = u1-u0;
    final double du2 = u12-u02;
    _a0 = ((fma(-d2,u0,y0)*u12)-(fma(-d2,u1,y1)*u02))/du2;
    _a1 = d2;
    _a2 = fma(-d2,du,y1-y0)/du2; 
    _x2 = x2;
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

    public static final InterpolantXY2XD1 
    make (final double x0, 
          final double y0,
          final double x1, 
          final double y1,
          final double x2,
          final double d2) { 
      if (x0 < x1) {
        return new InterpolantXY2XD1(x0,y0,x1,y1,x2,d2); }
      return new InterpolantXY2XD1(x1,y1,x0,y0,x2,d2); }

    public static final InterpolantXY2XD1 
    make (final Function f,
          final double x0, 
          final double x1, 
          final double x2) { 
      return make(
        x0,f.doubleValue(x0),
        x1,f.doubleValue(x1),
        x2,f.slope(x2)); }

    //--------------------------------------------------------------
  }
  //--------------------------------------------------------------

