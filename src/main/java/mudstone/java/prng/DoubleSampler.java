package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;

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
 * @version 2019-01-05
 */
public final class DoubleSampler implements ContinuousSampler {

  private final UniformRandomProvider _urp;
  private final int _delta;

  //--------------------------------------------------------------
  // private
  //--------------------------------------------------------------
  // TODO: move to a Doubles class?

//  private static final int SIGN_BITS = 1;
  private static final int EXPONENT_BITS = 11;
  private static final int SIGNIFICAND_BITS = 52;

//  private static final long SIGN_MASK =
//    1L << (EXPONENT_BITS + SIGNIFICAND_BITS);

//  private static final long EXPONENT_MASK =
//    ((1L << EXPONENT_BITS) - 1L) << SIGNIFICAND_BITS;

  private static final long SIGNIFICAND_MASK =
    (1L << SIGNIFICAND_BITS) - 1L;

//  private static final int EXPONENT_BIAS =
//    (1 << (EXPONENT_BITS - 1)) - 1;

  private static final int MAXIMUM_BIASED_EXPONENT =
    (1 << EXPONENT_BITS) - 1;

//  private static final int MAXIMUM_EXPONENT =
//    EXPONENT_BIAS;

//  private static final int MINIMUM_NORMAL_EXPONENT =
//    1 - MAXIMUM_EXPONENT;

//  private static final int MINIMUM_SUBNORMAL_EXPONENT =
//    MINIMUM_NORMAL_EXPONENT - SIGNIFICAND_BITS;

  private static final double makeDouble (final int s,
                                          final int e,
                                          final long t) {
    assert ((0 == s) || (1 ==s)) : "Invalid sign bit:" + s;
    assert (0 <= e) :
      "Negative exponent:" + Integer.toHexString(e);
    assert (e <= MAXIMUM_BIASED_EXPONENT) :
      "Exponent too large:" + Integer.toHexString(e) +
      ">" + Integer.toHexString(MAXIMUM_BIASED_EXPONENT);
    assert (0 <= t) :
      "Negative significand:" + Long.toHexString(t);
    assert (t <= SIGNIFICAND_MASK) :
      "Significand too large:" + Long.toHexString(t) +
      ">" + Long.toHexString(SIGNIFICAND_MASK);

    final long ss = ((long) s) << (EXPONENT_BITS + SIGNIFICAND_BITS);
    final long se = ((long) e) << SIGNIFICAND_BITS;

    assert (0L == (ss & se & t));
    return Double.longBitsToDouble(ss | se | t); }

  //--------------------------------------------------------------
  // TODO: Does this sample distinct double *values* with equal
  // probability, or distinct bit patterns, where multiple bit 
  // patterns may have equal values?
  // TODO: What about NaN? corresponds to multiple bit patterns
  @Override
  public double sample () {
    return
      makeDouble(
        _urp.nextInt(2),
        _urp.nextInt(_delta),
        _urp.nextLong() & SIGNIFICAND_MASK);  }

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
    return DoubleSampler.make(urp,1+MAXIMUM_BIASED_EXPONENT); }
  
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

