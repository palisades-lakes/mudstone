package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.*;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;

/** An quadratic function from <b>R</b> to <b>R</b> in Lagrange 
 * form.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-29
 */

public final class CubicLagrange extends ScalarFunctional {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final double _x0;
  private final double _x1;
  private final double _x2; 
  private final double _x3; 

  private final double _b0;
  private final double _b1;
  private final double _b2;
  private final double _b3;

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
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      final double dx3 = x-_x3;
      return 
        fma(_b0,dx1*dx2*dx3,
          fma(_b1,dx2*dx0*dx3,
            fma(_b2,dx0*dx1*dx3,
              _b3*dx0*dx1*dx2))); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      final double dx3 = x-_x3;
      return 
        fma(_b0,(dx1*dx2)+(dx2*dx3)+(dx3*dx1),
          fma(_b1,(dx2*dx3)+(dx3*dx0)+(dx0*dx2),
            fma(_b2,(dx3*dx0)+(dx0*dx1)+(dx1*dx3),
              _b3*(dx0*dx1)+(dx1*dx2)+(dx2*dx0)))); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

  @Override
  public final double doubleArgmin (final Domain support) { 
    final Interval bounds = (Interval) support;
    if (bounds.contains(_xmin)) { return _xmin; }
    return NaN; }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final String toString () {
    return
      getClass().getSimpleName() + "[" + 
      _x0 + "," + _x1 + "," + _x2 + "," + _x3 + ";" +
      _b0 + "," + _b1 + "," + _b2 + "," + _b3 + ";" +
      _xmin + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: BigFraction calculation?
  // TODO: use fma?

  private static final double argmin (final double x0, 
                                      final double x1, 
                                      final double x2, 
                                      final double x3, 
                                      final double b0, 
                                      final double b1, 
                                      final double b2, 
                                      final double b3) {
    // derivative as a*x^2 + b*x + c
    final double a = 3.0*(b0+b1+b2+b3);
    final double b = 
      -(((b0+b1)*(x2+x3)) +
        ((b1+b2)*(x3+x0)) +
        ((b2+b3)*(x0+x1)) +
        ((b3+b0)*(x3+x1)) +
        ((b0+b2)*(x3+x1)) +
        ((b1+b3)*(x0+x2)));
    final double c = 
      (((b0+b1)*(x2*x3)) +
        ((b1+b2)*(x3*x0)) +
        ((b2+b3)*(x0*x1)) +
        ((b3+b0)*(x3*x1)) +
        ((b0+b2)*(x3*x1)) +
        ((b1+b3)*(x0*x2)));
    final double b2m4ac = (b*b) - (4.0*a*c);
    // check for existence of critical points
    if (0.0 < b2m4ac) {
      final double sqrtb2m4ac = sqrt(b2m4ac);
      final double xmin0 = (-b - sqrtb2m4ac)/(2.0*a); 
      final double d20 = 2.0*a*xmin0 + b;
      if (d20>0) { return xmin0; }
      // TODO: must this be a local min now?
      final double xmin1 = (-b + sqrtb2m4ac)/(2.0*a); 
      final double d21 = 2.0*a*xmin1 + b;
      if (d21>0) { return xmin1; } }
    return NaN; }

  //--------------------------------------------------------------

  private CubicLagrange (final double x0, final double y0,
                         final double x1, final double y1,
                         final double x2, final double y2,
                         final double x3, final double y3) {
    assert x0 != x1;
    assert x0 != x2;
    assert x0 != x3;
    assert x1 != x2;
    assert x1 != x3;
    assert x2 != x3;
    _x0 = x0;
    _x1 = x1;
    _x2 = x2;
    _x3 = x3;
    _b0 = y0/((x0-x1)*(x0-x2)*(x0-x3));
    _b1 = y1/((x1-x2)*(x1-x3)*(x1-x0));
    _b2 = y2/((x2-x0)*(x2-x1)*(x2-x3));
    _b3 = y2/((x3-x0)*(x3-x1)*(x3-x2));

    // TODO: accurate 1st and 2nd derivative sign calculation?
    final double[] a = QuadraticUtils
      .interpolatingMonomialCoefficients(x0,y0,x1,y1,x2,y2);
    //    System.out.println(
    //      "QL[" + a[0] + " + " + a[1] + "*x + " + a[2] + "*x^2]");
    if (0.0 < a[2]) {
      _xmin = argmin(x0,y0,x1,y1,x2,y2,x3,y3);
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a[2]) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      if (0.0 < a[1]) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = a[1]; 
        _negativeLimitSlope = a[1]; }
      else if (0.0 > a[1]) {
        _xmin = POSITIVE_INFINITY;
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = a[1]; 
        _negativeLimitSlope = a[1]; }
      else { // constant
        _xmin = NaN;
        _positiveLimitValue = a[0]; 
        _negativeLimitValue = a[0]; 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  public static final CubicLagrange 
  make (final double x0, final double y0,
        final double x1, final double y1,
        final double x2, final double y2,
        final double x3, final double y3) {

    return new CubicLagrange(x0,y0,x1,y1,x2,y2,x3,y3); }

  public static final CubicLagrange 
  make (final Function f, 
        final double x0, 
        final double x1, 
        final double x2, 
        final double x3) {
    return make(
      x0,f.doubleValue(x0),
      x1,f.doubleValue(x1),
      x2,f.doubleValue(x2),
      x2,f.doubleValue(x3));}

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

