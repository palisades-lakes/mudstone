package mudstone.java.test.functions;

import java.util.Arrays;

import mudstone.java.AffineFunctional1d;
import mudstone.java.Doubles;
import mudstone.java.Function;

//----------------------------------------------------------------
/** Test function for 1d minimization (see 
 * org.apache.commons.math3.optim.univariate.BrentOptimizerTest).
 * <p>
 * Value is <code>value[i]</code> for <code>knot[i]<=x<knot[i+1]</code>.
 * Undefined (NaN) before <code>knot[0]</code>, which might be
 * <code>Double.NEGATIVE_INFINITY</code>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-01
 */

public final class RightContinuousStepFunction implements Function {

  private final double[] _knot;
  private final double[] _value;
  
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
    final int i = Arrays.binarySearch(_knot,x);
    if (i < -1) { return _value[-i-2]; } 
    if (i >= 0) { return _value[i]; } 
    return Double.NaN; }

  //--------------------------------------------------------------
  // TODO: undefined (NaN) at knots?
  
  @Override
  public final double slope (final double x) {
    if (x<_knot[0]) { return Double.NaN; }
    return 0.0; }

  //--------------------------------------------------------------

  @Override
  public final Function tangent (final double x) {
    return AffineFunctional1d.make(slope(x),doubleValue(x)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private RightContinuousStepFunction (final double[] knot,
                                       final double[] value) { 
    super(); 
    _knot = knot;
    _value = value; }

  //--------------------------------------------------------------

  public static final RightContinuousStepFunction 
  get (final double[] knot,
       final double[] value) { 
    assert knot.length == value.length;
    assert Doubles.strictlyIncreasing(knot);
    
    return new RightContinuousStepFunction(
      knot.clone(),value.clone()); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

