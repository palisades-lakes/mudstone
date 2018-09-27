package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * in simple monomial form, interpolating the values
 * of the supplied function at the 3 knots.
 * Primarily for testing, to compare accuracy and speed with
 * other quadratic implementations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-26
 */

public final class QuadraticMonomialFactory implements ModelFactory {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  private final static QuadraticMonomial
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2) {
    return 
      QuadraticMonomial.make(
        QuadraticUtils.interpolatingMonomialCoefficients(
          x0,y0,x1,y1,x2,y2)); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] knots)  {
    return interpolate(
      knots[0],f.doubleValue(knots[0]),
      knots[1],f.doubleValue(knots[1]),
      knots[2],f.doubleValue(knots[2])); }

//  @Override
//  public final Function model (final double[] z)  {
//    return interpolate(
//      z[0],z[1],
//      z[2],z[3],
//      z[4],z[5]) ; }

  @Override
  public final double[] matchValueAt (final double[] knots) {
    return new double[] { knots[0], knots[1], knots[2], }; }

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

