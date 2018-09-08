package mudstone.java.test.functions.scalar;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.AffineFunctional1d;
import mudstone.java.functions.scalar.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization (see 
 * org.apache.commons.math3.optim.univariate.BracketFinderTest).
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-07
 */

public final class SemiCubic extends ScalarFunctional {

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    if (x < -2.0) { return doubleValue(-2.0); }
    return (x - 1.0) * (x + 2.0) * (x + 3.0); }

  //--------------------------------------------------------------
  // TODO: accurate polynomial evaluation?

  @Override
  public final double slope (final double x) {
    if (x < -2.0) { return 0.0; }
    return 
      ((x + 2.0) * (x + 3.0)) + 
      ((x - 1.0) * (x + 3.0)) + 
      ((x - 1.0) * (x + 2.0)); }

  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: singleton?

  private SemiCubic () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link SemiCubic} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final SemiCubic get () { return new SemiCubic(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

