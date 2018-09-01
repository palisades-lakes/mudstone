package mudstone.java.exceptions;

/** Thrown when there's an abnormal increase in the function
 * value between major iterations, probably indicating different
 * parameter values are needed..
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-07-26
 */
public final class FunctionIncreases extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private FunctionIncreases (final String msg,
                             final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Search direction is not a descent direction,
   * probably indicating an error in the functional evaluation.
   */

  @SuppressWarnings("unused")
  public static final FunctionIncreases
  make (final boolean negDiag,
        final Object context) {
    final StringBuilder b = new StringBuilder();
//    b.append("\n");
//    b.append(m.toString());
    return new FunctionIncreases(b.toString(),negDiag); }

  public static final FunctionIncreases
  make (final Object context) { return make(false,context); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
