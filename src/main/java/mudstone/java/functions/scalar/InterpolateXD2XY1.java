package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * interpolating
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
 * @version 2018-10-04
 */

public final class InterpolateXD2XY1 implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  private final static ScalarFunctional 
  interpolate (final double x0, 
               final double d0,
               final double x1, 
               final double d1,
               final double x2,
               final double y2) {
    assert x0 != x1;
    final double a0 = y2;
    final double z0 = x0-x2;
    final double z1 = x1-x2;
    final double dz = z1-z0; // =x1-x0
    final double dd = d1-d0;
    final double a1 = (d0*z1-d1*z0)/dz;
    final double a2 = 0.5*dd/dz;
    return QuadraticMonomialShifted.make(a0,a1,a2,x2); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] x) {
    return interpolate(
      x[0],f.slope(x[0]),
      x[1],f.slope(x[1]),
      x[2],f.doubleValue(x[2])); }

  @Override
  public final double[] matchValueAt (final double[] x) {
    return new double[] { x[2], }; }

  @Override
  public double[] matchSlopeAt (double[] x) {
    return new double[] { x[0], x[1], }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private InterpolateXD2XY1 () { super(); }

  public static final ModelFactory 
  get () { return new InterpolateXD2XY1(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

