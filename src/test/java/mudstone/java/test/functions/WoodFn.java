package mudstone.java.test.functions;

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

strictfp public final class WoodFn implements Function {

  public static final int DIMENSION = 4;

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------
  /** Return the minimizer for the {@link WoodFn} test
   * function.
   */

  public static final double[] trueMinimizer () {
    return new double[] { 1, 1, 1, 1, }; }

  //--------------------------------------------------------------
  /** Return a standard starting point for the
   * {@link WoodFn} test function.
   */

  public static final double[] start () {
    return new double[] { -3, -1, -3, -1, }; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final int domainDimension () { return DIMENSION; }

  @Override
  public final int codomainDimension () { return 1; }

  //--------------------------------------------------------------

  private static final double f0 (final double[] x) {
    assert DIMENSION == x.length;
    final double x0 = x[0];
    final double x1 = x[1];
    final double a = (x0*x0) - x1;
    return 100.0*(a*a); }

  private static final void df0 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x0 = x[0];
    final double x1 = x[1];
    final double a = (x0*x0) - x1;
    g[0] += 400.0*x[0]*a;
    g[1] += -200.0*a; }

  private static final double f1 (final double[] x) {
    assert DIMENSION == x.length;
    final double x0 = x[0];
    final double a = 1.0 - x0;
    return a*a; }

  private static final void df1 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x0 = x[0];
    final double a = 1.0 - x0;
    g[0] += -2.0*a; }

  private static final double f2 (final double[] x) {
    assert DIMENSION == x.length;
    final double x2 = x[2];
    final double x3 = x[3];
    final double a = (x2*x2) - x3;
    return 90.0*(a*a); }

  private static final void df2 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x2 = x[2];
    final double x3 = x[3];
    final double a = (x2*x2) - x3;
    g[2] += 360.0*a*x2;
    g[3] += -180.0*a; }

  private static final double f3 (final double[] x) {
    assert DIMENSION == x.length;
    final double x2 = x[2];
    final double a = 1.0 - x2;
    return a*a; }

  private static final void df3 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x2 = x[2];
    final double a = 1.0 - x2;
    g[2] += -2.0*a; }

  private static final double f4 (final double[] x) {
    assert DIMENSION == x.length;
    final double x1 = x[1];
    final double a1 = 1.0 - x1;
    final double x3 = x[3];
    final double a3 = 1.0 - x3;
    return 10.1*((a1*a1) + (a3*a3)); }

  private static final void df4 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x1 = x[1];
    final double a1 = 1.0 - x1;
    final double x3 = x[3];
    final double a3 = 1.0 - x3;
    g[1] += -20.1*a1;
    g[3] += -20.1*a3; }

  private static final double f5 (final double[] x) {
    assert DIMENSION == x.length;
    return 19.8*(1.0-x[1])*(1.0-x[3]); }

  private static final void df5 (final double[] g,
                                 final double[] x) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    final double x1 = x[1];
    final double a1 = 1.0 - x1;
    final double x3 = x[3];
    final double a3 = 1.0 - x3;
    g[1] += -19.8*a3;
    g[3] += -19.8*a1; }
  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double[] x) {
    assert DIMENSION == x.length;
    return f0(x) + f1(x) + f2(x) + f3(x) + f4(x) + f5(x); }

  //--------------------------------------------------------------

  @Override
  public final void gradient (final double[] x,
                              final double[] g) {
    assert DIMENSION == x.length;
    assert DIMENSION == g.length;
    Arrays.fill(g,0.0);
    df0(g,x);
    df1(g,x);
    df2(g,x);
    df3(g,x);
    df4(g,x);
    df5(g,x); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private WoodFn () { super(); }

  //--------------------------------------------------------------
  /** Return a {@link WoodFn} test function.
   * Could be a singleton.
   */

  public static final WoodFn get () {
    return new WoodFn(); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

