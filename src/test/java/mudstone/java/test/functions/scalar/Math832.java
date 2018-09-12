package mudstone.java.test.functions.scalar;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization (see 
 * org.apache.commons.math3.optim.univariate.BrentOptimizerTest).
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-07
 */

public final class Math832 extends ScalarFunctional {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    final double sqrtX = Math.sqrt(x);
    final double a = 1.0e2 * sqrtX;
    final double b = 1.0e6 / x;
    final double c = 1.0e4 / sqrtX;
    return a + b + c; }

  //--------------------------------------------------------------
  
  @Override
  public final double slope (final double x) {
    final double sqrtX = Math.sqrt(x);
    final double da = 50.0 / sqrtX;
    final double db = -1.0e6 / x*x;
    final double dc = -5.0e3 / x*sqrtX;
    return da + db + dc; }

  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private Math832 () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link Math832} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final Math832 get () { return new Math832(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
