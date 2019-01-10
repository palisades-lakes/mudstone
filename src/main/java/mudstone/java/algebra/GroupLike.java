package mudstone.java.algebra;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;

import org.apache.commons.rng.UniformRandomProvider;

import mudstone.java.sets.BigFractions;
import mudstone.java.sets.Set;

/** Group-like structures: Set plus closed binary operation.
 * 
 * Not that useful (?), but a simple case for working out testing,
 * etc.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-09
 */
@SuppressWarnings("unchecked")
public final class GroupLike implements Set {

  private final BinaryOperator _operation;
  private final Set _elements;

  //--------------------------------------------------------------
  // methods 
  //--------------------------------------------------------------

  public final BinaryOperator operation () { return _operation; }
  public final Set elements () { return _elements; }

  //--------------------------------------------------------------
  // Laws (see https://en.wikipedia.org/wiki/Universal_algebra)
  //--------------------------------------------------------------

  public static final List<BiPredicate> MAGMA_LAWS =
    Arrays.asList(
      new BiPredicate[] 
        { (m, samples) -> {
          final GroupLike mm = (GroupLike) m;
          return 
            Operations.isClosed(
              mm.elements(),mm.operation(),(Iterator) samples); }, });

  //--------------------------------------------------------------
  // Set methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object x) {
    return _elements.contains(x); }

  // TODO: should there be an _equivalence slot?
  // instead of inheriting from _elements?
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
  // DANGER: relying on equivalence() returning equivalent object
  // each time

  @Override
  public final int hashCode () { 
    return Objects.hash(_operation,equivalence(),_elements); } 

  @Override
  public final boolean equals (final Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof GroupLike)) { return false; }
    GroupLike other = (GroupLike) obj;
    // WARNING: hard to tell if 2 operations are the same,
    // unless the implementing class has some kind of singleton
    // constraint.
    return 
      _operation.equals(other._operation)
      &&
      equivalence().equals(other.equivalence())
      &&
      _elements.equals(other._elements); }

  @Override
  public final String toString () { 
    return "[" + _elements 
      + "," + equivalence()
      + "," + _operation 
      + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private GroupLike (final BinaryOperator operation,
                 final Set elements) { 
    assert Objects.nonNull(operation);
    assert Objects.nonNull(elements);
    _operation = operation;
    _elements= elements; }

  //--------------------------------------------------------------
  // TODO: is it worth implementing singleton constraint?

  //  private static final Map<Magma,Magma> _cache = 
  //    new HashMap();

  //--------------------------------------------------------------

  public static final GroupLike make (final BinaryOperator operation,
                                  final Set elements) {
    return new GroupLike(operation,elements); }

  //--------------------------------------------------------------
  // pre-define some standard magmas

  public static final GroupLike BIGFRACTIONS_ADD = 
    GroupLike.make(BigFractions.ADD,BigFractions.get());

  public static final GroupLike BIGFRACTIONS_MULTIPLY = 
    GroupLike.make(BigFractions.MULTIPLY,BigFractions.get());

  //--------------------------------------------------------------
}
