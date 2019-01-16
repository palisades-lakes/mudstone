package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface ByteSampler extends NumberSampler {

  byte sample ();  
  
  @Override
  public default Number next () {
    return Byte.valueOf(sample());  } 
  
  public static ByteSampler 
  make (final UniformRandomProvider urp) {
    return new ByteSampler () {
      @Override
      public byte sample () { return (byte) urp.nextInt(); } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

