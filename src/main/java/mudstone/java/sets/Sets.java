package mudstone.java.sets;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.CollectionSampler;

import mudstone.java.exceptions.Exceptions;

/** Utilities merging <code>java.util.Set</code> and
 * <code>mudstone.java.sets.Set</code>.
 *
 * Static methods only; no state.
 *
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-11
 */

@SuppressWarnings("unchecked")
public final class Sets {

  /** Default notion of equivalence for most sets.
   */
  public static final BiPredicate OBJECT_EQUALS =
    new BiPredicate() {
    @Override
    public final boolean test (final Object t, 
                               final Object u) {
      return Objects.equals(t,u); }
  };

  //--------------------------------------------------------------
  /** Does the set contain the element?
   */

  public static final boolean contains (final Object set,
                                        final Object element) {
    if (set instanceof Set) {
      return ((Set) set).contains(element); }
    if (set instanceof java.util.Set) {
      return ((java.util.Set) set).contains(element); }
    throw Exceptions.unsupportedOperation(
      null,"contains",set,element); }

  //--------------------------------------------------------------

  public static final Iterator sampler (final Object set,
                                        final UniformRandomProvider prng,
                                        final Map options) {
    if (set instanceof Set) {
      return ((Set) set).sampler(prng,options); }

    if (set instanceof java.util.Set) {
      assert null == options;
      final CollectionSampler cs =
        new CollectionSampler(prng,((java.util.Set) set)); 
      return
        new Iterator () {
        @Override
        public final boolean hasNext () { return true; }
        @Override
        public final Object next () { return cs.sample(); } }; }

    throw Exceptions.unsupportedOperation(
      null,"contains",set,prng,options); }

  //--------------------------------------------------------------
  // predicates on equivalence relation
  //--------------------------------------------------------------
  /** Is a = a?
   */
  public final static boolean isReflexive (final Set elements,
                                           final BiPredicate equivalent,
                                           final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    return equivalent.test(a,a); }

  /** Is a = a?
   */
  public final static boolean isReflexive (final Set elements,
                                           final Iterator samples) {
    return isReflexive(elements,elements.equivalence(),samples); }

  //--------------------------------------------------------------
  /** Is a = a?
   */
  public final static boolean isSymmetric (final Set elements,
                                           final BiPredicate equivalent,
                                           final Iterator samples) {
    final Object a = samples.next();
    assert elements.contains(a);
    final Object b = samples.next();
    assert elements.contains(b);
    return equivalent.test(a,b) == equivalent.test(b,a); }

  /** Is a = a?
   */
  public final static boolean isSymmetric (final Set elements,
                                           final Iterator samples) {
    return isSymmetric(elements,elements.equivalence(),samples); }

  //--------------------------------------------------------------
  // disable constructor
  //--------------------------------------------------------------

  private Sets () {
    throw new UnsupportedOperationException(
      "can't instantiate " + getClass()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------
