package mudstone.java.functions.scalar;

import mudstone.java.functions.scalar.ScalarFunctional;

/** An quadratic function from <b>R</b> to <b>R</b> interpolating
 * 3 (x,y) pairs.
 * <p>
 * Currently a hack just providing the argmin.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-07
 */

public final class InterpolantXY3 extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  //  private final double _x0;
  //  private final double _y0;
  //  private final double _x1;
  //  private final double _y1;
  //  private final double _x2;
  //  private final double _y2;

  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------
  // TODO: value and derivatives

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------
  // TODO: too long for toString..

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _xmin + "]"; }
  //      _x0 + "," + _y0 + ";" +
  //      _x1 + "," + _y1 + ";" +
  //      _x2 + "," + _y2 + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  // Commons math 3 formula for argmin
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
    final double dx10 = x1-x0;
    final double dy10 = y1-y0;
    final double dx12 = x1-x2;
    final double dy12 = y1-y2;
    final double a = dx12*dy10;
    final double b = dx10*dy12;
    final double numer = ((x1+x2)*a)-((x1+x0)*b); 
    // may be +/- infinity if parabola degenerates to a line.
    _xmin = numer/(2.0*(a-b)); }

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


  //--------------------------------------------------------------
}
//--------------------------------------------------------------

