package mudstone.java.test.functions.scalar;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.DoubleInterval;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization.
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */

public final class Square extends ScalarFunctional {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) { return x*x; }

  @Override
  public final double slope (final double x) { return 2.0*x; }

  @Override
  public final Function tangentAt (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }
  
  @Override
  public final double doubleArgmin (final DoubleInterval support) { 
    return 0.0; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?
  
  private Square () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link Square} test function of the given
   * <code>dimension</code>.
   */

  public static final Square get () { return new Square(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

