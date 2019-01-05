package mudstone.java.sets;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.rng.UniformRandomProvider;
import mudstone.java.exceptions.Exceptions;

/** General, possibly unbounded, sets of <code>Object</code>s, 
 * and primitive values, as opposed to <code>java.util.Set</code>
 * (and primitve variants) which are enumerated finite sets.
 *
 * A usable set provides 2 basic functionalities:
 * <ol>
 * <li> Given a thing, a way to tell if that thing is in the set
 * (<code>contains</code>).
 * <li> A way to get at least some elements of the (non-empty) 
 * set. 
 * <ol>
 * <li> What you have to specify to determine a particular 
 * element will depend on the details of that set,
 * so <code>getXxx</code> methods take an opaque 
 * <code>options</code> argument.
 * It's also useful, for testing, to require some simple 
 * <code>sample</code> methods, that default to something 
 * reasonable in the no-arg case, allow seeded randomization
 * in the 1-arg case, and accept a general <code>options</code>
 * when desired. 
 * </ol>
 * 
 * Default <code>contains</code> return <code>false</code>
 * for every thing (ie, default is empty set).
 * 
 * <b>TODO:</b> replace sampling iterators with 0-arg functions?
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2019-01-04
 */
public interface Set {

  @SuppressWarnings("unused")
  public default boolean contains (final Object element) {
    return false; }

  //  @SuppressWarnings("unused")
  //  public default boolean contains (final boolean element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final byte element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final short element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final int element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final long element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final float element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final double element) {
  //    return false; }
  //
  //  @SuppressWarnings("unused")
  //  public default boolean contains (final char element) {
  //    return false; }

  //--------------------------------------------------------------
  // TODO: is this just a Function that maps options to values?
  // That is, Is a set a function from all java objects to its
  // elements?

  //  public default boolean getBoolean (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getBoolean",options); }
  //
  //  public default byte getByte (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getByte",options); }
  //
  //  public default short getShort (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getShort",options); }
  //
  //  public default int getInt (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getInt",options); }
  //
  //  public default long getLong (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getLong",options); }
  //
  //  public default float getFloat (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getFloat",options); }
  //
  //  public default double getDouble (final Map options) {
  //    throw Exceptions.unsupportedOperation (
  //      this,"getDouble",options); }
  //
  //  public default char getChar (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"getChar",options); }

  //  public default Object get (final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"get",options); }

  //--------------------------------------------------------------

  //  public default boolean sampleBoolean (final UniformRandomProvider prng,
  //                                        final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleBoolean",prng,options); }
  //
  //  public default byte sampleByte (final UniformRandomProvider prng,
  //                                  final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleByte",prng,options); }
  //
  //  public default short sampleShort (final UniformRandomProvider prng,
  //                                    final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleShort",prng,options); }
  //
  //  public default int sampleInt (final UniformRandomProvider prng,
  //                                final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleInt",prng,options); }
  //
  //  public default long sampleLong (final UniformRandomProvider prng,
  //                                  final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleLong",prng,options); }
  //
  //  public default float sampleFloat (final UniformRandomProvider prng,
  //                                    final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleFloat",prng,options); }
  //
  //  public default double sampleDouble (final UniformRandomProvider prng,
  //                                      final Map options) {
  //    throw Exceptions.unsupportedOperation (
  //      this,"sampleDouble",prng,options); }
  //
  //  public default char sampleChar (final UniformRandomProvider prng,
  //                                  final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleChar",prng,options); }

  //  public default Object sample (final UniformRandomProvider prng,
  //                                final Map options) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sample",prng,options); }

  //--------------------------------------------------------------
  // Using default options

  //  public default boolean sampleBoolean (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleBoolean",prng); }
  //
  //  public default byte sampleByte (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleByte",prng); }
  //
  //  public default short sampleShort (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleShort",prng); }
  //
  //  public default int sampleInt (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleInt",prng); }
  //
  //  public default long sampleLong (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleLong",prng); }
  //
  //  public default float sampleFloat (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleFloat",prng); }
  //
  //  public default double sampleDouble (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation (
  //      this,"sampleDouble",prng); }
  //
  //  public default char sampleChar (final UniformRandomProvider prng) {
  //    throw Exceptions.unsupportedOperation(
  //      this,"sampleChar",prng); }

  //  public default Object sample (final UniformRandomProvider prng) {
  //    return sample(prng,null); }

  //--------------------------------------------------------------
  // TODO: return a Supplier instead of an iterator?
  // TODO: 'generator' instead of 'sampler', since it might not be
  // 'random'?

  //  public default Iterator sampler (final UniformRandomProvider prng,
  //                                   final Map options) {
  //    return 
  //      new Iterator () {
  //      @Override
  //      public final boolean hasNext () { return true; }
  //      @Override
  //      public final Object next () { return sample(prng,options); } }; }

  public default Iterator sampler (final UniformRandomProvider prng,
                                   final Map options) {
    throw Exceptions.unsupportedOperation(
      this,"sampler",prng,options); }

  public default Iterator sampler (final UniformRandomProvider prng) {
    return sampler(prng,Collections.emptyMap()); }

  //--------------------------------------------------------------
}
//--------------------------------------------------------------

