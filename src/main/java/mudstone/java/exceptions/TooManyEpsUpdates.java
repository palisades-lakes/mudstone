package mudstone.java.exceptions;

/** Thrown when there are too many eps updates in cont ract().
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-04
 */

public final class TooManyEpsUpdates extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private TooManyEpsUpdates (final String msg,
                             final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Non-finite functional value or directional derivative at<br>
   *  <code>position = step.x()*direction + origin</code>.
   */

  public static final TooManyEpsUpdates
  make (final Object lineSearch,
        final boolean negDiag,
        final Object lf,
        final Object step) {
    final StringBuilder b = new StringBuilder();
    b.append(
      "Line search failed due to excessive updating of eps:\n");
    b.append("\n");
    b.append(lineSearch.toString());
    b.append("\n");
    b.append(String.format("last step: %s\n",step));
    b.append(lf.toString());
    return new TooManyEpsUpdates(b.toString(),negDiag); }

  public static final TooManyEpsUpdates
  make (final Object lineSearch,
        final Object lf,
        final Object step) {
    return make(lineSearch,false,lf,step); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
