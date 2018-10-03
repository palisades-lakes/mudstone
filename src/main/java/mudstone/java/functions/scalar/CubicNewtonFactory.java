package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * interpolating
 * <code>(x0,y0=f(x0)), (x1,y1=f(x1), (x2,d2=df(x2))</code>.
 * <p>
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-03
 */

public final class CubicNewtonFactory implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  private final static ScalarFunctional
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2,
               final double x3,
               final double y3) {
    return CubicNewton.make(x0,y0,x1,y1,x2,y2,x3,y3); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] knots) {
    return interpolate(
      knots[0],f.doubleValue(knots[0]),
      knots[1],f.doubleValue(knots[1]),
      knots[2],f.doubleValue(knots[2]),
      knots[3],f.doubleValue(knots[3])); }

  @Override
  public final double[] matchValueAt (final double[] knots) {
    return new double[] 
      { knots[0], knots[1], knots[2], knots[3], }; }

  @Override
  public double[] matchSlopeAt (final double[] knots) {
    return new double[] { }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private CubicNewtonFactory () { super(); }

  public static final ModelFactory 
  get () { return new CubicNewtonFactory(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

