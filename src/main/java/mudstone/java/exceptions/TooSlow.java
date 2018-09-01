package mudstone.java.exceptions;

/** Thrown when there are too many iterations without significant
 * improvement in the value or the gradient.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-07-31
 */
public final class TooSlow extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private TooSlow (final String msg,
                   final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Too many iterations without significant
   * improvement in the value or the gradient.
   */

  @SuppressWarnings("unused")
  public static final TooSlow
  make (final int nslow,
        final boolean negDiag,
        final Object context) {
    final StringBuilder b = new StringBuilder();
    b.append(
      String.format(
        "Too many iterations without improvement: %d\n",
        Integer.valueOf(nslow)));
//    b.append("\n");
//    b.append(context.toString());
    return new TooSlow(b.toString(),negDiag); }

  public static final TooSlow
  make (final int nslow,
        final Object context) {
    return make(nslow,false,context); }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
