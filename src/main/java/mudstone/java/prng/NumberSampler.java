package mudstone.java.prng;

import java.util.List;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CollectionSampler;

/** Generate numbers, possibly of different types, for testing.
 * Usually give equal probability to all possible values,
 * or perhaps give higher probability to value likely to 
 * generate edge cases (zero, min, max, ...).
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-23
 */

@SuppressWarnings("unchecked")
public interface NumberSampler {

  Number next ();  

  /** For testing, sample all the supported Number types.
   */
  public static NumberSampler 
  make (final UniformRandomProvider urp) {
    return new NumberSampler () {
      private final CollectionSampler<NumberSampler> samplers = 
        new CollectionSampler(
          urp,
          List.of(
            ByteSampler.make(urp),
            ShortSampler.make(urp),
            IntegerSampler.make(urp),
            LongSampler.make(urp),
            FloatSampler.make(urp),
            DoubleSampler.make(urp),
            BigFractionSampler.make(urp)));
      @Override
      public final Number next () {
        return samplers.sample().next(); } }; }

  /** For testing, sample all the supported Number types, 
   * excluding infinite and NaN values.
   */
  public static NumberSampler 
  finite (final UniformRandomProvider urp) {
    return new NumberSampler () {
      private final CollectionSampler<NumberSampler> samplers = 
        new CollectionSampler(
          urp,
          List.of(
            ByteSampler.make(urp),
            ShortSampler.make(urp),
            IntegerSampler.make(urp),
            LongSampler.make(urp),
            FiniteFloatSampler.make(urp),
            FiniteDoubleSampler.make(urp),
            BigFractionSampler.make(urp)));
      @Override
      public final Number next () {
        return samplers.sample().next (); } }; }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

