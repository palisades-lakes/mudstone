package mudstone.java.prng;

import java.util.List;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CollectionSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousUniformSampler;

/** 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-29
 */
@SuppressWarnings("unchecked")
public interface ArraySampler {

  Object next ();  

  //--------------------------------------------------------------

  public static final ArraySampler 
  make (final UniformRandomProvider urp,
        final Class elementType) {
    return new ArraySampler () {
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

