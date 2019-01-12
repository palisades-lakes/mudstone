package mudstone.java.sets;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;

import clojure.lang.Keyword;
import mudstone.java.prng.DoubleSampler;

/** The set of rational numbers represented by 
 * <code>BigFraction</code>
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-11
 */
public final class BigFractions implements Set {

  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object element) {
    return element instanceof BigFraction; }

  private static final BiPredicate<BigFraction,BigFraction> 
  BIGFRACTION_EQUALS = 
  new BiPredicate<BigFraction,BigFraction>() {

    @Override
    public final boolean test (final BigFraction q0, 
                               final BigFraction q1) {
      return Objects.equals(q0,q1); }
  };
  
  @Override
  public final BiPredicate equivalence () {
    return BIGFRACTION_EQUALS; }

  //--------------------------------------------------------------
  // convert from a random double
  // TODO: generate random BigIntegers for numerator 
  // and denominator?

  //  private static final Keyword LOWER = Keyword.intern("lower");
  //  private static final Keyword UPPER = Keyword.intern("upper");
  //
  //  private static final ContinuousSampler 
  //  doubleSampler (final UniformRandomProvider prng,
  //                 final Map options) {
  //    final double lower;
  //    if (options.containsKey(LOWER)) {
  //      lower = ((Number) options.get(LOWER)).doubleValue(); }
  //    else { lower = -Double.MAX_VALUE; }
  //    final double upper;
  //    if (options.containsKey(UPPER)) {
  //      upper = ((Number) options.get(UPPER)).doubleValue(); }
  //    else { upper = Double.MAX_VALUE; }
  //
  //    return new ContinuousUniformSampler(prng,lower,upper); }

  private static final Keyword DELTA = Keyword.intern("delta");

  private static final ContinuousSampler 
  doubleSampler (final UniformRandomProvider urp,
                 final Map options) {

    if (options.containsKey(DELTA)) {
      return 
        DoubleSampler.make(
          urp,
          ((Number) options.get(DELTA)).intValue()); }

    return DoubleSampler.make(urp); }

  //--------------------------------------------------------------

  @Override
  public final Iterator sampler (final UniformRandomProvider urp,
                                 final Map options) {
    final ContinuousSampler cs = doubleSampler(urp,options);
    return 
      new Iterator () {
      @Override
      public final boolean hasNext () { return true; }
      @Override
      public final Object next () { 
        for (;;) { // WARNING: intinite loop?
          final double z = cs.sample();
          if (Double.isFinite(z)) { 
            return new BigFraction(z); } } } }; }

  @Override
  public final Iterator sampler (final UniformRandomProvider urp) {
    return sampler(urp,Collections.emptyMap()); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private BigFractions () { }

  private static final BigFractions SINGLETON = 
    new BigFractions();

  public static final BigFractions get () { return SINGLETON; } 

  public static final BinaryOperator<BigFraction> ADD =
    new BinaryOperator<BigFraction>() {
    @Override
    public final BigFraction apply (final BigFraction q0, 
                                    final BigFraction q1) {
      return q0.add(q1); } 
  };

  public static final UnaryOperator<BigFraction>
  ADDITIVE_INVERSE =
    new UnaryOperator<BigFraction>() {
    @Override
    public final BigFraction apply (final BigFraction q) {
      return q.negate(); } 
  };

  public static final BinaryOperator<BigFraction> MULTIPLY =
    new BinaryOperator<BigFraction>() {
    @Override
    public final BigFraction apply (final BigFraction q0, 
                                    final BigFraction q1) {
      return q0.multiply(q1); } 
  };

  public static final UnaryOperator<BigFraction>
  MULTIPLICATIVE_INVERSE =
    new UnaryOperator<BigFraction>() {
    @Override
    public final BigFraction apply (final BigFraction q) {
      // only a partial inverse
      if (BigFraction.ZERO.equals(q)) { return null; }
      return q.reciprocal(); } 
  };

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

