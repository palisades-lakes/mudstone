package mudstone.java.exceptions;

/** Thrown when functional value or derivative is not finite.
 * Probably indicates an implementation error.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-02
 */
public final class NotFinite extends AbnormalExit  {

  private static final long serialVersionUID = 1L;

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private NotFinite (final String msg) { super(msg,false);  }

  //--------------------------------------------------------------
  /** Non-finite functional value or directional derivative at<br>
   *  <code>position = step.x()*direction + origin</code>.
   */

  @SuppressWarnings("unused")
  public static final NotFinite
  make (final Object context,
        final double x,
        final double y,
        final double d) {
    final StringBuilder b = new StringBuilder();
    b.append("Nonfinite value or directional derivative: %s\n");
    b.append("x=" + x + "\n");
    b.append("f(x)=" + y + "\n");
    b.append("df(x)=" + d + "\n");
    //b.append(context.toString());

    return new NotFinite(b.toString()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
