package mudstone.java.functions.scalar;

/** Two strictly increasing <code>double</code>s. Immutable.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-25
 */
public final class DoubleInterval  {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _lower;
  public final double lower () { return _lower; }

  private final double _upper;
  public final double upper () { return _upper; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () {
    int c = 17;

    final long l0  = Double.doubleToLongBits(_lower);
    final int c0 = (int) (l0 ^ (l0 >>> 32));
    c = (31*c) + c0;

    final long l1  = Double.doubleToLongBits(_upper);
    final int c1 = (int) (l1 ^ (l1 >>> 32));
    c = (31*c) + c1;

    return c; }

  @Override
  public final boolean equals (final Object that) {
    if (! (that instanceof DoubleInterval)) { return false; }
    final DoubleInterval di = (DoubleInterval) that;
    return
      (_lower == di._lower) && (_upper == di._upper); }

  @Override
  public final String toString () {
    return
      String.format(
        "DoubleInterval(%E,%E)",
        Double.valueOf(_lower), 
        Double.valueOf(_upper)); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private DoubleInterval (final double x0,
                          final double x1) {
    super(); 
    assert (x0 < x1);
    _lower = x0; _upper = x1; }

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
