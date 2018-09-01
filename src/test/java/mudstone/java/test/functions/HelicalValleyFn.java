package mudstone.java.test.functions;

import static java.lang.Math.*;

import java.util.Arrays;

import mudstone.java.Function;

//==========================================================
/** A common cost function used to test optimization code.<br>
 * <p>
 * See Dennis-Schnabel, Appendix B.
 * <p>
 * Note: immutable.
 * <p>
 * TODO: automatic gradient check.
 * TODO: add translation to test other optima
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-02
 */

//strictfp 
public final class HelicalValleyFn implements Function {

  public static final int DIMENSION = 3;

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------
  /** Return the minimizer for the {@link HelicalValleyFn} test
   * function.
   */

  public static final double[] trueMinimizer () {
    return new double[] { 1, 0, 0, }; }

  //--------------------------------------------------------------
  /** Return a standard starting point for the
   * {@link HelicalValleyFn} test function.
   */

  public static final double[] start () {
    return new double[] { -1, 0, 0, }; }

  //--------------------------------------------------------------

  private static final double TWOPI = 2.0*PI;

  private static final double theta (final double x0,
                                     final double x1) {
    return atan2(x1,x0)/TWOPI; }

  private static final double dtheta0 (final double x0,
                                       final double x1) {
    final double x02 = x0*x0;
    final double x12 = x1*x1;
    return -x1/ (TWOPI*(x02 + x12)); }

  private static final double dtheta1 (final double x0,
                                       final double x1) {
    final double x02 = x0*x0;
    final double x12 = x1*x1;
    return x0 / (TWOPI*(x02 + x12)); }

  //--------------------------------------------------------------

  private static final double f0 (final double[] x) {
    assert DIMENSION == x.length;
    return 10.0*(x[2] - (10.0*theta(x[0],x[1]))); }

  private static final double df00 (final double[] x) {
    assert DIMENSION == x.length;
    return -100.0*dtheta0(x[0],x[1]); }

  private static final double df01 (final double[] x) {
    assert DIMENSION == x.length;
    return -100.0*dtheta1(x[0],x[1]); }

  //  private static final double df02 (final double[] x) {
  //    assert DIMENSION == x.length;
  //    return 10.0; }

  //--------------------------------------------------------------

  private static final double f1 (final double[] x) {
    assert DIMENSION == x.length;
    final double x0 = x[0];
    final double x1 = x[1];
    return 10.0*(sqrt((x0*x0) + (x1*x1)) - 1.0); }

  private static final double df10 (final double[] x) {
    assert DIMENSION == x.length;
    final double x0 = x[0];
    final double x1 = x[1];
    return (10.0*x0)/sqrt((x0*x0) + (x1*x1)); }

  private static final double df11 (final double[] x) {
    assert DIMENSION == x.length;
    final double x0 = x[0];
    final double x1 = x[1];
    return (10.0*x1)/sqrt((x0*x0) + (x1*x1)); }


  //--------------------------------------------------------------

  private static final double f2 (final double[] x) {
    assert DIMENSION == x.length;
    return x[2]; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final int domainDimension () { return DIMENSION; }

  @Override
  public final int codomainDimension () { return 1; }

  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double[] x) {
    assert DIMENSION == x.length;
    final double y0 = f0(x);
    final double y1 = f1(x);
    final double y2 = f2(x);
    return (y0*y0) + (y1*y1) + (y2*y2); }

  //--------------------------------------------------------------

  @Override
  public final void gradient (final double[] x,
                              final double[] g) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    Arrays.fill(g,Double.NaN);
    final double y0 = f0(x);
    final double y1 = f1(x);
    final double y2 = f2(x);
    g[0] = 2.0*((y0*df00(x)) + (y1*df10(x)));
    g[1] = 2.0*((y0*df01(x)) + (y1*df11(x)));
    g[2] = 2.0*((y0*10.0) + y2); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private HelicalValleyFn () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link HelicalValleyFn} test function.
   * Could be a singleton.
   */

  public static final HelicalValleyFn get () {
    return new HelicalValleyFn(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

