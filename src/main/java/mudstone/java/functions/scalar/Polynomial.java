package mudstone.java.functions.scalar;

import java.util.Set;

/** Base for polynomials on <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-05
 */

public abstract class Polynomial extends ScalarFunctional {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  public abstract int degree ();
  
  public final boolean validKnots (final double[] x,
                                   final KnotType[] flags) {
    final Set vKnots = KnotType.distinctValueKnots(x,flags);
    final int nv = vKnots.size();
    final Set sKnots = KnotType.distinctSlopeKnots(x,flags);
    final int ns = sKnots.size();
    return 
      (nv>=1) 
      &&
      ((nv+ns) == degree()+1); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  public Polynomial () { }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

