package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** Like {@link 
 * org.apache.commons.rng.sampling.distribution.DiscreteSampler}
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface LongSampler extends NumberSampler {

  long sample ();  
 
  @Override
  public default Number next () {
    return Long.valueOf(sample()); }
  
  public static LongSampler 
  make (final UniformRandomProvider urp) {
    return new LongSampler () {
      @Override
      public long sample () { return urp.nextLong(); } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

