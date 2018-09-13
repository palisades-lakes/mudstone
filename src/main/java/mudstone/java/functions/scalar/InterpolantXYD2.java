package mudstone.java.functions.scalar;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/** A cubic function from <b>R</b> to <b>R</b> interpolating
 * 2 (x,y=f(x),d=df(x)) triples (Hermite interpolant).
 * <p>
 * Only argmin implemented for now.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-13
 */

public final class InterpolantXYD2 extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

//  private final double _x0;
//  private final double _x1;
//
//  private final double _dx10;
//
//  private final double _y0;
//  private final double _y1;
//
//  private final double _d0;
//  private final double _d1;

  private final double _xmin;

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------


  // Note: only a local minimum; global minimum is either
  // +/- infinity.

  @Override
  public final double doubleArgmin () { return _xmin; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
//      _x0 + "," + _y0 + "," + _d0 + ";" +
//      _x1 + "," + _y1 + "," + _d1 + ";" +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private InterpolantXYD2 (final double x0, 
                           final double y0,
                           final double d0,
                           final double x1, 
                           final double y1,
                           final double d1) {
    assert x0 < x1  : "Fail: " + x0 + " < " + x1 ;
    //    _x0 = x0;
    //    _x1 = x1;
    //    _y0 = y0;
    //    _y1 = y1;
    //    _d0 = d0;
    //    _d1 = d1;

    final double dx = x1-x0;
    final double v = (d0+d1) - ((3.0*(y1-y0)) / dx);
    final double t = (v*v) - (d0*d1);
    // complex roots, no critical points
    // could return global min at +/- infinity.
    if (t < 0.0) { _xmin = Double.NaN; }
    else {
      final double w0 = sqrt(t);
      final double w1 = (d0+v)-w0;
      final double w2 = (d1+v)+w0;
      if ((w1 == 0.0) && (w2 == 0.0)) { _xmin = Double.NaN; }
      else if (abs(w1) >= abs(w2)) { _xmin =  x0 + ((dx*d0) / w1); }
      else { _xmin =   x1 - ((dx*d1) / w2); } } }

  public static final InterpolantXYD2 
  make (final double x0, final double y0, final double d0,
        final double x1, final double y1, final double d1) {

    if (x0 < x1) {
      return new InterpolantXYD2(x0,y0,d0,x1,y1,d1); }
    return new InterpolantXYD2(x1,y1,d1,x0,y0,d0); }


  //--------------------------------------------------------------
}
//--------------------------------------------------------------

