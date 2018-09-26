package mudstone.java.functions.scalar;

import static java.lang.Math.fma;

import mudstone.java.functions.Function;

/** A ModelFactory returning scalar quadratic functions 
 * interpolating
 * <code>(x0,y0=f(x0)), (x1,y1=f(x1), (x2,d2=df(x2))</code>.
 * <p>
 * This will primarily be used in the context of golden ratio
 * expansion/contraction steps in scalar function
 * minimization (almost always in a line search).
 * In that context, the argmin of the model function is already 
 * known, and passed to the constructor as <code>(x2,d2=0)</code>. 
 * <p>
 * Creating an (unnecessary) explicit model function puts golden 
 * steps into a common framework with secant, lagrange, and 
 * hermite steps. Although the model function isn't necessary in
 * any of those cases, thinking about which model function is a
 * better approximation to the function to be minimized gives us
 * some idea about which step might work better when.
 * At least, it makes it easier to generate figures comparing
 * the true function to its approximations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class InterpolateXY2XD1 implements ModelFactory {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  private final static QuadraticMonomialShifted 
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double d2) {
    assert x0 != x1;
    // TODO: use BigFraction to compute monomial coefficients?
    final double u0 = x0-x2;
    final double u02 = u0*u0;
    final double u1 = x1-x2;
    final double u12 = u1*u1;
    final double du = u1-u0;
    final double du2 = u12-u02;
    final double a0 = ((fma(-d2,u0,y0)*u12)-(fma(-d2,u1,y1)*u02))/du2;
    final double a1 = d2;
    final double a2 = fma(-d2,du,y1-y0)/du2; 
    return QuadraticMonomialShifted.make(a0,a1,a2,x2); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double[] x) {
    return interpolate(
      x[0],f.doubleValue(x[0]),
      x[1],f.doubleValue(x[1]),
      x[2],f.slope(x[2])); }

  @Override
  public final Function model (final double[] z)  {
    return interpolate(z[0],z[1],z[2],z[3],z[4],z[5]) ; }

  @Override
  public final double[] matchValueAt (final double[] x) {
    return new double[] { x[0], x[1], }; }

  @Override
  public double[] matchSlopeAt (double[] x) {
    return new double[] { x[2], }; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private InterpolateXY2XD1 () { super(); }

  public static final ModelFactory 
  get () { return new InterpolateXY2XD1(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

