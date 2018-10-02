package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar cubic functions 
 * interpolating
 * <code>(x0,y0=f(x0)), (x1,y1=f(x1), (x2,y2=f(x2)) (x3,y3=f(x3))</code>.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-29
 */

public final class CubicLagrangeFactory implements ModelFactory {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  private final static CubicLagrange
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2,
               final double x3,
               final double y3) {
    return CubicLagrange.make(x0,y0,x1,y1,x2,y2,x3,y3); }

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

  private CubicLagrangeFactory () { super(); }

  public static final ModelFactory 
  get () { return new CubicLagrangeFactory(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

