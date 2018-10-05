package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

import java.util.List;
import java.util.function.BiFunction;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.Vektor;

/** An affine function from <b>R</b> to <b>R</b>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-05
 */

public final class AffineFunctional1d extends Polynomial {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------
  // TODO: wrap a LinearFunctional?

  private final double _translation;
  public final double translation () { return _translation; }

  private final double _slope;
  public final double slope () { return _slope; }

  //--------------------------------------------------------------
  // Polynomial methods
  //--------------------------------------------------------------

  @Override
  public final int degree () { return 1; }
  
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

  public static final ScalarFunctional 
  interpolateXYD (final Function f,
                  final double[] x) {
    final double x0 = x[0];
    final double y0 = f.doubleValue(x0);
    final double a1 = f.slope(x0);
    if (0.0==a1) { return ConstantFunction.make(y0); }
    final double a0 = fma(-a1,x0,y0);
    return new AffineFunctional1d(a0,a1); }

  public static final ScalarFunctional 
  interpolateXYD (final Object f,
                  final Object x) {
    return interpolateXYD((Function) f, (double[]) x); }

  //--------------------------------------------------------------

  public static final ScalarFunctional 
  interpolateXY (final Function f,
                  final double[] x) {
    final double x0 = x[0];
    final double x1 = x[1];
    final double y0 = f.doubleValue(x0);
    final double y1 = f.doubleValue(x1);
    if (y0==y1) { return ConstantFunction.make(y0); }
    // TODO: BigFraction?
    final double a1 = (y1-y0)/(x1-x0);
    final double a0 = ((y0*x1)-(y1*x0))/(x1-x0);
    return new AffineFunctional1d(a0,a1); }

  public static final ScalarFunctional 
  interpolateXY (final Object f,
                  final Object x) {
    return interpolateXY((Function) f, (double[]) x); }

  public static final List<BiFunction> INTERPOLATORS =
    List.of(
      AffineFunctional1d::interpolateXY,
      AffineFunctional1d::interpolateXYD);
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

