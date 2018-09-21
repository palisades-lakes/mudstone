package mudstone.java.functions.scalar;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
import static java.lang.Math.fma;

import mudstone.java.functions.Function;

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
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      return fma(_b0,dx1*dx2,fma (_b1,dx2*dx0,_b2*dx0*dx1)); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitValue; }
    return _negativeLimitValue; }

  @Override
  public final double slope (final double x) {
    if (isFinite(x)) {
      final double dx0 = x-_x0;
      final double dx1 = x-_x1;
      final double dx2 = x-_x2;
      return fma(_b0,dx1+dx2,fma(_b1,dx2+dx0,_b2*(dx0+dx1))); }
    if (isNaN(x)) { return NaN; }
    if (POSITIVE_INFINITY == x) { return _positiveLimitSlope; }
    return _negativeLimitSlope; }

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
  // monomial coefficients for limiting values
  // TODO: BigFraction for accuracy?

  private static final double a0 (final double x0, 
                                  final double y0,
                                  final double x1, 
                                  final double y1,
                                  final double x2, 
                                  final double y2) {
    final double dx01 = x0-x1;
    final double dx12 = x1-x2;
    final double dx20 = x2-x0;
    return
      -(((y0*x1*x2)/(dx01*dx20)) +
        ((y1*x2*x0)/(dx12*dx01)) +
        ((y2*x0*x1)/(dx20*dx12))); }

  private static final double a1 (final double x0, 
                                  final double y0,
                                  final double x1, 
                                  final double y1,
                                  final double x2, 
                                  final double y2) {
    final double dx01 = x0-x1;
    final double dx12 = x1-x2;
    final double dx20 = x2-x0;
    return
      ((y0*(x1+x2))/(dx01*dx20)) +
      ((y1*(x2+x0))/(dx12*dx01)) +
      ((y2*(x0+x1))/(dx20*dx12)); }

  private static final double a2 (final double x0, 
                                  final double y0,
                                  final double x1, 
                                  final double y1,
                                  final double x2, 
                                  final double y2) {
    final double dx01 = x0-x1;
    final double dx12 = x1-x2;
    final double dx20 = x2-x0;
    return
      -((y0/(dx01*dx20)) +
        (y1/(dx12*dx01)) +
        (y2/(dx20*dx12))); }

  //--------------------------------------------------------------

  // symmetric argmin formula
  private QuadraticLagrange (final double x0, final double y0,
                             final double x1, final double y1,
                             final double x2, final double y2) {
    assert x0 != x1;
    assert x1 != x2;
    assert x2 != x0;
    _x0 = x0;
    _x1 = x1;
    _x2 = x2;
    _b0 = y0/((x0-x1)*(x0-x2));
    _b1 = y1/((x1-x2)*(x1-x0));
    _b2 = y2/((x2-x0)*(x2-x1));

    // TODO: accurate 1st and 2nd derivative sign calculation?
    final double a2 = a2(x0,y0,x1,y1,x2,y2);
    if (0.0 < a2) {
      final double a = (x1-x2)*(y0-y1);
      final double b = (x0-x1)*(y1-y2);
      final double numer = ((x1+x0)*b)-((x1+x2)*a); 
      _xmin = numer/(2.0*(b-a)); 
      _positiveLimitValue = POSITIVE_INFINITY; 
      _negativeLimitValue = POSITIVE_INFINITY; 
      _positiveLimitSlope = POSITIVE_INFINITY; 
      _negativeLimitSlope = NEGATIVE_INFINITY; }
    else if (0.0 > a2) {
      _xmin = POSITIVE_INFINITY;
      _positiveLimitValue = NEGATIVE_INFINITY; 
      _negativeLimitValue = NEGATIVE_INFINITY; 
      _positiveLimitSlope = NEGATIVE_INFINITY; 
      _negativeLimitSlope = POSITIVE_INFINITY; }
    else {// affine, look at a1
      final double a1 = a1(x0,y0,x1,y1,x2,y2);
      if (0.0 < a1) {
        _xmin = NEGATIVE_INFINITY;
        _positiveLimitValue = POSITIVE_INFINITY; 
        _negativeLimitValue = NEGATIVE_INFINITY; 
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; }
      else if (0.0 > a1) {
        _xmin = POSITIVE_INFINITY;
        _positiveLimitValue = NEGATIVE_INFINITY; 
        _negativeLimitValue = POSITIVE_INFINITY; 
        _positiveLimitSlope = a1; 
        _negativeLimitSlope = a1; }
      else { // constant
        final double a0 = a0(x0,y0,x1,y1,x2,y2);
        _xmin = NaN;
        _positiveLimitValue = a0; 
        _negativeLimitValue = a0; 
        _positiveLimitSlope = 0.0; 
        _negativeLimitSlope = 0.0; } } } 

  public static final QuadraticLagrange 
  make (final double x0, final double y0,
        final double x1, final double y1,
        final double x2, final double y2) {

    // TODO: not necessary to sort?
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

