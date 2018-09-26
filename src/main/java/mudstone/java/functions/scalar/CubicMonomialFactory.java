package mudstone.java.functions.scalar;

import static java.lang.Math.fma;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar cubic functions 
 * in simple monomial form, interpolating the values and slopes
 * of the supplied function at -1,0,1.
 * Primarily for testing, to compare accuracy and speed with
 * other quadratic implementations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class CubicMonomialFactory implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------
  // match value at -1,0,1 regardless of knots.

  @Override
  public final Function model (final Function f,
                               final double[] knots)  {
    // TODO: other formulas possible --- accuracy?
    final double ym1 = f.doubleValue(-1.0);
    final double y0 = f.doubleValue(0.0);
    final double yp1 = f.doubleValue(1.0);
    final double d0 = f.slope(0.0);
    return CubicMonomial.make(
      y0,
      d0,
      0.5*fma(-2.0,y0,ym1+yp1),
      0.5*fma(-2.0,d0,yp1-ym1)); }

  // match value at -1,0,1 regardless of knots.
  @Override
  public final double[] matchValueAt (final double[] knots) {
    return new double[] { -1.0, 0.0, 1.0 }; }

  // match slope at -1,0,1 regardless of knots.
  @Override
  public double[] matchSlopeAt (final double[] knots) {
    return new double[] { 0.0 }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private CubicMonomialFactory () { super(); }

  public static final ModelFactory 
  get () { return new CubicMonomialFactory(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

