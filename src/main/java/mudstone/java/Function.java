package mudstone.java;

/** A (optionally differentiable) function between linear spaces,
 * from <b>R</b><sup>n</sup> (domain)
 * to <b>R</b><sup>m</sup> (codomain). 
 * <p>
 * Note: Elements of <b>R</b><sup>n</sup> are represented by
 * mutable instances of <code>double[]</code> of the right length.
 * <p>
 * Optional interface methods are provided for the common cases of
 * real (<code>double</code>) valued codomains and real 
 * (<code>double</code>) valued domains and codomains.
 * <p>
 * TODO: may consider an immutable representation for elements of
 *  <b>R</b><sup>n</sup>.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-31`
 */
public interface Function {

  public default boolean inDomain (final Vektor x) {
    return domainDimension() == x.dimension(); }

  public default boolean inCodomain (final Vektor y) {
    return codomainDimension() == y.dimension(); }


  /** Which <b>R</b><sup>n</sup> is the domain of this */
  int domainDimension ();

  /** Which <b>R</b><sup>m</sup> is the codomain of this */
  int codomainDimension ();
  
  public default boolean inDomain (final double[] x) {
    return domainDimension() == x.length; }

  public default boolean inCodomain (final double[] y) {
    return codomainDimension() == y.length; }

  //--------------------------------------------------------------
  // general methods
  //--------------------------------------------------------------
  // WARNING: using mutable args to Function.value(), etc.,
  // means we can't memoize them.

  /** <code>y = f(x)</code>
   * <b>WARNING:</b> overwrites <code>y</code>!
   */

  public default void value (final double[] x,
                             final double[] y) {
    assert inDomain(x);
    assert inCodomain(y);
    throw new UnsupportedOperationException(
      "No value(double[],double[]]) method for " + getClass()); }

  /** Return <code>y = f(x)</code>
   */

  public default double[] value (final double[] x) {
    assert inDomain(x);
    final double[] y = new double[codomainDimension()];
    value(x,y);
    return y; }

  /** Return the linear function that is the derivative of this at 
   * <code>x</code>.
   */

  public default Function derivativeAt (final double[] x) {
    assert inDomain(x);
    throw new UnsupportedOperationException(
      "No dertivativeAt(double[]) method for " + getClass()); }

  //--------------------------------------------------------------
  // 1d codomain methods
  //--------------------------------------------------------------
  /** Return the value of the function at <code>x</code>.
   */

  public default double doubleValue (final double[] x) {
    assert inDomain(x);
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No doubleValue(double[]) method for " + getClass()); }

  //--------------------------------------------------------------
  /** Overwrite <code>g</code> with the gradient of the function
   * at <code>x</code>.
   */

  public default void gradient (final double[] x, 
                                final double[] g) {
    assert inDomain(x);
    assert inDomain(g);
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No gradient(double[],double[]) method for " + getClass()); }

  /** Return the gradient of this at <code>x</code>.
   */

  public default double[] gradient (final double[] x) {
    assert inDomain(x);
    assert 1 == codomainDimension();
    final double[] g = new double[domainDimension()];
    gradient(x,g);
    return g; }

  //--------------------------------------------------------------
  /** Return the value of the function at <code>x</code>
   * and overwrite <code>g</code> with the gradient there.
   * <p>
   * It's fairly common that computing the value and gradient
   * together is much cheaper than separately.
   * <p>
   * TODO: measure the performance improvement for some real
   * examples.
   * <p>
   * TODO: consider returning a new <code>double[]</code> instead.
   */
  
  public default double valueAndGradient (final double[] x,
                                          final double[] g) {
    assert inDomain(x);
    assert inDomain(g);
    assert 1 == codomainDimension();
    gradient(x,g);
    return doubleValue(x); }

  /** Return the value of the function at <code>x</code>
   * and overwrite <code>g</code> with the gradient there
   * (1d domain case).
   * <p>
   * It's fairly common that computing the value and gradient
   * together is much cheaper than separately.
   * <p>
   * TODO: measure the performance improvement for some real
   * examples.
   * <p>
   * TODO: consider returning a new <code>double[]</code> instead.
   */
//  public default double valueAndGradient (final double x,
//                                          final double[] g) {
//  assert inDomain(g);
//  assert 1 == codomainDimension();
//    return valueAndGradient(g,new double[] {x}); }

  //--------------------------------------------------------------
  // 1d domain and codomain methods
  //--------------------------------------------------------------
  /** Return the value of the function at <code>x</code>
   * (1d domain case).
   */
  public default double doubleValue (final double x) {
    assert 1 == domainDimension();
    assert 1 == codomainDimension();
    return doubleValue(new double[] {x}); }

  /** Return the slope of the function at <code>x</code>
   * (1d domain case).
   */
  @SuppressWarnings("unused")
  public default double slope (final double x) {
    assert 1 == domainDimension();
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No slope(double) method for " + getClass()); }

  //--------------------------------------------------------------
  // general methods
  //--------------------------------------------------------------
  // WARNING: using mutable args to Function.value(), etc.,
  // means we can't memoize them.
  // TODO: replace double[] with MutableVektor?

  /** Return <code>f(x)</code>
   */

  public default Vektor value (final Vektor x) {
    assert inDomain(x);
    throw new UnsupportedOperationException(
      "No value(Vektor) method for " + getClass()); }

  //--------------------------------------------------------------
  /** Return the linear function that is the derivative of this 
   * at <code>x</code>.
   */

  public default Function derivativeAt (final Vektor x) {
    assert inDomain(x);
    throw new UnsupportedOperationException(
      "No derivativeAt(Vektor) method for " + getClass()); }

  /** Return the affine function that is the tangent of this 
   * at <code>x</code>.
   */

  public default Function tangent (final Vektor x) {
    assert inDomain(x);
    throw new UnsupportedOperationException(
      "No tangent(Vektor) method for " + getClass()); }

  //--------------------------------------------------------------
  // 1d codomain methods
  //--------------------------------------------------------------
  /** Return the value of the function at <code>x</code>.
   */

  public default double doubleValue (final Vektor x) {
    assert inDomain(x);
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No doubleValue(Vektor) method for " + getClass()); }

  //--------------------------------------------------------------
  /** Overwrite <code>g</code> with the gradient of the function
   * at <code>x</code>.
   * <p>
   * TODO: consider returning a new <code>double[]</code> instead.
   */

  public default Vektor gradient (final Vektor x) {
    assert inDomain(x);
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No gradient(double[],double[]) method for " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

