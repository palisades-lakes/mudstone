package mudstone.java.functions.scalar;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ScalarFunctional;

/** An quadratic function from <b>R</b> to <b>R</b> interpolating
 * 3 (x,y) pairs (quadratic Lagrange interpolant).
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-15
 */

public final class InterpolantXY3 extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _x0;
  private final double _x1;
  private final double _x2; 

  private final double _b0;
  private final double _b1;
  private final double _b2;

  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double x) {
    final double dx0 = x-_x0;
    final double dx1 = x-_x1;
    final double dx2 = x-_x2;
    return (_b0*dx1*dx2) + (_b1*dx2*dx0) + (_b2*dx0*dx1); }

  @Override
  public final double slope (final double x) {
    final double dx0 = x-_x0;
    final double dx1 = x-_x1;
    final double dx2 = x-_x2;
    return _b0*(dx1+dx2) + _b1*(dx2+dx0) + _b2*(dx0+dx1); }

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

  // Commons Math 3 formula for argmin
  //  private InterpolantXY3 (final double x0, final double y0,
  //                          final double x1, final double y1,
  //                          final double x2, final double y2) {
  //    assert x0 < x1 && x1 < x2 : 
  //      "Fail: " + x0 + " < " + x1 + " < " + x2;
  //    final double z1012 = (x1-x0)*(y1-y2);
  //    final double z1210 = (x1-x2)*(y1-y0);
  //    final double denom = 2.0*(z1210-z1012);
  //    final double numer = (x1-x2)*z1210 - (x1-x0)*z1012; 
  //    // may be +/- infinity if parabola degenerates to a line.
  //    _xmin = x1 - numer/denom; }

  // symmetric argmin formula
  private InterpolantXY3 (final double x0, final double y0,
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

  public static final InterpolantXY3 
  make (final double x0, final double y0,
        final double x1, final double y1,
        final double x2, final double y2) {

    if (x0 < x1) {
      if (x1 < x2) {
        return new InterpolantXY3(x0,y0,x1,y1,x2,y2); }
      if (x0 < x2) {
        return new InterpolantXY3(x0,y0,x2,y2,x1,y1); } 
      if (x2 < x0) {
        return new InterpolantXY3(x2,y2,x0,y0,x1,y1); } }

    if (x1 < x0) {
      if (x0 < x2) {
        return new InterpolantXY3(x1,y1,x0,y0,x2,y2); }
      if (x1 < x2) {
        return new InterpolantXY3(x1,y1,x2,y2,x0,y0); } 
      if (x2 < x1) {
        return new InterpolantXY3(x2,y2,x1,y1,x0,y0); } }

    throw new IllegalArgumentException(
      "Not distinct: " + x0 + ", " + x1 + ", " + x2); }

  public static final InterpolantXY3 
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

