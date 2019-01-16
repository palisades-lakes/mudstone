package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** Like {@link 
 * org.apache.commons.rng.sampling.distribution.DiscreteSampler}
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface ShortSampler extends NumberSampler {

  short sample ();  
 
  @Override
  public default Number next () {
    return Short.valueOf(sample()); }
  
  public static ShortSampler 
  make (final UniformRandomProvider urp) {
    return new ShortSampler () {
      @Override
      public short sample () { return (short) urp.nextInt(); } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

