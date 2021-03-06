package mudstone.java.test.algebra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import mudstone.java.algebra.OneSetOneOperation;
import mudstone.java.algebra.OneSetTwoOperations;
import mudstone.java.algebra.TwoSetsTwoOperations;
import mudstone.java.prng.PRNG;
import mudstone.java.prng.Seeds;
import mudstone.java.test.sets.SetTests;

//----------------------------------------------------------------
/** Common code for testing sets. 
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2021-01-27
 * 
 * mvn test -Dtest=mudstone.java.test.algebra.AlgebraicStructureTests
 */

@SuppressWarnings("unchecked")
public final class AlgebraicStructureTests {

  private static final int TRYS = 1000;
  static final int LINEARSPACE_TRYS = 127;

  // TODO: should each structure know what laws it obeys?
  // almost surely.
  // then only need one test method...
  
  public static final void 
  magmaTests (final OneSetOneOperation magma) {
    SetTests.tests(magma);
    final Supplier g = 
      magma.generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-09.txt")));
    for(final Predicate law : magma.magmaLaws()) {
      for (int i=0; i<TRYS; i++) {
        assertTrue(law.test(g)); } } }

  public static final void 
  commutativegroupTests (final OneSetOneOperation group) {
    SetTests.tests(group);
    final Supplier g = 
      group.generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-09.txt")));
    for(final Predicate law : group.commutativegroupLaws()) {
      for (int i=0; i<TRYS; i++) {
        assertTrue(law.test(g)); } } }

  public static final void 
  fieldTests (final OneSetTwoOperations field) {
    SetTests.tests(field);
    final Supplier g = 
      field.generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-11.txt")));

    for(final Predicate law : field.fieldLaws()) {
      for (int i=0; i<TRYS; i++) {
        assertTrue(law.test(g)); } } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void bigFractions () {
    magmaTests(OneSetOneOperation.BIGFRACTIONS_ADD);
    magmaTests(OneSetOneOperation.BIGFRACTIONS_MULTIPLY); 
    fieldTests(OneSetTwoOperations.BIGFRACTIONS_FIELD); }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void qTests () {
    fieldTests(OneSetTwoOperations.Q_FIELD); }

  //--------------------------------------------------------------

  public static final void 
  linearspaceTests (final TwoSetsTwoOperations space) {
  
    SetTests.tests(space);
  
    final OneSetOneOperation elements = 
      (OneSetOneOperation) space.elements();
    commutativegroupTests(elements);
  
    final OneSetTwoOperations scalars = 
      (OneSetTwoOperations) space.scalars();
    fieldTests(scalars);
  
    final Supplier sg = 
      space.scalars().generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-11.txt")));
    final Supplier vg = 
      space.elements().generator( 
        PRNG.well44497b(
          Seeds.seed("seeds/Well44497b-2019-01-09.txt")));
  
    for(final Object law : space.linearspaceLaws()) {
      for (int i=0; i<LINEARSPACE_TRYS; i++) {
        assertTrue(((BiPredicate) law).test(vg,sg));} } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void bigFractionN () {
    for (final int n : new int[] { 1, 3, 13, 127}) {
      final TwoSetsTwoOperations bfn = 
        TwoSetsTwoOperations.getBFn(n);
      AlgebraicStructureTests.linearspaceTests(bfn); } }

  @SuppressWarnings({ "static-method" })
  @Test
  public final void qN () {
    for (final int n : new int[] { 1, 3, 13, 127}) {
      final TwoSetsTwoOperations qn = 
        TwoSetsTwoOperations.getQn(n);
      AlgebraicStructureTests.linearspaceTests(qn); } }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
