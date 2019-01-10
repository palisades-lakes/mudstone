package mudstone.java.algebra;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import mudstone.java.sets.Set;

/** Constructor methods for Predicates/BiPredicate closures on 
 * sets and operations.
 * 
 * Universal algebra approach: binary, unary, nullary ops
 * plus 'laws' involving universal quantifiers impose
 * constraint on ops. No existential quantifiers as in traditional
 * definitions makes testing easier --- can check universal
 * quantified predicate approximately, using samples, but no easy
 * way to even approximately determine the truth of 'there exists'
 * statements.
 * 
 * See https://en.wikipedia.org/wiki/Universal_algebra
 *
 * https://en.wikipedia.org/wiki/Outline_of_algebraic_structures
 * 
 * (TODO: will need a 'TriPredicate' for affine spaces, etc.).
 * 
 * Constants and class (static) methods only; 
 * no instance state or methods.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-10
 */

@SuppressWarnings("unchecked")
public final class Laws {

  //--------------------------------------------------------------
  /** Is the value of the operation an element of the structure?
   */
  public final static BiPredicate<Iterator,Iterator> 
  closed (final Set elements,
          final Set scalars,
          final BinaryOperator operation) {
    return new BiPredicate<Iterator,Iterator> () {
      @Override
      public final boolean test (final Iterator elementSamples,
                                 final Iterator scalarSamples) {
        final Object a = scalarSamples.next();
        assert scalars.contains(a);
        final Object b = elementSamples.next();
        assert elements.contains(b);
        return elements.contains(operation.apply(a,b)); } }; }

  /** Is the value of the operation an element of the structure?
   */
  public final static Predicate<Iterator> 
  closed (final Set elements,
          final BinaryOperator operation) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
        final Object a = samples.next();
        assert elements.contains(a);
        final Object b = samples.next();
        assert elements.contains(b);
        return elements.contains(operation.apply(a,b)); } }; }

  //--------------------------------------------------------------
  /** Is the operation associative?
   */

  public final static Predicate<Iterator>  
  associative (final Set elements,
               final BinaryOperator operation) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
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
            operation.apply(operation.apply(a,b),c)); } }; }

  //--------------------------------------------------------------
  // TODO: right identity vs left identity?

  /** Does <code>(operation a identity) == 
   * (operation identity a) = a</code>?
   */
  public final static Predicate<Iterator> 
  identity (final Set elements,
            final BinaryOperator operation,
            final Object identity) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
        final Object a = samples.next();
        assert elements.contains(a);
        assert elements.contains(identity);
        final Object r = operation.apply(a,identity);
        final Object l = operation.apply(identity,a);
        final BiPredicate equal = elements.equivalence();
        return equal.test(a,r) && equal.test(a,l); } }; }

  //--------------------------------------------------------------
  // TODO: right inverses vs left inverses?

  /** Does <code>(operation a (inverse a)) == 
   * (operation (inverse a) a) = identity</code>?
   * ...except for excluded elements, as with the additive
   * identity (zero) having no inverse element for the
   * multiplicative operation in a ring-like structure.
   */
  public final static Predicate<Iterator>  
  inverse (final Set elements,
           final BinaryOperator operation,
           final Object identity,
           final UnaryOperator inverse,
           final java.util.Set excluded) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
        final Object a = samples.next();
        if (excluded.contains(a)) { return true; }
        assert elements.contains(a);
        assert elements.contains(identity);
        final Object ainv = inverse.apply(a);
        final BiPredicate equal = elements.equivalence();
        return 
          equal.test(identity,operation.apply(a,ainv))
          && 
          equal.test(identity,operation.apply(ainv,a)); } }; }

  /** Does <code>(operation a (inverse a)) == 
   * (operation (inverse a) a) = identity</code>
   * for all <code>a</code> in <code>elements</code>?
   */
  public final static Predicate<Iterator>  
  inverse (final Set elements,
           final BinaryOperator operation,
           final Object identity,
           final UnaryOperator inverse) {
    return inverse(
      elements,operation,identity,inverse,java.util.Set.of()); }

  //--------------------------------------------------------------
  /** Is the operation commutative (aka symmetric)?
   */

  public final static Predicate<Iterator>  
  commutative (final Set elements,
               final BinaryOperator operation) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
        final Object a = samples.next();
        assert elements.contains(a);
        final Object b = samples.next();
        assert elements.contains(b);
        final BiPredicate equal = elements.equivalence();
        return 
          equal.test(
            operation.apply(a,b),
            operation.apply(b,a)); } }; }

  //--------------------------------------------------------------
  /** Does <code>multiply</code> distribute over <code>add</code>?
   * <code>a*(b+c) == (a*b) + (a*c)</code>?
   * (Module-like version)
   */

  public final static BiPredicate<Iterator,Iterator> 
  distributive (final Set elements,
                final Set scalars,
                final BinaryOperator add,
                final BinaryOperator multiply) {
    return new BiPredicate<Iterator,Iterator> () {
      @Override
      public final boolean test (final Iterator elementSamples,
                                 final Iterator scalarSamples) {

        final Object a = scalarSamples.next();
        assert scalars.contains(a);
        final Object b = elementSamples.next();
        assert elements.contains(b);
        final Object c = elementSamples.next();
        assert elements.contains(c);
        final BiPredicate equal = elements.equivalence();
        return 
          equal.test(
            multiply.apply(a,add.apply(b,c)),
            add.apply(
              multiply.apply(a,b),
              multiply.apply(a,c))); } }; }

  /** Does <code>multiply</code> distribute over <code>add</code>?
   * <code>a*(b+c) == (a*b) + (a*c)</code>?
   * (Ring-like version)
   */

  public final static Predicate<Iterator>  
  distributive (final Set elements,
                  final BinaryOperator add,
                  final BinaryOperator multiply) {
    return new Predicate<Iterator> () {
      @Override
      public final boolean test (final Iterator samples) {
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
            add.apply(
              multiply.apply(a,b),
              multiply.apply(a,c))); } }; }

  //--------------------------------------------------------------
  // by algebraic structure
  //--------------------------------------------------------------
 
  public static final List<Predicate> 
  magma  (final Set elements,
              final BinaryOperator operation) {
    return List.of(closed(elements,operation));}

  public static final List<Predicate> 
  semigroup  (final Set elements,
                  final BinaryOperator operation) {
    return List.of(
      closed(elements,operation),
      associative(elements,operation));}

  public static final List<Predicate> 
  monoid  (final Set elements,
               final BinaryOperator operation,
               final Object identity) {
    return List.of(
      closed(elements,operation),
      associative(elements,operation),
      identity(elements,operation,identity));}

  public static final List<Predicate> 
  group  (final Set elements,
              final BinaryOperator operation,
              final Object identity,
              final UnaryOperator inverse) {
    return List.of(
      closed(elements,operation),
      associative(elements,operation),
      identity(elements,operation,identity),
      inverse(elements,operation,identity,inverse));}

  public static final List<Predicate> 
  commutativegroup  (final Set elements,
                         final BinaryOperator operation,
                         final Object identity,
                         final UnaryOperator inverse) {
    return List.of(
      closed(elements,operation),
      associative(elements,operation),
      identity(elements,operation,identity),
      inverse(elements,operation,identity,inverse),
      commutative(elements,operation));}

  //--------------------------------------------------------------
  // disable constructor
  //--------------------------------------------------------------

  private Laws () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
