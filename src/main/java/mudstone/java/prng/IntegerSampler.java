package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** Like {@link 
 * org.apache.commons.rng.sampling.distribution.DiscreteSampler}
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface IntegerSampler extends NumberSampler {

  int sample();  
  
  @Override
  public default Number next () {
    return Integer.valueOf(sample()); }
  
  public static IntegerSampler 
  make (final UniformRandomProvider urp) {
    return new IntegerSampler () {
      @Override
      public int sample () { return urp.nextInt(); } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

