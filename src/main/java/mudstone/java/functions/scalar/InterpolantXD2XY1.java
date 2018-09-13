package mudstone.java.functions.scalar;

import static java.lang.Math.fma;

import mudstone.java.functions.scalar.ScalarFunctional;

/** A quadratic function from <b>R</b> to <b>R</b> interpolating
 * (x0,y0=f(x0),d0=df(x0)) and (x1,d1=df(x1).
 * The argmin of this parabola is at the zero of its derivative,
 * and is what's usually called the 'secant' step. 
 * <p>
 * An alternative might be to match:
 * (x0,d0=df(x0)), (xmid=(x0+x1)/2,ymid=(f(x0)+f(x1))/2), 
 * (x1,d1=df(x1)). Because this is primarily used for its
 * critical point (the zero of the derivative) the exact y values 
 * don't usually matter.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-14
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

  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    final double z = x-_x2;
    return fma(z,fma(z,_a2,_a1),_a0);  }

  @Override
  public final double slope (final double x) {
    final double z = x-_x2;
    return fma(z,2.0*_a2,_a1);  }

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
      //      _a2 + +"*(x-" + _x2 + ")^2;
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
    // 1st assertion not really necessary
    assert x0 < x1 : "Fail: " + x0 + " < " + x1;
    assert d0 != d1;
    _x2 = x2;
    _a0 = y2;
    final double z0 = x0-x2;
    final double z1 = x1-x2;
    final double dz = z1-z0; // =x1-x0
    final double dd = d1-d0;
    _a1 = (d0*z1-d1*z0)/dz;
    _a2 = 0.5*dd/dz;
    // TODO: epsilon tests here?
    if (0.0<_a2) {
      _xmin = _a1 + x2; }
    else if ((0.0==_a2) && (0.0 < _a1)) {
      _xmin = Double.NEGATIVE_INFINITY; }
    else { // 2 minima, pick one
      _xmin = Double.POSITIVE_INFINITY; } }

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

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

