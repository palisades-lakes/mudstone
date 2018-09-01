package mudstone.java.exceptions;

/** Thrown when the interval is expanded too many times
 * without bracketing a minimum, because the derivative at
 * the right boundary remain negative.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-04
 */
public final class NegativeSlope extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private NegativeSlope (final String msg,
                         final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Too many interval expansions without bracketing a minimum.
   */

  public static final NegativeSlope
  make (final Object lineSearch,
        final boolean negDiag,
        final Object functional,
        final Object step) {
    final StringBuilder b = new StringBuilder();
    b.append("UNable to bracket a minimum:\n");
    b.append("\n");
    b.append(lineSearch.toString());
    b.append("\n");
    b.append(String.format("last step: %s\n",step));
    b.append(functional.toString());
    return new NegativeSlope(b.toString(),negDiag); }

  public static final NegativeSlope
  make (final Object lineSearch,
        final Object functional,
        final Object step) {
    final StringBuilder b = new StringBuilder();
    b.append("UNable to bracket a minimum:\n");
    b.append("\n");
    b.append(lineSearch.toString());
    b.append("\n");
    b.append(String.format("last step: %s\n",step));
    b.append(functional.toString());
    return new NegativeSlope(b.toString(),false); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
