package mudstone.java.algebra;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;

import org.apache.commons.rng.UniformRandomProvider;

import mudstone.java.sets.BigFractions;
import mudstone.java.sets.Set;

/** Magma: set plus closed binary operation.
 * 
 * Not that useful (?), but a simple case for working out testing,
 * etc.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-07
 */
@SuppressWarnings("unchecked")
public final class Magma implements Set {

  private final BinaryOperator _operation;
  private final Set _elements;

  //--------------------------------------------------------------
  // methods 
  //--------------------------------------------------------------

  public final BinaryOperator operation () { return _operation; }

  public final Set elements () { return _elements; }

  //--------------------------------------------------------------
  // Set methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object x) {
    return _elements.contains(x); }

  @Override
  public final Iterator sampler (final UniformRandomProvider prng,
                                 final Map options) { 
    return _elements.sampler(prng,options); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () { 
    return Objects.hash(_operation,_elements); } 

  @Override
  public final boolean equals (final Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof Magma)) { return false; }
    Magma other = (Magma) obj;
    // WARNING: hard to tell if 2 operations are the same,
    // unless the implementing class has some kind of singleton
    // constraint.
    return 
      _operation.equals(other._operation)
      &&
      _elements.equals(other._elements); }

  @Override
  public final String toString () { 
    return "[" + _elements + "," + _operation + "]"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private Magma (final BinaryOperator operation,
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

  public static final Magma make (final BinaryOperator operation,
                                  final Set elements) {
    return new Magma(operation,elements); }

  //--------------------------------------------------------------
  // pre-define some standard magmas
  
  public static final Magma BIGFRACTIONS_ADD = 
    Magma.make(BigFractions.ADD,BigFractions.get());

  public static final Magma BIGFRACTIONS_MULTIPLY = 
    Magma.make(BigFractions.MULTIPLY,BigFractions.get());

  //--------------------------------------------------------------
}
