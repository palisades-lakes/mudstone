package mudstone.java.prng;

import org.apache.commons.rng.UniformRandomProvider;

/** Sample finite (not NaN or inf) doubles
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-15
 */
public interface FiniteDoubleSampler extends NumberSampler {

  double sample();  
  
  @Override
  public default Number next () {
    return Double.valueOf(sample()); }
  
  public static FiniteDoubleSampler 
  make (final UniformRandomProvider urp) {
    final DoubleSampler fs = DoubleSampler.make(urp);
    return new FiniteDoubleSampler () {
      @Override
      public double sample () { 
        // TODO: fix infinite loop
        for (;;) {
          final double f = fs.sample();
          if (Double.isFinite(f)) { return f; } } } };
  }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------

