package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar cubic functions * matching
 * <code>(x0,y0=f(x0),d0=df(x0))</code>
 * and <code>(x1,y1=f(x1),d1=df(x1))</code>.
 * <p>
 * This is almost certainly overkill. It's intended to be used to 
 * calculate hermite step in scalar function minimization
 * (almost always in a line search), so there's a lot of mechanism
 * that's unlikely to ever be used. My excuse is that, by making 
 * the * various model functions explicit, the ideas underlying 
 * various 1d minimization algorithms should be more clearly 
 * expressed in the code.
 * The ideal here is that the code should be as easy to understand
 * as the descriptions in typical textbooks.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-21
 */

public final class InterpolateXYD2 implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  private final static CubicHermite
  interpolate (final double x0, 
               final double y0,
               final double d0, 
               final double x1,
               final double y1,
               final double d1) {
    assert x0 != x1;
    return CubicHermite.make(x0,y0,d0,x1,y1,d1); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] x) {
    return interpolate(
      x[0],f.doubleValue(x[0]),f.slope(x[0]),
      x[1],f.doubleValue(x[1]),f.slope(x[1])); }

  @Override
  public final Function model (final double[] z)  {
    return interpolate(z[0],z[1],z[2],z[3],z[4],z[5]) ; }

  @Override
  public final double[] matchValueAt (final double[] x) {
    return new double[] { x[0], x[1], }; }

  @Override
  public double[] matchSlopeAt (double[] x) {
    return new double[] { x[0], x[1], }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private InterpolateXYD2 () { super(); }

  public static final ModelFactory 
  get () { return new InterpolateXYD2(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

