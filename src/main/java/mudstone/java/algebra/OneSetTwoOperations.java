package mudstone.java.algebra;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;

import mudstone.java.sets.BigFractions;
import mudstone.java.sets.Set;

/** One set plus 2 operations.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-10
 */
@SuppressWarnings("unchecked")
public final class OneSetTwoOperations implements Set {

  private final BinaryOperator _add;
  // may be null
  private final Object _additiveIdentity;
  // may be null
  private final UnaryOperator _additiveInverse;
  private final BinaryOperator _multiply;
  // may be null
  private final Object _multiplicativeIdentity;
  // may be null
  private final UnaryOperator _multiplicativeInverse;

  private final Set _elements;

  //--------------------------------------------------------------
  // methods 
  //--------------------------------------------------------------

  public final BinaryOperator add () { return _add; }
  public final UnaryOperator additiveInverse () { return _additiveInverse; }
  // TODO: return a Supplier (nullary operator) instead?
  public final Object additiveIdentity () { return _additiveIdentity; }
  public final BinaryOperator multiply () { return _multiply; }
  public final UnaryOperator multiplicativeInverse () { return _multiplicativeInverse; }
  // TODO: return a Supplier (nullary operator) instead?
  public final Object multiplicativeIdentity () { return _multiplicativeIdentity; }
  public final Set elements () { return _elements; }

  //--------------------------------------------------------------
  // Set methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object x) {
    return _elements.contains(x); }

  @Override
  public final BiPredicate equivalence () {
    return _elements.equivalence(); }

  @Override
  public final Iterator sampler (final UniformRandomProvider prng,
                                 final Map options) { 
    return _elements.sampler(prng,options); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () { 
    return 
      Objects.hash(
        _add,
        _additiveIdentity,
        _additiveInverse,
        _multiply,
        _multiplicativeIdentity,
        _multiplicativeInverse,
        _elements); }

  @Override
  public boolean equals (Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final OneSetTwoOperations other = (OneSetTwoOperations) obj;
    if (! Objects.equals(_add,other._add)) { 
      return false; }
    if (! Objects.equals(
      _additiveIdentity,other._additiveIdentity)) { 
      return false; }
    if (! Objects.equals(
      _additiveInverse,other._additiveInverse)) { 
      return false; }
    if (! Objects.equals(_multiply,other._multiply)) {
      return false; }
    if (! Objects.equals(
      _multiplicativeIdentity,other._multiplicativeIdentity)) { 
      return false; }
    if (! Objects.equals
      (_multiplicativeInverse,other._multiplicativeInverse)) { 
      return false; }
    if (! Objects.equals
      (_elements,other._elements)) { 
      return false; }
    return true;
  }


  @Override
  public final String toString () { 
    return "[" + _elements + 
      ",\n" + _add + 
      "," + _additiveIdentity + 
      "," + _additiveInverse + 
      ",\n" + _multiply + 
      "," + _multiplicativeIdentity + 
      "," + _multiplicativeInverse + 
      "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------


  private OneSetTwoOperations (final BinaryOperator add,
                 final Object additiveIdentity,
                 final UnaryOperator additiveInverse,
                 final BinaryOperator multiply,
                 final Object multiplicativeIdentity,
                 final UnaryOperator multiplicativeInverse,
                 final Set elements) { 
    assert Objects.nonNull(add);
    assert Objects.nonNull(additiveIdentity);
    assert Objects.nonNull(additiveInverse);
    assert Objects.nonNull(multiply);
    assert Objects.nonNull(multiplicativeIdentity);
    assert Objects.nonNull(multiplicativeInverse);
    assert Objects.nonNull(elements);
    _add = add;
    _additiveIdentity = additiveIdentity;
    _additiveInverse = additiveInverse;
    _multiply = multiply;
    _multiplicativeIdentity = multiplicativeIdentity;
    _multiplicativeInverse = multiplicativeInverse;
    _elements= elements; }

  //--------------------------------------------------------------
  // TODO: is it worth implementing singleton constraint?

  //  private static final Map<Magma,Magma> _cache = 
  //    new HashMap();

  //--------------------------------------------------------------

  public static final OneSetTwoOperations make (final BinaryOperator add,
                                  final Object additiveIdentity,
                                  final UnaryOperator additiveInverse,
                                  final BinaryOperator multiply,
                                  final Object multiplicativeIdentity,
                                  final UnaryOperator multiplicativeInverse,
                                  final Set elements) {

    return new OneSetTwoOperations(
      add,
      additiveIdentity,
      additiveInverse,
      multiply,
      multiplicativeIdentity,
      multiplicativeInverse,
      elements); }

  //--------------------------------------------------------------
  // pre-define some standard magmas

  public static final OneSetTwoOperations BIGFRACTIONS_FIELD = 
    OneSetTwoOperations.make(
      BigFractions.ADD,
      BigFraction.ZERO,
      BigFractions.ADDITIVE_INVERSE,
      BigFractions.MULTIPLY,
      BigFraction.ONE,
      BigFractions.MULTIPLICATIVE_INVERSE,
      BigFractions.get());

  //--------------------------------------------------------------
}
