package mudstone.java.prng;

import java.util.List;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CollectionSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousUniformSampler;

/** 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-28
 */
@SuppressWarnings("unchecked")
public interface BigFractionSampler extends NumberSampler {

  BigFraction sample ();  

  @Override
  public default Number next () { return sample();  } 

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
 
  public static BigFractionSampler 
  make (final UniformRandomProvider urp) {
    final double dp = 0.9;
    return new BigFractionSampler () {
      private final ContinuousSampler choose = 
        new ContinuousUniformSampler(urp,0.0,1.0);
      private final ContinuousSampler cs = 
        DoubleSampler.make(urp);
      private final CollectionSampler edgeCases = 
        new CollectionSampler(
          urp,
          List.of(
            BigFraction.ZERO,
            BigFraction.ONE,
            BigFraction.MINUS_ONE));
      @Override
      public BigFraction sample () { 
        final boolean edge = choose.sample() > dp;
        if (edge) { return (BigFraction) edgeCases.sample(); }
        for (;;) { // WARNING: intinite loop?
          final double z = cs.sample();
          if (Double.isFinite(z)) { 
            return new BigFraction(z); } } } };
  }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

