package mudstone.java.sets;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.distribution.ContinuousUniformSampler;

import clojure.lang.Keyword;

/** The set of rational numbers represented by 
 * <code>BigFraction</code>
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-03
 */
public final class BigFractions implements Set {

  //--------------------------------------------------------------

  @Override
  public final boolean contains (final Object element) {
    return element instanceof BigFraction; }

  //--------------------------------------------------------------
  // convert from a random double
  // TODO: generate random BigIntegers for numerator 
  // and denominator?

  private static final Keyword LOWER = Keyword.intern("lower");
  private static final Keyword UPPER = Keyword.intern("upper");

  private static final ContinuousUniformSampler 
  doubleSampler (final UniformRandomProvider prng,
                 final Map options) {
    final double lower;
    if (options.containsKey(LOWER)) {
      lower = ((Number) options.get(LOWER)).doubleValue(); }
    else { lower = -Double.MAX_VALUE; }
    final double upper;
    if (options.containsKey(UPPER)) {
      upper = ((Number) options.get(UPPER)).doubleValue(); }
    else { upper = Double.MAX_VALUE; }

    return new ContinuousUniformSampler(prng,lower,upper); }

  //--------------------------------------------------------------

  @Override
  public final Object sample (final UniformRandomProvider prng,
                              final Map options) {
    return 
      new BigFraction(doubleSampler(prng,options).sample()); }

  //--------------------------------------------------------------

  @Override
  public final Object sample (final UniformRandomProvider prng) {
    return sample(prng,Collections.emptyMap()); }

  //--------------------------------------------------------------

  @Override
  public final Iterator sampler (final UniformRandomProvider prng,
                                 final Map options) {
    final ContinuousUniformSampler cus = 
      doubleSampler(prng,options);
    return 
      new Iterator () {
      @Override
      public final boolean hasNext () { return true; }
      @Override
      public final Object next () { 
        return new BigFraction(cus.sample()); } }; }

  @Override
  public final Iterator sampler (final UniformRandomProvider prng) {
    return sampler(prng,Collections.emptyMap()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

