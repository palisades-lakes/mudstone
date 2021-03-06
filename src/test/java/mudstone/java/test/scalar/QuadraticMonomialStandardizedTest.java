package mudstone.java.test.scalar;


import static mudstone.java.test.scalar.Common.affines;

import static mudstone.java.test.scalar.Common.constants;
import static mudstone.java.test.scalar.Common.cubics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.general;

import static mudstone.java.test.scalar.Common.quadraticKnots;
import static mudstone.java.test.scalar.Common.quadratics;
import static mudstone.java.test.scalar.Common.quadraticTestPts;
import static mudstone.java.test.scalar.Common.otherFns;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Domain;
import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.QuadraticMonomialStandardized;

//----------------------------------------------------------------
/** Test monomial form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticMonomialStandardizedTest test > QuadraticMonomialStandardizedTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-10-09
 */

public final class QuadraticMonomialStandardizedTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void exactTests () {
    final Domain support = expand(quadraticTestPts);
    final List<BiFunction> factories = 
      List.of(QuadraticMonomialStandardized::interpolate);
    final Iterable<Function> functions = Iterables.concat(
        
      quadratics, affines, constants);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        //System.out.println();
        //System.out.println(f);
        for (final double[][] kn : quadraticKnots) {
          if (QuadraticMonomialStandardized.validKnots(kn)) {
//            System.out.println(
//              Arrays.toString(kn[0]) + 
//              ", " + 
//              Arrays.toString(kn[1]));
             exact(f,factory,kn,quadraticTestPts,support,
              6.0e5,3.0e7,4.0e7); } } } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void generalTests () {
    final Domain support = expand(quadraticTestPts);
    final List<BiFunction> factories = 
      List.of(QuadraticMonomialStandardized::interpolate);
    final Iterable<Function> functions = Iterables.concat(
      cubics, otherFns);
    for (final BiFunction factory : factories) {
      for (final Function f : functions) {
        for (final double[][] kn : quadraticKnots) {
          if (QuadraticMonomialStandardized.validKnots(kn)) {
            general(f,factory,kn,support,
              1.0e0,4.0e5,8.0e3); } } } } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------


