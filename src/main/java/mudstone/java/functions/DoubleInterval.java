package mudstone.java.functions;

/** Two strictly increasing <code>double</code>s. Immutable.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-02
 */
public final class DoubleInterval  {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _x0;
  public final double x0 () { return _x0; }

  private final double _x1;
  public final double x1 () { return _x1; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () {
    int c = 17;

    final long l0  = Double.doubleToLongBits(_x0);
    final int c0 = (int) (l0 ^ (l0 >>> 32));
    c = (31*c) + c0;

    final long l1  = Double.doubleToLongBits(_x1);
    final int c1 = (int) (l1 ^ (l1 >>> 32));
    c = (31*c) + c1;

    return c; }

  @Override
  public final boolean equals (final Object that) {
    if (! (that instanceof DoubleInterval)) { return false; }
    final DoubleInterval di = (DoubleInterval) that;
    return
      (_x0 == di._x0) && (_x1 == di._x1); }

  @Override
  public final String toString () {
    return
      String.format(
        "DoubleInterval(%E,%E)",
        Double.valueOf(_x0), 
        Double.valueOf(_x1)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private DoubleInterval (final double x0,
                          final double x1) {
    super(); 
    assert (x0 < x1);
    _x0 = x0; _x1 = x1; }

  /** <code>a</code>, <code>b>/code>, and <code>c</code> must be 
   * distinct. 
   */
  public static final DoubleInterval make (final double x0,
                                           final double x1) {
    assert (x0 != x1);

    if (x0 < x1) { return new DoubleInterval(x0,x1); }
    return new DoubleInterval(x1,x0); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
