package mudstone.java.prng;

import static mudstone.java.prng.Generator.bigFractionGenerator;
import static mudstone.java.prng.Generator.byteGenerator;
import static mudstone.java.prng.Generator.doubleGenerator;
import static mudstone.java.prng.Generator.finiteDoubleGenerator;
import static mudstone.java.prng.Generator.finiteFloatGenerator;
import static mudstone.java.prng.Generator.floatGenerator;
import static mudstone.java.prng.Generator.intGenerator;
import static mudstone.java.prng.Generator.longGenerator;
import static mudstone.java.prng.Generator.shortGenerator;

import java.util.List;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CollectionSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousUniformSampler;

import mudstone.java.exceptions.Exceptions;
import mudstone.java.numbers.Doubles;
import mudstone.java.numbers.Floats;

/** Generators of primitives or Objects as zero-arity 'functions'
 * that return different values on each call.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-29
 */

@SuppressWarnings("unchecked")
public interface Generator {

  // default methods throw UnsupportetOperationException.

  public default Object next () {
    throw Exceptions.unsupportedOperation(this,"next"); }

  public default boolean nextBoolean () {
    throw Exceptions.unsupportedOperation(this,"nextBoolean"); }

  public default byte nextByte () {
    throw Exceptions.unsupportedOperation(this,"nextByte"); }

  public default char nextChar () {
    throw Exceptions.unsupportedOperation(this,"nextChar"); }

  public default short nextShort () {
    throw Exceptions.unsupportedOperation(this,"nextShort"); }

  public default int nextInt () {
    throw Exceptions.unsupportedOperation(this,"nextInt"); }

  public default long nextLong () {
    throw Exceptions.unsupportedOperation(this,"nextLong"); }

  public default float nextFloat () {
    throw Exceptions.unsupportedOperation(this,"nextFloat"); }

  public default double nextDouble () {
    throw Exceptions.unsupportedOperation(this,"nextDouble"); }

  //--------------------------------------------------------------

  public static Generator 
  byteGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      @Override
      public final byte nextByte () { return (byte) urp.nextInt(); } 
      @Override
      public final Object next () {
        return Byte.valueOf(nextByte()); } }; }

  public static Generator 
  shortGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      @Override
      public final short nextShort () { return (short) urp.nextInt(); } 
      @Override
      public final Object next () {
        return Short.valueOf(nextShort()); } }; }

  public static Generator 
  intGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      @Override
      public final int nextInt () { return urp.nextInt(); } 
      @Override
      public final Object next () {
        return Integer.valueOf(nextInt()); } }; }

  public static Generator 
  longGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      @Override
      public final long nextLong () { return urp.nextLong(); } 
      @Override
      public final Object next () {
        return Long.valueOf(nextLong()); } }; }

  public static Generator 
  floatGenerator (final UniformRandomProvider urp,
                  final int delta) {
    return new Generator () {
      @Override
      public final float nextFloat () { 
        return Floats.makeFloat(
          urp.nextInt(2),
          urp.nextInt(delta),
          urp.nextInt() & Floats.SIGNIFICAND_MASK); } 
      @Override
      public final Object next () {
        return Float.valueOf(nextFloat()); } }; }

  public static Generator 
  floatGenerator (final UniformRandomProvider urp) {
    return 
      floatGenerator(urp,1+Floats.MAXIMUM_BIASED_EXPONENT); } 

  public static Generator 
  doubleGenerator (final UniformRandomProvider urp,
                   final int delta) {
    return new Generator () {
      @Override
      public final double nextDouble () { 
        return Doubles.makeDouble(
          urp.nextInt(2),
          urp.nextInt(delta),
          urp.nextLong() & Doubles.SIGNIFICAND_MASK); } 
      @Override
      public final Object next () {
        return Double.valueOf(nextDouble()); } }; }

  public static Generator 
  doubleGenerator (final UniformRandomProvider urp) {
    return 
      doubleGenerator(urp,1+Doubles.MAXIMUM_BIASED_EXPONENT); } 

  //--------------------------------------------------------------

  public static Generator 
  finiteFloatGenerator (final UniformRandomProvider urp,
                        final int delta) {
    final Generator f = floatGenerator(urp,delta);
    return new Generator () {
      @Override
      public final float nextFloat () {
        // TODO: fix infinite loop
        for (;;) {
          final float x = f.nextFloat();
          if (Float.isFinite(x)) { return x; } } } 
      @Override
      public final Object next () {
        return Float.valueOf(nextFloat()); } }; }

  public static Generator 
  finiteFloatGenerator (final UniformRandomProvider urp) {
    return 
      finiteFloatGenerator(urp,1+Floats.MAXIMUM_BIASED_EXPONENT); } 

  public static Generator 
  finiteDoubleGenerator (final UniformRandomProvider urp,
                         final int delta) {
    final Generator d = doubleGenerator(urp,delta);
    return new Generator () {
      @Override
      public final double nextDouble () {
        // TODO: fix infinite loop
        for (;;) {
          final double x = d.nextDouble();
          if (Double.isFinite(x)) { return x; } } } 
      @Override
      public final Object next () {
        return Long.valueOf(nextLong()); } }; }

  public static Generator 
  finiteDoubleGenerator (final UniformRandomProvider urp) {
    return 
      finiteDoubleGenerator(
        urp,1+Doubles.MAXIMUM_BIASED_EXPONENT); } 

  //--------------------------------------------------------------
  // TODO: options?
  // TODO: using a DoubleSampler: those are (?) the most likely
  // values to see, but could do something to extend the 
  // range to values not representable as double.

  /** Intended primarily for testing. Sample a random double
   * (see {@link mudstone.java.prng.DoubleSampler})
   * and convert to <code>BigFraction</code>
   * with {@link #DOUBLE_P} probability;
   * otherwise return {@link BigFraction#ZERO} or 
   * {@link BigFractrion#ONE}, {@link BigFractrion#MINUS_ONE},  
   * with equal probability (these are potential edge cases).
   */

  public static Generator 
  bigFractionGenerator (final UniformRandomProvider urp) {
    final double dp = 0.9;
    return new Generator () {
      private final ContinuousSampler choose = 
        new ContinuousUniformSampler(urp,0.0,1.0);
      private final Generator fdg = finiteDoubleGenerator(urp);
      private final CollectionSampler edgeCases = 
        new CollectionSampler(
          urp,
          List.of(
            BigFraction.ZERO,
            BigFraction.ONE,
            BigFraction.MINUS_ONE));
      @Override
      public Object next () { 
        final boolean edge = choose.sample() > dp;
        if (edge) { return edgeCases.sample(); }
        return new BigFraction(fdg.nextDouble()); } };
  }

  //--------------------------------------------------------------
  // TODO: options?
  // TODO: using a DoubleSampler: those are (?) the most likely
  // values to see, but could do something to extend the 
  // range to values not representable as double.

  /** Intended primarily for testing. Sample a random double
   * (see {@link mudstone.java.prng.DoubleSampler})
   * and convert to <code>BigFraction</code>
   * with {@link #DOUBLE_P} probability;
   * otherwise return {@link BigFraction#ZERO} or 
   * {@link BigFractrion#ONE}, {@link BigFractrion#MINUS_ONE},  
   * with equal probability (these are potential edge cases).
   */

  public static Generator 
  numberGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      private final CollectionSampler<Generator> generators = 
        new CollectionSampler(
          urp,
          List.of(
            byteGenerator(urp),
            shortGenerator(urp),
            intGenerator(urp),
            longGenerator(urp),
            floatGenerator(urp),
            doubleGenerator(urp),
            bigFractionGenerator(urp)));
      @Override
      public final Object next () {
        return generators.sample().next(); } }; }


  /** Intended primarily for testing. Sample a random double
   * (see {@link mudstone.java.prng.DoubleSampler})
   * and convert to <code>BigFraction</code>
   * with {@link #DOUBLE_P} probability;
   * otherwise return {@link BigFraction#ZERO} or 
   * {@link BigFractrion#ONE}, {@link BigFractrion#MINUS_ONE},  
   * with equal probability (these are potential edge cases).
   */

  public static Generator 
  finiteNumberGenerator (final UniformRandomProvider urp) {
    return new Generator () {
      private final CollectionSampler<Generator> generators = 
        new CollectionSampler(
          urp,
          List.of(
            byteGenerator(urp),
            shortGenerator(urp),
            intGenerator(urp),
            longGenerator(urp),
            finiteFloatGenerator(urp),
            finiteDoubleGenerator(urp),
            bigFractionGenerator(urp)));
      @Override
      public final Object next () {
        return generators.sample().next(); } }; }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

