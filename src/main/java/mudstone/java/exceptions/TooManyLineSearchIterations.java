package mudstone.java.exceptions;

/** Thrown when there are too many iterations in the line search,
 * without satisfying the (approximate) Wolfe conditions.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-04
 */
public final class TooManyLineSearchIterations
extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private TooManyLineSearchIterations (final String msg,
                                       final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Too many line search iterations without satisfying wolfe
   * conditions.
   */

  public static final TooManyLineSearchIterations
  make (final Object lineSearch,
        final boolean negDiag,
        final Object functional,
        final Object step) {
    final StringBuilder b = new StringBuilder();
    b.append("Line search failed too many iterations:\n");
    b.append("\n");
    b.append(lineSearch.toString());
    b.append("\n");
    b.append(String.format("last step: %s\n",step));
    b.append(functional.toString());
    return new TooManyLineSearchIterations(b.toString(),negDiag); }

  public static final TooManyLineSearchIterations
  make (final Object lineSearch,
        final Object functional,
        final Object step) {
    return make(lineSearch,false,functional,step); }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
