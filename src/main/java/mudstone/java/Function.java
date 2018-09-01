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
 * @version 2018-09-01`
 */
public interface Function {

  // TODO: move to Domain objects
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

  //--------------------------------------------------------------
  /** Return the affine function that is the tangent of this 
   * at <code>x</code>.
   */

  public default Function tangent (final Vektor x) {
    assert inDomain(x);
    return AffineFunction.make(derivativeAt(x),value(x));}

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
  /** Return the gradient of the function at <code>x</code>.
   */

  public default Vektor gradient (final Vektor x) {
    assert inDomain(x);
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No gradient(Vektor) method for " + getClass()); }

  //--------------------------------------------------------------
  // 1d domain and codomain methods
  //--------------------------------------------------------------
  /** Return the value of the function at <code>x</code>
   * (1d domain case).
   */
  @SuppressWarnings("unused")
  public default double doubleValue (final double x) {
    assert 1 == domainDimension();
    assert 1 == codomainDimension();
    throw new UnsupportedOperationException(
      "No doubleValue(double) method for " + getClass()); }

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
}
//--------------------------------------------------------------

