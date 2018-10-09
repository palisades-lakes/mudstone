package mudstone.java.functions.scalar;

import mudstone.java.functions.Dn;
import mudstone.java.functions.Domain;
import mudstone.java.functions.Functional;

/** Base class for real valued functions on <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-09
 */

public abstract class ScalarFunctional extends Functional {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  /** Return something informative and short that can be used as
   * a component in filenames. Default method is usually not
   * informative enough.
   */
  
  public String safeName () {
    return getClass().getSimpleName(); }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final Domain domain () { return Dn.D1; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  public ScalarFunctional () { }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

