package mudstone.java.test.functions.scalar;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization (see 
 * org.apache.commons.math3.optim.univariate.BrentOptimizerTest).
 * <p>
 * Negative of commons math3 quintic test function.
 * Zeros at 0, +-0.5 and +-1.
 * Local minimum at 0.27195613.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class Quintic extends ScalarFunctional {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    return -(x-1.0)*(x-0.5)*x*(x+0.5)*(x+1.0); }

  // TODO: accurate polynomial evaluation?

  @Override
  public final double slope (final double x) {
    return 
      -(((x-0.5)*x*(x+0.5)*(x+1.0)) +
      ((x-1.0)*x*(x+0.5)*(x+1.0)) +
      ((x-1.0)*(x-0.5)*(x+0.5)*(x+1.0)) +
      ((x-1.0)*(x-0.5)*x*(x+1.0)) +
      ((x-1.0)*(x-0.5)*x*(x+0.5))); }

  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  @Override
  public final double doubleArgmin () {
    return 0.27195613; }
  
  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private Quintic () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link Quintic} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final Quintic get () { return new Quintic(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

