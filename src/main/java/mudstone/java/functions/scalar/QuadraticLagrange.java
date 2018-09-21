package mudstone.java.functions.scalar;

import static java.lang.Math.*;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ScalarFunctional;

/** An quadratic function from <b>R</b> to <b>R</b> in Lagrange 
 * form.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-21
 */

public final class QuadraticLagrange extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  // Lagrange form

  private final double _x0;
  private final double _x1;
  private final double _x2; 

  private final double _b0;
  private final double _b1;
  private final double _b2;

  // TODO: space vs re-computing cost?
  private final double _xmin;

  private final double _positiveLimitValue;
  private final double _negativeLimitValue;
  private final double _positiveLimitSlope;
  private final double _negativeLimitSlope;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    final double dx0 = x-_x0;
    final double dx1 = x-_x1;
    final double dx2 = x-_x2;
    return fma(_b0,dx1*dx2,fma (_b1,dx2*dx0,_b2*dx0*dx1)); }

  @Override
  public final double slope (final double x) {
    final double dx0 = x-_x0;
    final double dx1 = x-_x1;
    final double dx2 = x-_x2;
    return fma(_b0,dx1+dx2,fma(_b1,dx2+dx0,_b2*(dx0+dx1))); }

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _x0 + "," + _x1 + "," + _x2 + ";" +
      _b0 + "," + _b1 + "," + _b2 + ";" +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  // symmetric argmin formula
  private QuadraticLagrange (final double x0, final double y0,
                             final double x1, final double y1,
                             final double x2, final double y2) {
    assert x0 < x1 && x1 < x2 : 
      "Fail: " + x0 + " < " + x1 + " < " + x2;
    _x0 = x0;
    _x1 = x1;
    _x2 = x2;
    _b0 = y0/((x0-x1)*(x0-x2));
    _b1 = y1/((x1-x2)*(x1-x0));
    _b2 = y2/((x2-x0)*(x2-x1));
    // TODO: within epsilon test?
    if (0.0 > (_b0 + _b1 + _b2)) { // 1/2 2nd derivative
      // critical point is a maximum, xmin at either +/- infinity
      _xmin = Double.POSITIVE_INFINITY; }
    else {
      // may still be +/- infinity if parabola degenerates to a line.
      final double a = (x1-x2)*(y0-y1);
      final double b = (x0-x1)*(y1-y2);
      final double numer = ((x1+x0)*b)-((x1+x2)*a); 
      _xmin = numer/(2.0*(b-a)); } }

  public static final QuadraticLagrange 
  make (final double x0, final double y0,
        final double x1, final double y1,
        final double x2, final double y2) {

    if (x0 < x1) {
      if (x1 < x2) {
        return new QuadraticLagrange(x0,y0,x1,y1,x2,y2); }
      if (x0 < x2) {
        return new QuadraticLagrange(x0,y0,x2,y2,x1,y1); } 
      if (x2 < x0) {
        return new QuadraticLagrange(x2,y2,x0,y0,x1,y1); } }

    if (x1 < x0) {
      if (x0 < x2) {
        return new QuadraticLagrange(x1,y1,x0,y0,x2,y2); }
      if (x1 < x2) {
        return new QuadraticLagrange(x1,y1,x2,y2,x0,y0); } 
      if (x2 < x1) {
        return new QuadraticLagrange(x2,y2,x1,y1,x0,y0); } }

    throw new IllegalArgumentException(
      "Not distinct: " + x0 + ", " + x1 + ", " + x2); }

  public static final QuadraticLagrange 
  make (final Function f, 
        final double x0, 
        final double x1, 
        final double x2) {
    return make(
      x0,f.doubleValue(x0),
      x1,f.doubleValue(x1),
      x2,f.doubleValue(x2));}

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

