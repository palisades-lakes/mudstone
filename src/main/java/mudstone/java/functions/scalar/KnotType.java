package mudstone.java.functions.scalar;

import java.util.HashSet;
import java.util.Set;

/** Used in approximating a function by interpolating it at domain
 * points. An interpolating function is expected to take a
 * <code>ScalarFunctional</code> to approximate and a 
 * <code>double[]</code> (or perhaps a list) containing sample
 * locations (knots) in the domain. A parallel 
 * <code>KnotType</code> is used to determine how each knot 
 * constrains the interpolant: whether the values or slopes
 * or both match.
 *  
 * @author mcdonald dot john dot alan at gmail dot com
 * @version 2018-10-05
 */
@SuppressWarnings("unchecked")
public enum KnotType {
  VALUE,
  SLOPE,
  BOTH,
  NEITHER;

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------
  
  public static final Set distinctValueKnots (final double[] x,
                                              final KnotType[] flags) {
    final Set unique = new HashSet();
    int i=0;
    for (final KnotType flag : flags) {
      if (VALUE.equals(flag) || BOTH.equals(flag)) {
        unique.add(Double.valueOf(x[i])); } 
      i++; }
    return unique; }

  public static final Set distinctSlopeKnots (final double[] x,
                                              final KnotType[] flags) {
    final Set unique = new HashSet();
    int i=0;
    for (final KnotType flag : flags) {
      if (SLOPE.equals(flag) || BOTH.equals(flag)) {
        unique.add(Double.valueOf(x[i])); } 
      i++; }
    return unique; }

//--------------------------------------------------------------
}

