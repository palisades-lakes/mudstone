package mudstone.java.test.functions;

import mudstone.java.functions.AffineFunctional1d;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization.
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-07
 */

public final class Square extends ScalarFunctional {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) { return x*x; }

  //--------------------------------------------------------------

  @Override
  public final double slope (final double x) { return 2.0*x; }
  
  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }
  
  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?
  
  private Square () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link Square} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final Square get () { return new Square(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

