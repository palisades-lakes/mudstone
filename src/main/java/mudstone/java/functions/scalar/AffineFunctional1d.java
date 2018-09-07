package mudstone.java.functions.scalar;

import mudstone.java.Function;
import mudstone.java.ScalarFunctional;
import mudstone.java.Vektor;

/** An affine function from <b>R</b> to <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-06
 */

public final class AffineFunctional1d extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // TODO: wrap a LinearFunctional?

  private final double _slope;
  public final double slope () { return _slope; }

  private final double _translation;
  public final double translation () { return _translation; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    return _slope*x + _translation; }

  @Override
  @SuppressWarnings("unused")
  public final double slope (final double x) { return _slope; }

  @Override
  @SuppressWarnings("unused")
  public final Function tangent (final Vektor x) { return this; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------
  // TODO: too long for toString..

  @Override
  public final String toString () {
    final StringBuilder b = new StringBuilder();
    b.append(getClass().getSimpleName());
    b.append(":\n");
    b.append(_slope);
    b.append("*x + ");
    b.append(_translation);
    return b.toString(); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private AffineFunctional1d (final double slope,
                            final double translation) {
    _slope = slope; 
    _translation = translation; }

  public static final AffineFunctional1d 
  make (final double slope,
        final double translation) {
    return new AffineFunctional1d(slope,translation); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

