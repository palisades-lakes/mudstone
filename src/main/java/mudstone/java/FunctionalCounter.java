package mudstone.java;

/** Wrapper that counts function and gradient evaluations,
 * and caches last position, value, and gradient.
 * 
 * <em>WARNING:</em> not `thread safe!
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-01
 */

public final class FunctionalCounter implements Function {

  //--------------------------------------------------------------
  // fields
  //--------------------------------------------------------------

  private final Function _inner;
  public final Function inner () { return _inner; }

  // profiling seems to show domainDimension() taking a long time?
  private final int _dd;
  
  private int _nf = 0;
  public final int nf () { return _nf; }

  private int _ng = 0;
  public final int ng () { return _ng; }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public final int domainDimension () { return _dd; }

  @Override
  public final int codomainDimension () { return 1; }

  @Override
  public final double doubleValue (final Vektor x) {
    _nf++; return _inner.doubleValue(x); }

  @Override
  public final Vektor gradient (final Vektor x) {
    _ng++; return _inner.gradient(x); }

  @Override
  public final Function tangent (final Vektor x) {
    _nf++; _ng++; return _inner.tangent(x); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private FunctionalCounter (final Function inner) {
    super();
    _inner = inner;
    _dd = inner.domainDimension(); }

  public static final FunctionalCounter 
  wrap (final Function inner) {
    // assert 1 == inner.domainDimension();
    return new FunctionalCounter(inner); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
