package mudstone.java.sets;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.prng.NumberSampler;

/** The set of rational numbers, accepting any 'reasonable' 
 * representation. Calculation converts to BigFraction where
 * necessary.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public final class Q implements Set {

  //--------------------------------------------------------------
  // class methods
  //--------------------------------------------------------------

  // All known java numbers are rational, meaning there's an 
  // exact, loss-less conversion to BigFraction, used by methods
  // below. But we can't know how to convert unknown
  // implementations of java.lang.Number, so we have to exclude 
  // those, for the start.
  // Also, we only want immutable classes here...
  // TODO: collect some stats and order tests by frequency?

  private static final boolean isKnownRational (final Object x) {
    return 
      (x instanceof BigFraction) 
      ||
      (x instanceof Double) 
      ||
      (x instanceof Integer) 
      ||
      (x instanceof Long) 
      ||
      (x instanceof Float) 
      ||
      (x instanceof Short) 
      ||
      (x instanceof Byte) 
      ||
      (x instanceof BigInteger); }

  //--------------------------------------------------------------

  private static final BigFraction toBigFraction (final Object x) {
    assert isKnownRational(x) : 
      x + " is not a known rational number type";
    if (x instanceof BigFraction) { 
      return (BigFraction) x; }
    else if (x instanceof Double) { 
      return new BigFraction(((Double) x).doubleValue()); }
    else if (x instanceof Integer) {
      return new BigFraction(((Integer) x).intValue()); }
    else if (x instanceof Long) { 
      return new BigFraction(((Long) x).longValue()); }
    else if (x instanceof Float) {
      return new BigFraction(((Float) x).floatValue()); }
    else if (x instanceof Short) {
      return new BigFraction(((Short) x).intValue()); }
    else if (x instanceof Byte) {
      return new BigFraction(((Byte) x).intValue()); }
    else if (x instanceof BigInteger) {
      return new BigFraction(((BigInteger) x)); }
    else {
      throw Exceptions.unsupportedOperation(
        Q.class,"toBigFraction",x); } }

  // BigFraction.equals reduces both arguments before checking
  // numerator and denominators are equal.
  // Guessing our BigFractions are usually already reduced.
  // Try n0*d1 == n1*d0 instead
  // TODO: try using BigINteger.bitLength() to decide
  // which method to use?

  private static final boolean 
  equalBigFractions (final BigFraction q0, 
                     final BigFraction q1) {
    if (q0 == q1) { return true; }
    if (null == q0) {
      if (null == q1) { return true; }
      return false; }
    final BigInteger n0 = q0.getNumerator(); 
    final BigInteger d0 = q0.getDenominator(); 
    final BigInteger n1 = q1.getNumerator(); 
    final BigInteger d1 = q1.getDenominator(); 
    return n0.multiply(d1).equals(n1.multiply(d0)); }

  private static final BiPredicate<Number,Number> EQUALS = 
    new BiPredicate<Number,Number>() {
    @Override
    public final boolean test (final Number x0, 
                               final Number x1) {
      final BigFraction q0 = toBigFraction(x0);
      final BigFraction q1 = toBigFraction(x1);
      return equalBigFractions(q0,q1); }
  };

  //--------------------------------------------------------------
  // Set methods
  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object element) {
    return isKnownRational(element); }

  //  @Override
  //  public final boolean contains (final byte element) {
  //    // all java numbers are rational
  //    return true; }
  //
  //  @Override
  //  public final boolean contains (final short element) {
  //    // all java numbers are rational
  //    return true; }
  //
  //  @Override
  //  public final boolean contains (final int element) {
  //    // all java numbers are rational
  //    return true; }
  //
  //  @Override
  //  public final boolean contains (final long element) {
  //    // all java numbers are rational
  //    return true; }
  //
  //  @Override
  //  public final boolean contains (final float element) {
  //    // all java numbers are rational
  //    return true; }
  //
  //  @Override
  //  public final boolean contains (final double element) {
  //    // all java numbers are rational
  //    return true; }

  //--------------------------------------------------------------

  @Override
  public final BiPredicate equivalence () { return EQUALS; }

  //--------------------------------------------------------------

  @Override
  public final Supplier generator (final UniformRandomProvider urp,
                                   final Map options) {
    final NumberSampler sampler = NumberSampler.finite(urp); 
    return 
      new Supplier () {
      @Override
      public final Object get () { return sampler.next(); } }; }

  @Override
  public final Supplier generator (final UniformRandomProvider urp) {
    return generator(urp,Collections.emptyMap()); }

  //--------------------------------------------------------------
  // Object methods
  //--------------------------------------------------------------

  @Override
  public final int hashCode () { return 0; }

  // singleton
  @Override
  public final boolean equals (final Object that) {
    return that instanceof Q; }

  @Override
  public final String toString () { return "BigFractions"; }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private Q () { }

  private static final Q SINGLETON = new Q();

  public static final Q get () { return SINGLETON; } 

  public static final BinaryOperator<Number> ADD =
    new BinaryOperator<Number>() {
    @Override
    public final BigFraction apply (final Number x0, 
                                    final Number x1) {
      final BigFraction q0 = toBigFraction(x0);
      final BigFraction q1 = toBigFraction(x1);
      return q0.add(q1); } 
  };

  public static final UnaryOperator<Number>
  ADDITIVE_INVERSE =
  new UnaryOperator<Number>() {
    @Override
    public final Number apply (final Number x) {
      final BigFraction q = toBigFraction(x);
      return q.negate(); } 
  };

  public static final BinaryOperator<Number> MULTIPLY =
    new BinaryOperator<Number>() {
    @Override
    public final BigFraction apply (final Number x0, 
                                    final Number x1) {
      final BigFraction q0 = toBigFraction(x0);
      final BigFraction q1 = toBigFraction(x1);
      return q0.multiply(q1); } 
  };

  public static final UnaryOperator<Number>
  MULTIPLICATIVE_INVERSE =
  new UnaryOperator<Number>() {
    @Override
    public final BigFraction apply (final Number x) {
      final BigFraction q = toBigFraction(x);
      // only a partial inverse
      if (BigFraction.ZERO.equals(q)) { return null; }
      return q.reciprocal(); } 
  };

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

