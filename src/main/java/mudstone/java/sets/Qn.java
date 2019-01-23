package mudstone.java.sets;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;

import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;

import mudstone.java.exceptions.Exceptions;

/** The set of arrays of some fixed length <code>n</code>,
 *  of primitive numbers, or 
 * of certain subclasses of <code>Number</code>,
 * interpreted as tuples of rational numbers.
 * 
 * This is primarily intended to support implementing the standard
 * <em>rational</em> linear space <b>Q</b><sup>n</sup>.
 * (Essentially <b>R</b><sup>n</sup> except over the rational
 * numbers rather than the reals.)

 * TODO: generalize to tuples 
 * implemented with lists, <code>int</code> indexed maps
 * for sparse vectors, etc.
 * Long term goal is to support any useful data structures
 * that can be used to represent tuples of rational numbers.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-22
 */
@SuppressWarnings("unchecked")
public final class Qn implements Set {

  //--------------------------------------------------------------
  // convert representation to BigFraction[] as default.
  // higher performance methods use raw representation where
  // computations are exact.
  //--------------------------------------------------------------

  private static final BigFraction[]
    toBigFractions (final byte[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final short[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final int[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final long[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final float[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final double[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[]
    toBigFractions (final Number[] x) {
    final int n = x.length;
    final BigFraction[] y = new BigFraction[n];
    for (int i=0;i<n;i++) { y[i] = Q.toBigFraction(x[i]); }
    return y; }

  private static final BigFraction[] 
    toBigFractions (final Object x) {

    if (x instanceof BigFraction[]) { 
      return (BigFraction[]) x; }

    if (x instanceof byte[]) { 
      return toBigFractions((byte[]) x); }

    if (x instanceof short[]) { 
      return toBigFractions((short[]) x); }

    if (x instanceof int[]) { 
      return toBigFractions((int[]) x); }

    if (x instanceof long[]) { 
      return toBigFractions((long[]) x); }

    if (x instanceof float[]) { 
      return toBigFractions((float[]) x); }

    if (x instanceof double[]) { 
      return toBigFractions((double[]) x); }

    if (x instanceof Number[]) { 
      return toBigFractions((Number[]) x); }

    throw Exceptions.unsupportedOperation(
      Qn.class,"toBigFractions",x); }

  //--------------------------------------------------------------
  // Set methods
  //--------------------------------------------------------------
  private final int _dimension;
  public final int dimension () { return _dimension; }

  private final BiPredicate
  _equivalence =
  new BiPredicate() {
    @Override
    public final boolean test (final Object x0, 
                               final Object x1) {
      assert contains(x0);
      assert contains(x1);

      // TODO: don't need to convert, fix this
      final BigFraction[] q0 = toBigFractions(x0);
      final BigFraction[] q1 = toBigFractions(x1);
      assert _dimension == q0.length;
      assert _dimension == q1.length;
      for (int i=0;i<_dimension;i++) {
        if (! BigFractions.equalBigFractions(q0[i],q1[i])) {
          return false; } }
      return true;
    }
  };

  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object element) {
    if (null == element) { return false; }
    final Class c = element.getClass();
    if (! c.isArray()) { return false; }
    if (! Q.knownRational(c.getComponentType())) { return false; }
    if (_dimension != Array.getLength(element)) { return false; }
    return true; }

  @Override
  public final BiPredicate equivalence () { return _equivalence; }

  //--------------------------------------------------------------
  // TODO: need generators for all array types.

  @Override
  public final Supplier generator (final UniformRandomProvider urp,
                                   final Map options) {
    final Supplier bf = BigFractions.get().generator(urp,options);
    return 
      new Supplier () {
      // TODO: replace Supplier with an interface that has 
      // generateArray(), generateList(), etc, methods.
      @Override
      public final Object get () { 
        final BigFraction[] q = new BigFraction[_dimension];
        for (int i=0;i<_dimension;i++) { 
          q[i] = (BigFraction) bf.get(); }
        return q; } }; }

  @Override
  public final Supplier generator (final UniformRandomProvider urp) {
    return generator(urp,Collections.emptyMap()); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () { return _dimension; }

  // singleton
  @Override
  public final boolean equals (final Object that) {
    return 
      (that instanceof Qn)
      &&
      (_dimension == ((Qn) that)._dimension); }

  @Override
  public final String toString () { return "BigFractions"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------
  // TODO: support zero-dimensional space?

  private Qn (final int dimension) { 
    assert dimension > 0;
    _dimension = dimension; }

  private static final IntObjectMap<Qn> _cache = 
    new IntObjectHashMap();

  public static final Qn get (final int dimension) {
    final Qn dn0 = _cache.get(dimension);
    if (null != dn0) { return dn0; }
    final Qn dn1 = new Qn(dimension); 
    _cache.put(dimension,dn1);
    return dn1; }

  //--------------------------------------------------------------
  // operations for algebraic structures over BigFraction tuples.
  //--------------------------------------------------------------
  /** A <code>BinaryOperator</code> that adds elementwise
   * <code>BigFraction[]</code> instances of length 
   * <code>dimension</code>.
   */

  public static final BinaryOperator 
  adder (final int dimension) {
    assert dimension > 0;
    return
      new BinaryOperator () {
      @Override
      public final BigFraction[] apply (final Object x0, 
                                        final Object x1) {
        assert null != x0;
        assert null != x1;
        // TODO: conversion rarely necessary, fix this
        final BigFraction[] q0 = toBigFractions(x0);
        final BigFraction[] q1 = toBigFractions(x1);
        assert dimension == q0.length;
        assert dimension == q1.length;
        final BigFraction[] qq = new BigFraction[dimension];
        for (int i=0;i<dimension;i++) { qq[i] = q0[i].add(q1[i]); }
        return qq; } }; }

  // TODO: special sparse representation for zero vector?

  public static final Object 
  additiveIdentity (final int dimension) {
    assert dimension > 0;
    final BigFraction[] qq = new BigFraction[dimension];
    Arrays.fill(qq,BigFraction.ZERO);
    return qq; }

  public static final UnaryOperator
  additiveInverse (final int dimension) {
    assert dimension > 0;
    return 
      new UnaryOperator() {
      @Override
      public final Object apply (final Object x) {
        assert null != x;
        // TODO: direct negation to BigFraction[] 
        // saves intermediate array, fix this
        final BigFraction[] q = toBigFractions(x);
        assert dimension == q.length;
        final BigFraction[] qq = new BigFraction[dimension];
        for (int i=0;i<dimension;i++) { qq[i] = q[i].negate(); }
        return qq; } }; }

  public static final 
  BiFunction scaler (final int dimension) {
    assert dimension > 0;
    return
      new BiFunction() {
      @Override
      public final Object apply (final Object a, 
                                 final Object x) {
        // TODO: conversion expensive and rarely necessary
        // fix this
        final BigFraction qa =Q.toBigFraction(a);
        assert null != x;
        final BigFraction[] q = toBigFractions(x);
        assert dimension == q.length;
        final BigFraction[] qq = new BigFraction[dimension];
        for (int i=0;i<dimension;i++) { 
          qq[i] = q[i].multiply(qa); }
        return qq; } }; }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
