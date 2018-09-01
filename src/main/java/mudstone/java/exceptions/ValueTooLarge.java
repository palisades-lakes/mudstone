package mudstone.java.exceptions;

/** Thrown when the change in function value <= feps*|_f|
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-07-26
 */
public final class ValueTooLarge extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private ValueTooLarge (final String msg,
                         final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** change in function value <= feps*|_f|.
   */

  @SuppressWarnings("unused")
  public static final ValueTooLarge
  make (final boolean negDiag,
        final Object context) {
    final StringBuilder b = new StringBuilder();
    b.append("Too little value improvement\n");
    if (negDiag) {
      b.append("value of eta2 may be too small.\n"); }
    //b.append("\n");
    //b.append(context.toString());
    
    return new ValueTooLarge(b.toString(),negDiag); }

  public static final ValueTooLarge
  make (final Object context) { return make(false,context); }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
