package mudstone.java.functions.scalar;

import java.util.HashSet;
import java.util.Set;

/** Base for polynomials on <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-06
 */

@SuppressWarnings("unchecked")
public abstract class Polynomial extends ScalarFunctional {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  public abstract int degree ();

  // TODO: move elsewhere
  private static final int countDistinct (final double[] x) {
    final Set unique = new HashSet();
    for (final double xi : x) {
      unique.add(Double.valueOf(xi)); } 
    return unique.size(); }

  public static final boolean validKnots (final double[][] knots,
                                          final int degree) {
    final int nv = countDistinct(knots[0]);
    final int ns = countDistinct(knots[1]);
    return 
      (nv>=1) 
      &&
      ((nv+ns) == degree+1); }

  //  public final boolean validKnots (final double[] x,
  //                                   final KnotType[] flags) {
  //    final Set vKnots = KnotType.distinctValueKnots(x,flags);
  //    final int nv = vKnots.size();
  //    final Set sKnots = KnotType.distinctSlopeKnots(x,flags);
  //    final int ns = sKnots.size();
  //    return 
  //      (nv>=1) 
  //      &&
  //      ((nv+ns) == degree()+1); }


  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  public Polynomial () { }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

