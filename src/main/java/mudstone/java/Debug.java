package mudstone.java;


/** Debugging output.
 *
 * Static methods only; no state.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-07-30
 */

public final class Debug {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------
  /** Hex string representation of the bits implementing the double.
   */

  public static final String hexString (final double x) {
    return 
      Long.toHexString(Double.doubleToLongBits(x))
      .toUpperCase(); }

  //--------------------------------------------------------------

  public static final void printf (final String format,
                                   final Object... args) {
    System.out.printf(format,args); }

  //--------------------------------------------------------------

  public static final void printf (final String format,
                                   final boolean arg) {
    System.out.printf(format,Boolean.valueOf(arg)); }

  //--------------------------------------------------------------

  public static final void printf (final String format,
                                   final int arg) {
    System.out.printf(format,Integer.valueOf(arg)); }

  //--------------------------------------------------------------

  public static final void printf (final String format,
                                   final double arg) {
    System.out.printf(format,Double.valueOf(arg)); }

  //--------------------------------------------------------------

  public static final void printf (final String format,
                                   final double arg0,
                                   final double arg1) {
    System.out.printf(format,
      Double.valueOf(arg0),Double.valueOf(arg1)); }

  //--------------------------------------------------------------
  //  @SuppressWarnings("unused")
  //  private static final void debugPrint  (final String s) {
  //    //System.out.print(s);
  //  }

  //  @SuppressWarnings("unused")
  //  public static final void debugPrintf  (final String format,
  //                                         final Object... args) {
  //    //System.out.printf(format,args);
  //  }

  //  @SuppressWarnings("unused")
  //  private static final void tracePrint (final String s) {
  //    //System.out.print(s);
  //  }

  //  @SuppressWarnings("unused")
  //  public static final void tracePrintf (final String format,
  //                                        final Object... args) {
  //    //System.out.printf(format,args);
  //  }

  //  @SuppressWarnings("unused")
  //  public static final void traceLineSearch (final String format,
  //                                            final Object... args) {
  //    //System.out.printf(format,args);
  //  }

  //--------------------------------------------------------------
  // disable constructor
  //--------------------------------------------------------------

  private Debug () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }


  //--------------------------------------------------------------
}
//--------------------------------------------------------------
