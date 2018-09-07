package mudstone.java.test.functions;

import mudstone.java.functions.AffineFunctional1d;
import mudstone.java.functions.Function;
import mudstone.java.functions.ScalarFunctional;

//----------------------------------------------------------------
/** Test function for 1d minimization.
 * <p>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-06
 */

public final class SumTwo1d extends ScalarFunctional {

  private final Function _f0;
  private final Function _f1;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) { 
    return _f0.doubleValue(x) + _f1.doubleValue(x); }

  //--------------------------------------------------------------

  @Override
  public final double slope (final double x) { 
    return _f0.slope(x) + _f1.slope(x); }

  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private SumTwo1d (final Function f0,
                    final Function f1) { 
    super(); 
    // assuming immutable...strictly just that don't need to copy.
    _f0 = f0;
    _f1 = f1; }

  //--------------------------------------------------------------
  /** Return a {@link SumTwo1d} test function of the given
   * <code>dimension</code>.
   * Correct gradient is zero vektor; passing in
   * <code>gi</code> allows creating an
   * invalid function for testing.
   */

  public static final SumTwo1d get (final Function f0,
                                    final Function f1)  { 
    return new SumTwo1d(f0,f1); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

