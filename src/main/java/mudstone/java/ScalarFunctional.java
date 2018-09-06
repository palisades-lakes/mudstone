package mudstone.java;

/** Base class for real valued functions on <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-06
 */

public abstract class ScalarFunctional extends Functional {

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

