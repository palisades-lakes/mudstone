package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;

import mudstone.java.numbers.Doubles;

/** Return any possible <code>double</code> with equal 
 * probability.
 * 
 * Most random number generators attempt to implement something
 * approximating:
 * sample a <em>real</code> number from some probability measure
 * on the real line, round to the nearest <code>double</code>,
 * and return that.
 * 
 * This sampler treats the set of possible <code>double</code>
 * values as a discrete finite set, and returns any element of
 * the set with equal probability.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-29
 */
public final class DoubleSampler 
implements ContinuousSampler, NumberSampler {

  private final UniformRandomProvider _urp;
  private final int _delta;

  //--------------------------------------------------------------
  // TODO: Does this sample distinct double *values* with equal
  // probability, or distinct bit patterns, where multiple bit 
  // patterns may have equal values?
  // TODO: What about NaN? corresponds to multiple bit patterns
  @Override
  public final double sample () {
    return
      Doubles.makeDouble(
        _urp.nextInt(2),
        _urp.nextInt(_delta),
        _urp.nextLong() & Doubles.SIGNIFICAND_MASK);  }

  @Override
  public final Number next () {
    return Double.valueOf(sample());  }

  //--------------------------------------------------------------

  private DoubleSampler (final UniformRandomProvider urp,
                         final int delta) {
    _delta = delta;
    _urp = urp; }

  /** Sample random double values, with equal probability for
   * any double value whose
   * exponent &lt; <code>delta</code>.
   */

  public static final DoubleSampler 
  make (final UniformRandomProvider urp,
        final int delta) {
    return new DoubleSampler(urp,delta); }
  
  /** Sample random double values, with equal probability for
   * any double value.
   */
  public static final DoubleSampler 
  make (final UniformRandomProvider urp) {
    return DoubleSampler.make(
      urp,
      1+Doubles.MAXIMUM_BIASED_EXPONENT); }
  
  public static final DoubleSampler 
  make (final int delta) {
    return DoubleSampler.make(
      PRNG.well44497b(
        Seeds.seed("Well44497b-2019-01-05.txt")),
      delta); }
  
  public static final DoubleSampler 
  make () {
    return DoubleSampler.make(
      PRNG.well44497b(
        Seeds.seed("Well44497b-2019-01-05.txt"))); }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

