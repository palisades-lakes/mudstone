package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** Sample finite (not NaN or inf) floats.
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface FiniteFloatSampler extends NumberSampler {

  float sample();  
  
  @Override
  public default Number next () {
    return Float.valueOf(sample()); }
  
  public static FiniteFloatSampler 
  make (final UniformRandomProvider urp) {
    final FloatSampler fs = FloatSampler.make(urp);
    return new FiniteFloatSampler () {
      @Override
      public float sample () { 
        // TODO: fix infinite loop
        for (;;) {
          final float f = fs.sample();
          if (Float.isFinite(f)) { return f; } } } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

