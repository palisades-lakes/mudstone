package mudstone.java.test.functions;

import java.util.Arrays;

import mudstone.java.Function;

//----------------------------------------------------------------
/** Test exceptions with constant functionalN.
 * <p>
 * Note: immutable.
 * <p>
 * TODO: add translation to test other optima
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-02
 */

public strictfp final class ConstantFn implements Function {

  private final int _dimension;

  private final double _value;
  private final double _gi;

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  public final double[] start (final double xi) {
    final double[] x = new double[domainDimension()];
    Arrays.fill(x,xi);
    return x; }

  public final double[] trueMinimizer (final double xi) {
    final double[] x = new double[domainDimension()];
    Arrays.fill(x,xi);
    return x; }

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
    return _value; }

  //--------------------------------------------------------------

  @Override
  public final void gradient (final double[] x,
                              final double[] g) {
    final int n = domainDimension();
    assert n == g.length;
    assert n == x.length;
    Arrays.fill(g,_gi); }
  
  //--------------------------------------------------------------

  @Override
  public final double valueAndGradient (final double[] x,
                                        final double[] g) {
    final int n = domainDimension();
    assert n == g.length;
    assert n == x.length;
    Arrays.fill(g,_gi);
    return _value; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private ConstantFn (final int dimension,
                      final double v,
                      final double gi) {
    super();
    _dimension = dimension;
    _value = v;
    _gi = gi; }

  //--------------------------------------------------------------
  /** Return a {@link ConstantFn} test function of the given
   ** <code>dimension</code> (which is forced to be even).
   **/

  public static final ConstantFn get (final int dimension,
                                      final double v,
                                      final double gi) {
    return new ConstantFn(dimension,v,gi); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------

