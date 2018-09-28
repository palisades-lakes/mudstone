package mudstone.java.functions.scalar;

import static java.lang.Math.nextUp;

/** Closed interval in <code>double</code>s.
 * <p>
 * Immutable.
 * <p>
 * TODO: how to handle closed [-infinity,infinity]?
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-28
 */
public final class ClosedInterval extends Interval {

  //--------------------------------------------------------------
  // Domain methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final double x) {
    return (lower() <= x) && (x <= upper()); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final boolean equals (final Object that) {
    if (! (that instanceof ClosedInterval)) { return false; }
    final ClosedInterval di = (ClosedInterval) that;
    return
      (lower() == di.lower()) && (upper() == di.upper()); }

  @Override
  public final String toString () {
    return "[" + lower() + "," + upper() + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private ClosedInterval (final double x0,
                          final double x1) {
    super(x0,x1); }

  /** return <code>[min(x0,x1),max(x0,x1)]</code>. 
   * <code>max(x0,x1)</code> must be finite.
   */

  public static final ClosedInterval make (final double x0,
                                           final double x1) {
    final double lower;
    final double upper;
    if (x0 <= x1) { lower = x0; upper = x1; }
    else { lower = x1; upper = x0; }
    return new ClosedInterval(lower,nextUp(upper)); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
