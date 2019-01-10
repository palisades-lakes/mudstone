package mudstone.java.algebra;

import java.util.Iterator;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.sets.Set;

/** Predicates on sets and operations.
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
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-09
 */
public interface Law {

  public default boolean value (final Set elements,
                                final Iterator sampler) {
    throw Exceptions.unsupportedOperation(
      this,"value",elements,sampler); }

  public default boolean value (final Set elements,
                                final BinaryOperator op,
                                final Iterator sampler) {
    throw Exceptions.unsupportedOperation(
      this,"value",elements,op,sampler); }

  public default boolean value (final Set elements,
                                final BinaryOperator op,
                                final Object identity,
                                final Iterator sampler) {
    throw Exceptions.unsupportedOperation(
      this,"value",elements,op,identity,sampler); }

  public default boolean value (final Set elements,
                                final BinaryOperator op,
                                final Object identity,
                                final UnaryOperator inverse,
                                final Iterator sampler) {
    throw Exceptions.unsupportedOperation(
      this,"value",elements,op,identity,inverse,sampler); }

  public default boolean value (final Set elements,
                                final BinaryOperator add,
                                final BinaryOperator mul,
                                final Iterator sampler) {
    throw Exceptions.unsupportedOperation(
      this,"value",elements,add,mul,sampler); }

  //--------------------------------------------------------------
  // Useful laws
  //--------------------------------------------------------------

  public final static Law CLOSED = new Law () {
    @Override
    public final boolean value (final Set elements,
                                final BinaryOperator operation,
                                final Iterator samples) {
      final Object a = samples.next();
      assert elements.contains(a);
      final Object b = samples.next();
      assert elements.contains(b);
      return elements.contains(operation.apply(a,b)); } 
  }; 

  //--------------------------------------------------------------

  public final static Law ASSOCIATIVE = new Law () {
    @Override
    public final boolean value (final Set elements,
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
  };

  //--------------------------------------------------------------
  // TODO: right identity vs left identity?

  public final static Law IDENTITY = new Law () {
    @Override
    public final boolean value (final Set elements,
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
  };

  //--------------------------------------------------------------
  // TODO: right inverses vs left inverses?

  public final static Law INVERSE = new Law () {
    @Override
    public final boolean value (final Set elements,
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
  };

  //--------------------------------------------------------------

  public final static Law COMMUTATIVE = new Law () {
    @Override
    public final boolean value (final Set elements,
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
  };

  //--------------------------------------------------------------
  /** Does <code>multiply</code> distribute over <code>add</code>?
   * <code>a*(b+c) == (a*b) + (a*c)</code>?
   */

  public final static Law DISTRIBUTIVE = new Law () {
    @Override
    public final boolean value (final Set elements,
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
  };

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

