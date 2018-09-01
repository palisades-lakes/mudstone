package mudstone.java.exceptions;

/** Thrown when there are too many iterations in the line search,
 * without satisfying the (approximate) Wolfe conditions.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-04
 */
public final class WolfeNeverSatisfied extends AbnormalExit {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private WolfeNeverSatisfied (final String msg,
                               final boolean negDiag) {
    super(msg,negDiag); }

  //--------------------------------------------------------------
  /** Too many line search iterations without satisfying wolfe
   * conditions.
   */

  public static final WolfeNeverSatisfied
  make (final Object lineSearch,
        final boolean negDiag,
        final Object functional) {
    final StringBuilder b = new StringBuilder();
    b.append("Line search failed too many iterations:\n");
    b.append("\n");
    b.append(lineSearch.toString());
    b.append("\n");
    b.append(functional.toString());
    return new WolfeNeverSatisfied(b.toString(),negDiag); }

  public static final WolfeNeverSatisfied
  make (final Object lineSearch,
        final Object functional) {
    return make(lineSearch,false,functional); }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
