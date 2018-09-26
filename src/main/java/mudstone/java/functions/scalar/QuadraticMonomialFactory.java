package mudstone.java.functions.scalar;

import static java.lang.Math.fma;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * in simple monomial form, interpolating the values
 * of the supplied function at -1,0,1.
 * Primarily for testing, to compare accuracy and speed with
 * other quadratic implementations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class QuadraticMonomialFactory implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------
  // match value at -1,0,1 regardless of knots.

  @Override
  public final Function model (final Function f,
                               final double[] knots)  {
    final double ym1 = f.doubleValue(-1.0);
    final double y0 = f.doubleValue(0.0);
    final double yp1 = f.doubleValue(1.0);
    return QuadraticMonomial.make(
      y0,
      0.5*(yp1-ym1),
      0.5*fma(-2.0,y0,ym1+yp1)); }

  // match value at -1,0,1 regardless of knots.
  @Override
  public final double[] matchValueAt (final double[] knots) {
    return new double[] { -1.0, 0.0, 1.0 }; }

  // doesn't match slope anywhere
  @Override
  public double[] matchSlopeAt (final double[] knots) {
    return new double[] { }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private QuadraticMonomialFactory () { super(); }

  public static final ModelFactory 
  get () { return new QuadraticMonomialFactory(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

