package mudstone.java.functions.scalar;

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
 * @version 2018-09-20
 */

public final class InterpolateXY3 implements ModelFactory {

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  private final static QuadraticLagrange
  interpolate (final double x0, 
               final double y0,
               final double x1, 
               final double y1,
               final double x2,
               final double y2) {
    return QuadraticLagrange.make(x0,y0,x1,y1,x2,y2); }

  //--------------------------------------------------------------
  // ModelFactory methods
  //--------------------------------------------------------------

  @Override
  public final Function model (final Function f,
                               final double x0,
                               final double x1,
                               final double x2) {
    return interpolate(
      x0,f.doubleValue(x0),
      x1,f.doubleValue(x1),
      x2,f.doubleValue(x2)); }

  @Override
  public final Function model (final double x0, 
                               final double y0,
                               final double x1, 
                               final double y1,
                               final double x2,
                               final double y2)  {
    return interpolate(x0,y0,x1,y1,x2,y2) ; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private InterpolateXY3 () { super(); }

  public static final ModelFactory 
  make () { return new InterpolateXY3(); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

