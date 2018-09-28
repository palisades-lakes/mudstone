package mudstone.java.test.scalar;

import static mudstone.java.test.scalar.Common.affineQuadratics;
import static mudstone.java.test.scalar.Common.constantQuadratics;
import static mudstone.java.test.scalar.Common.exact;
import static mudstone.java.test.scalar.Common.expand;
import static mudstone.java.test.scalar.Common.knots;
import static mudstone.java.test.scalar.Common.quadraticQuadratics;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

import mudstone.java.functions.Function;
import mudstone.java.functions.scalar.ModelFactory;
import mudstone.java.functions.scalar.QuadraticMonomialStandardizedFactory;

//----------------------------------------------------------------
/** Test monomial form parabolas. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/scalar/QuadraticMonomialStandardizedTest test > QuadraticMonomialStandardizedTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2018-09-28
 */

strictfp
public final class QuadraticMonomialStandardizedTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void tests () {
    final Iterable<ModelFactory> factories = 
      List.of(QuadraticMonomialStandardizedFactory.get());
    final Iterable<Function> functions = Iterables.concat(
      quadraticQuadratics, affineQuadratics, constantQuadratics);
    for (final ModelFactory factory : factories) {
      for (final Function f : functions) {
        //System.out.println(f);
        for (final double[] kn : knots) {
          //System.out.println(Arrays.toString(kn));
          exact(f,factory,kn,expand(kn),1.0e3,5.0e3,2.0e4); } } } }
  
  //--------------------------------------------------------------
}
//--------------------------------------------------------------


