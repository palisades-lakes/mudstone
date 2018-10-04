package mudstone.java.functions.scalar;

import static java.lang.Double.*;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.Vektor;

/** An affine function from <b>R</b> to <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-04
 */

public final class AffineFunctional1d extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // TODO: wrap a LinearFunctional?

  private final double _translation;
  public final double translation () { return _translation; }

  private final double _slope;
  public final double slope () { return _slope; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    if (isNaN(x)) { return NaN; }
    return _slope*x + _translation; }

  @Override
  @SuppressWarnings("unused")
  public final double slope (final double x) { 
    if (isNaN(x)) { return NaN; }
    return _slope; }

  @Override
  public final double doubleArgmin (final Domain support) { 
    if (isNaN(_slope) || isNaN(_translation)) { return NaN; }
    final double xmin;
    if (0.0 < _slope) { xmin = NEGATIVE_INFINITY; }
    else if (0.0 > _slope) { xmin = POSITIVE_INFINITY; }
    else { xmin = NaN; }
    if (support.contains(xmin)) { return xmin; }
    return NaN; }

  @Override
  @SuppressWarnings("unused")
  public final Function tangentAt (final Vektor x) { 
    return this; }

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

  private AffineFunctional1d (final double translation,
                              final double slope) {
    _slope = slope; 
    _translation = translation; }

  public static final AffineFunctional1d 
  make (final double translation,
        final double slope) {
    return new AffineFunctional1d(translation,slope); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

