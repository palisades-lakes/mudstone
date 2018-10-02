package mudstone.java.functions.scalar;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.functions.Function;

/** Interface for factories that create 
 * approximating/interpolating model functions
 * from values and derivatives of a scalar function at a few
 * domain values.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public interface ModelFactory {

  /** Return a model function approximating <code>f</code> in some 
   * sense, using values and derivatives at the 
   * domain values in <code>x</code>.
   */

  public default Function model (final Function f,
                                 final double[] knots) {
    throw Exceptions.unsupportedOperation(this,"model",f,knots); }

  /** Return a model function interpolating/approximating
   * a set of x,y, and slope values given in the <code>z</code>.
   * The interpretation of the args depends on the implementing
   * class.
   */

  public default Function model (final double[] knots) {
    throw Exceptions.unsupportedOperation(this,"model",knots); }

  //--------------------------------------------------------------
  // for testing
  // TODO: better approach to this...
  // could have a wrapper model function that retains the sample
  // points.
  //--------------------------------------------------------------
  /** A model function generated by <code>this.model(f,x)</code>
   * should match <code>f.doubleValue</code> at the elements
   * of the returned array, which might be empty.
   */
  public double[] matchValueAt (final double[] knots);

  /** A model function generated by <code>this.model(f,x)</code>
   * should match <code>f.slope</code> at the elements
   * of the returned array, which might be empty.
   */
  public double[] matchSlopeAt (final double[] knots);
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
