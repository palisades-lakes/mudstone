package mudstone.java.functions.scalar;

/** Half-open interval in <code>double</code>s.
 * <p>
 * Immutable.
 * <p>
 * TODO: how to handle closed [-infinity,infinity]?
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-28
 */
public final class HalfOpenInterval extends Interval {

  //--------------------------------------------------------------
  // Domain methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final double x) {
    return (lower() <= x) && (x < upper()); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final boolean equals (final Object that) {
    if (! (that instanceof HalfOpenInterval)) { return false; }
    final HalfOpenInterval di = (HalfOpenInterval) that;
    return
      (lower() == di.lower()) && (upper() == di.upper()); }

  @Override
  public final String toString () {
    return "[" + lower() + "," + upper() + ")"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private HalfOpenInterval (final double x0,
                            final double x1) {
    super(x0,x1); }

  /** return <code>[min(x0,x1),max(x0,x1))</code>. */

  public static final HalfOpenInterval make (final double x0,
                                             final double x1) {
    if (x0 < x1) { 
      assert x0 < x1;
      return new HalfOpenInterval(x0,x1); }
    assert x1 < x0;
    return new HalfOpenInterval(x1,x0); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
