package mudstone.java.algebra;

import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import mudstone.java.sets.Set;

/** Algebraic structures are defined as a set (or 3) with
 * 'operations', which are usually functions of two arguments
 * between the sets. What kind of algebraic structure we have is
 * determined by predicates satisfied by the operations.
 * This class defines a number of relevant predicates that can be
 * used in generative testing to check whether an implementation
 * seems to satisfy the necessary properties or not.
 *
 * Constants and class (static) methods only; 
 * no instance state or methods.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-08
 */

@SuppressWarnings("unchecked")
public final class Operations {

  // TODO: rely on operation for assertions?
  //--------------------------------------------------------------
  /** Is the value of the operation an element of the structure?
   */
  public final static boolean isClosed (final Set elements,
                                        final BinaryOperator operation,
                                        final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    final Object b = samples.next();
    assert elements.contains(b);
    return elements.contains(operation.apply(a,b)); }

  //--------------------------------------------------------------
  /** Is the operation associative?
   */

  public final static boolean isAssociative (final Set elements,
                                             final BinaryOperator operation,
                                             final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    final Object b = samples.next();
    assert elements.contains(b);
    final Object c = samples.next();
    assert elements.contains(c);
    final BiPredicate equal = elements.equivalence();
    return 
      equal.test(
        operation.apply(a,operation.apply(b,c)),
        operation.apply(operation.apply(a,b),c)); }

  //--------------------------------------------------------------
  // TODO: right identity vs left identity?

  /** Does <code>(operation a identity) == 
   * (operation identity a) = a</code>?
   */
  public final static boolean isIdentity (final Set elements,
                                          final BinaryOperator operation,
                                          final Object identity,
                                          final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    assert elements.contains(identity);
    final Object r = operation.apply(a,identity);
    final Object l = operation.apply(identity,a);
    final BiPredicate equal = elements.equivalence();
    return equal.test(a,r) && equal.test(a,l); }

  //--------------------------------------------------------------
  // TODO: right inverses vs left inverses?

  /** Does <code>(operation a (inverse a)) == 
   * (operation (inverse a) a) = identity</code>?
   */
  public final static boolean isInverse (final Set elements,
                                         final BinaryOperator operation,
                                         final Object identity,
                                         final UnaryOperator inverse,
                                         final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    assert elements.contains(identity);
    final Object ainv = inverse.apply(a);
    final BiPredicate equal = elements.equivalence();
    return 
      equal.test(identity,operation.apply(a,ainv))
      && 
      equal.test(identity,operation.apply(ainv,a)); }

  //--------------------------------------------------------------
  /** Is the operation commutative (aka symmetric)?
   */

  public final static boolean isCommutative (final Set elements,
                                             final BinaryOperator operation,
                                             final Iterator samples) {

    final Object a = samples.next();
    assert elements.contains(a);
    final Object b = samples.next();
    assert elements.contains(b);
    final BiPredicate equal = elements.equivalence();
    return 
      equal.test(
        operation.apply(a,b),
        operation.apply(b,a)); }

  //--------------------------------------------------------------
  /** Does <code>multiply</code> distribute over <code>add</code>?
   * <code>a*(b+c) == (a*b) + (a*c)</code>?
   */

  public final static boolean isDistributive (final Set elements,
                                              final BinaryOperator add,
                                              final BinaryOperator multiply,
                                              final Iterator samples) {

    final Object a = samples.next();
    assert elements.contains(a);
    final Object b = samples.next();
    assert elements.contains(b);
    final Object c = samples.next();
    assert elements.contains(c);
    final BiPredicate equal = elements.equivalence();
    return 
      equal.test(
        multiply.apply(a,add.apply(b,c)),
        add.apply(multiply.apply(a,b),multiply.apply(a,c))); }

  //--------------------------------------------------------------
  // disable constructor
  //--------------------------------------------------------------

  private Operations () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
