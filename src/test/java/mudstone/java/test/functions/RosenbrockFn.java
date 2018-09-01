package mudstone.java.test.functions;

import static java.lang.Math.fma;
import java.util.Arrays;

import mudstone.java.Function;

//==========================================================
/** A common cost function used to test optimization code.<br>
 * <b>Note:</b> The dimension of the domain must be even.
 * <p>
 * See Dennis-Schnabel, Appendix B.
 * <p>
 * Could re-write this as square nonlinear Map x -> _f
 * followed by l2 norm for testing product Cost functions.
 * <p>
 * Note: immutable.
 * <p>
 * TODO: add translation to test other optima
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-08
 */

//strictfp 
public final class RosenbrockFn implements Function {

  private final int _dimension;

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------
  /** Return the minimizer for the {@link RosenbrockFn} test
   * function.
   */

  public final double[] trueMinimizer () {

    final double[] xmin = new double[domainDimension()];
    Arrays.fill(xmin,1.0);
    return xmin; }

  //--------------------------------------------------------------
  /** Return a standard starting point for the
   * {@link RosenbrockFn} test function.
   */

  public final double[] start0 () {

    final double[] p = new double[domainDimension()];
    final int n = domainDimension()/2;
    for (int i=0;i<n;i++) {
      final int j= 2*i;
      p[j] = -1.2;
      p[j+1] = 1.0; }

    return p; }

  //--------------------------------------------------------------
  /** Return a standard starting point for the
   * {@link RosenbrockFn} test function.
   */

  public final double[] start1 () {

    final double[] p = new double[domainDimension()];
    final int n = domainDimension()/2;
    for (int i=0;i<n;i++) {
      final int j= 2*i;
      p[j] = 6.390;
      p[j+1] =-0.221; }

    return p; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final int domainDimension () { return _dimension; }

  @Override
  public final int codomainDimension () { return 1; }

  //--------------------------------------------------------------

  @Override
  public final double doubleValue (final double[] x) {
    final int n = domainDimension();
    assert n == x.length;
    double y = 0.0;
    for (int j=0;j<n;) {
      final double x2im1 = x[j++];
      final double x2i   = x[j++];
      final double f2im1 = 10.0*(x2i - (x2im1*x2im1));
      final double f2i   = 1.0 - x2im1;
      y = fma(f2im1,f2im1,y);
      y = fma(f2i,f2i,y); } 
    return y; }

  //--------------------------------------------------------------

  @Override
  public final void gradient (final double[] x,
                              final double[] g) {
    final int n = domainDimension();
    assert n == g.length;
    assert n == x.length;
    //Arrays.fill(g,Double.NaN);
    for (int j=0;j<n;j+=2) {
      final double x2im1 = x[j];
      final int j1 = j+1;
      final double x2i   = x[j1];

      final double f2im1 = 10.0*(x2i - (x2im1*x2im1));
      final double f2i   = 1.0 - x2im1;

      final double df2im1_dx2im1 = -20.0*x2im1;
      g[j] = 2.0*fma(f2im1,df2im1_dx2im1,-f2i);
      g[j1] = 20.0*f2im1; } }

  //--------------------------------------------------------------

  @Override
  public final double valueAndGradient (final double[] x,
                                        final double[] g) {
    final int n = domainDimension();
    assert n == g.length;
    assert n == x.length;
    //Arrays.fill(g,Double.NaN);
    double y = 0.0;
    for (int j=0;j<n;j+=2) {
      final double x2im1 = x[j];
      final int j1 = j+1;
      final double x2i   = x[j1];

      final double f2im1 = 10.0*(x2i-(x2im1*x2im1));
      final double f2i   = 1.0 - x2im1;

      final double df2im1_dx2im1 = -20.0*x2im1;
      g[j] = 2.0*fma(f2im1,df2im1_dx2im1,-f2i);
      g[j1] = 20.0*f2im1;
      y = fma(f2im1,f2im1,y);
      y = fma(f2i,f2i,y); }

    return y; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private RosenbrockFn (final int dimension) {
    super();
    assert (dimension % 2) == 0;
    _dimension = dimension; }

  //--------------------------------------------------------------
  /** Return a {@link RosenbrockFn} test function of the given
   ** <code>dimension</code> (which is forced to be even).
   **/

  public static final RosenbrockFn get (final int halfDimension) {
    return new RosenbrockFn(2*halfDimension); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

