package mudstone.java.functions.scalar;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.functions.Dn;
import mudstone.java.functions.Domain;
import mudstone.java.functions.Functional;

/** Base class for real valued functions on <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-08
 */

public abstract class ScalarFunctional extends Functional {

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  public String safeName () {
    throw 
    Exceptions.unsupportedOperation(getClass(),"safeName"); }

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

