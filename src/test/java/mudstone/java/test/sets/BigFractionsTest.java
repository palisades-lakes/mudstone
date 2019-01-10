package mudstone.java.test.sets;

import org.junit.jupiter.api.Test;

import mudstone.java.sets.BigFractions;

//----------------------------------------------------------------
/** Test <code>BigFractions</code> set. 
 * <p>
 * <pre>
 * mvn -q -Dtest=mudstone/java/test/sets/BigFractionsTest test > BigFractionsTest.txt
 * </pre>
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-09
 */

public final class BigFractionsTest {

  @SuppressWarnings({ "static-method" })
  @Test
  public final void setTests () {
    SetTests.tests(BigFractions.get()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
