package mudstone.java.functions.scalar;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.functions.Function;

/** Interface for factories that create 
 * approximating/interpolating model functions
 * from values and derivatives of a scalar function at a few
 * domain values.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-20
 */

public interface ModelFactory {

  // TODO: simplify this interface so only 1 or 2 methods are 
  // needed. Issue is supporting multiple arities --- could use
  // double[] or DoubleList or DoubleDeque...
  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------
  /** Return a model function approximating <code>f</code> in some 
   * sense, using values and derivatives at <code>x0,x1,x2</code>
   */

  @SuppressWarnings("boxing")
  public default Function model (final Function f,
                                 final double x0,
                                 final double x1) {
    throw Exceptions.unsupportedOperation(
      this,"model",f,x0,x1); }

  /** Return a model function approximating <code>f</code> in some 
   * sense, using values and derivatives at <code>x0,x1,x2</code>
   */

  @SuppressWarnings("boxing")
  public default Function model (final Function f,
                                 final double x0,
                                 final double x1,
                                 final double x2) {
    throw Exceptions.unsupportedOperation(
      this,"model",f,x0,x1,x2); }

  /** Return a model function interpolating/approximating
   * a set of x,y, and slope values given in the args.
   * The interpretation of the args depends on the implementing
   * class.
   */

  @SuppressWarnings("boxing")
  public default Function model (final double z0,
                                 final double z1,
                                 final double z2,
                                 final double z3) {
    throw Exceptions.unsupportedOperation(
      this,"model",z0,z1,z2,z3); }

  /** Return a model function interpolating/approximating
   * a set of x,y, and slope values given in the args.
   * The interpretation of the args depends on the implementing
   * class.
   */

  @SuppressWarnings("boxing")
  public default Function model (final double z0,
                                 final double z1,
                                 final double z2,
                                 final double z3,
                                 final double z4,
                                 final double z5) {
    throw Exceptions.unsupportedOperation(
      this,"model",z0,z1,z2,z3,z4,z5); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

