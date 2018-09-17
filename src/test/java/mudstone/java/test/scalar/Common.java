package mudstone.java.test.scalar;

import static java.lang.StrictMath.*;
import org.junit.jupiter.api.Assertions;

import mudstone.java.functions.Function;

//----------------------------------------------------------------
/** Shared tests for scalar functions
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-16
 */

strictfp
public final class Common {

  //--------------------------------------------------------------
  /** Check that the value of <code>f</code> actually a local
   * minimum, that is, it increases if we move a small amount
   * (<code>step*sqrt(ulp(1+abs(xmin)))</code>)s away from 
   * <code>f.doubleArgmin()</code>.
   * Only require this if <code>f.doubleArgmin()</code> is finite.
   */
  public static final void checkArgmin (final Function f,
                                        final double step) {

    final double xmin = f.doubleArgmin();
    if (Double.isFinite(xmin)) {
      final double ymin = f.doubleValue(xmin);
      //final double delta = ulps*ulp(1.0+abs(xmin));
      final double delta = step*sqrt(ulp(1.0+abs(xmin)));
      //System.out.println(xmin + " + " + delta);
      Assertions.assertTrue(
        ymin < f.doubleValue(xmin-delta),
        () -> { 
          return 
            "\n" + f.getClass().getSimpleName() + "\n" +
            ymin + ">=" + f.doubleValue(xmin-delta) + "\n" +
            " at " + xmin + " : " + (xmin-delta) + "\n";  });
      Assertions.assertTrue(
        ymin < f.doubleValue(xmin+delta),
        () -> { 
          return 
          "\n" + f.getClass().getSimpleName() + "\n" +
          ymin + ">=" + f.doubleValue(xmin+delta) + "\n" +
          " at " + xmin + " : " + (xmin+delta) + "\n"; }); } }

  //--------------------------------------------------------------
  // utility class; disable constructor
  //--------------------------------------------------------------

  private Common () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }
  //--------------------------------------------------------------
}
//--------------------------------------------------------------
