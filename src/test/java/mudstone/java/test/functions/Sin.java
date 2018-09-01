package mudstone.java.test.functions;

import mudstone.java.AffineFunctional1d;
import mudstone.java.Function;

//----------------------------------------------------------------
/** Test function for 1d minimization.
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-01
 */

public final class Sin implements Function {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final int domainDimension () { return 1; }

  @Override
  public final int codomainDimension () { return 1; }

  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    return Math.sin(x); }

  //--------------------------------------------------------------

  @Override
  public final double slope (final double x) {
    return Math.cos(x); }
  
  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }
  
  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?
  
  private Sin () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link Sin} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final Sin get () { return new Sin(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

