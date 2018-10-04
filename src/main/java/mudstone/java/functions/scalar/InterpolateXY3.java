package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * interpolating
 * <code>(x0,y0=f(x0)), (x1,y1=f(x1), (x2,d2=df(x2))</code>.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-03
 */

public final class InterpolateXY3 implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  private final static ScalarFunctional
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2) {
    return QuadraticLagrange.interpolateXY(x0,y0,x1,y1,x2,y2); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] x) {
    return interpolate(
      x[0],f.doubleValue(x[0]),
      x[1],f.doubleValue(x[1]),
      x[2],f.doubleValue(x[2])); }

  @Override
  public final Function model (final double[] z)  {
    return interpolate(z[0],z[1],z[2],z[3],z[4],z[5]) ; }

  @Override
  public final double[] matchValueAt (final double[] x) {
    return new double[] { x[0], x[1], x[2], }; }

  @Override
  public double[] matchSlopeAt (double[] x) {
    return new double[] { }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private InterpolateXY3 () { super(); }

  public static final ModelFactory 
  get () { return new InterpolateXY3(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

