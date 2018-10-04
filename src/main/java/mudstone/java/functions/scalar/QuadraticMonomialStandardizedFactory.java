package mudstone.java.functions.scalar;

import static java.lang.Math.*;

import org.apache.commons.math3.fraction.BigFraction;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * in simple monomial form, interpolating the values
 * of the supplied function at the 3 knots.
 * Primarily for testing, to compare accuracy and speed with
 * other quadratic implementations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-04
 */

public final class QuadraticMonomialStandardizedFactory 
implements ModelFactory {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  private static final double qfma (final BigFraction a,
                                    final double z,
                                    final BigFraction b) {
    return a.multiply(new BigFraction(z)).add(b).doubleValue(); }

  private final static ScalarFunctional
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2) {

    // TODO: better to use centered (-1,1) standardization?

    // transform x range to (0,1)
    // final double xmin = min(x0,min(x1,x2));
    // final double xmax = max(x0,max(x1,x2));
    // final double ax = xmax-xmin);
    // final double bx = -ax*xmin;

    final BigFraction xmin = new BigFraction(min(x0,min(x1,x2)));
    final BigFraction xmax = new BigFraction(max(x0,max(x1,x2)));
    final BigFraction ax = xmax.subtract(xmin).reciprocal();
    final BigFraction bx = ax.negate().multiply(xmin);

    // transform y range to (0,1)
    // final double ymin = min(y0,min(y1,y2));
    // final double ymax = max(y0,max(y1,y2));
    // final double ay = 1.0/(ymax-ymin);
    // final double by = -ay*ymin;

    final BigFraction ymin = new BigFraction(min(y0,min(y1,y2)));
    final BigFraction ymax = new BigFraction(max(y0,max(y1,y2)));
    final BigFraction ay;
    final BigFraction by;
    if (ymin.equals(ymax) ) {
      ay = BigFraction.ZERO;
      by = ymin; }
    else {
      ay = ymax.subtract(ymin).reciprocal();
      by = ay.negate().multiply(ymin); }

    // monomial coefficients for interpolating points 
    // (0,1) -> (0,1)
    final double[] a = 
      QuadraticUtils.interpolatingMonomialCoefficients(
        qfma(ax,x0,bx),qfma(ay,y0,by),
        qfma(ax,x1,bx),qfma(ay,y1,by),
        qfma(ax,x2,bx),qfma(ay,y2,by));

    // need y transform from (0,1) to y range
    return QuadraticMonomialStandardized.make(
      a[0],a[1],a[2],
      ax.doubleValue(),bx.doubleValue(),
      ymax.subtract(ymin).doubleValue(),ymin.doubleValue()); }

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

  private QuadraticMonomialStandardizedFactory () { super(); }

  public static final ModelFactory 
  get () { return new QuadraticMonomialStandardizedFactory(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

