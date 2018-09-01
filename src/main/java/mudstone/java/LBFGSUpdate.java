package mudstone.java;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import com.carrotsearch.hppc.DoubleArrayDeque;
import com.carrotsearch.hppc.DoubleDeque;
import com.carrotsearch.hppc.cursors.DoubleCursor;

/** A linear function that maps a gradient to the L-BFGS search
 * direction.
 * <p>
 * This is an integrated implementation,
 * maintaining queues of past changes in position and gradient,
 * and using those in the algorithm from Nocedal-Wright 2nd ed.
 * algorithm 7.4, p 178.
 * <p>
 * <b>WARNING:</b> !!!mutable!!! !!!Not thread safe!!!
 * 
 * @author palisades dot lakes at gmail dot com
 * @version 2018-08-15
 */

public final class LBFGSUpdate implements Function {

  /** (Co)Domain dimension. */
  private final int dimension;

  /** Maximum history length. */
  private final int maxHistory;

  // Note: dx, dg and dxdg need to be updated together.
  
  /** Changes in sample position. */
  private final Deque<Vektor> dx;

  /** Changes in gradient. */
  private final Deque<Vektor> dg;

  /** cache dot products between corresponding gradient and
   * position change vectors.
   */

  private final DoubleDeque dxdg;

  private final double[] tmp; 

  public double scale;

  /** Current history length. */
  private final int history () { return dg.size(); }

  //--------------------------------------------------------------
  // methods
  //--------------------------------------------------------------

  public final boolean isFull () {
    return history() >= domainDimension(); }

  public final void clear () {
    dg.clear(); dx.clear(); dxdg.clear(); scale = 1.0; }

  public final void update (final Vektor dxi,
                            final Vektor dgi,
                            // dot product often already computed
                            final double dxdgi) {
    assert history() <= maxHistory;
    if (history() == maxHistory) { 
      dx.removeFirst(); 
      dg.removeFirst(); 
      dxdg.removeFirst(); }

    dx.addLast(dxi);
    dg.addLast(dgi);
    dxdg.addLast(dxdgi); 
    
    final double dg2last = dg.getLast().l2norm2();
    // dg2last == 0 implies no change in gradient?
    if (dg2last > 0.0) { scale = dxdg.getLast()/dg2last; } }

  //--------------------------------------------------------------
  // Function methods
  //--------------------------------------------------------------

  @Override
  public int domainDimension () { return dimension; }

  @Override
  public int codomainDimension () { return dimension; }

  // Note: should not change state of this Function
  // changes tmp array, which could be allocated here...
  @Override
  public final Vektor value (final Vektor g) {
    final int m = history();
    if (0 == m) { return g; }

    final double[] dc = g.coordinates();
    final Iterator<Vektor> skDown = dx.descendingIterator();
    final Iterator<Vektor> ykDown = dg.descendingIterator();
    final Iterator<DoubleCursor> skykDown = dxdg.descendingIterator();
    for (int j=m-1;j>=0;j--) {
      final Vektor skj = skDown.next();
      final Vektor ykj = ykDown.next();
      final double skykj = skykDown.next().value;
      final double t = skj.dot(dc) / skykj;
      tmp[j] = t;
      ykj.axpy(-t,dc); }

    Doubles.scale1(-scale,dc); 

    final Iterator<Vektor> skUp = dx.iterator();
    final Iterator<Vektor> ykUp = dg.iterator();
    final Iterator<DoubleCursor> skykUp = dxdg.iterator();
    for (int j=0;j<m;j++) {
      final Vektor skj = skUp.next();
      final Vektor ykj = ykUp.next();
      final double skykj = skykUp.next().value;
      final double tauj = tmp[j];
      final double t = ykj.dot(dc) / skykj;
      skj.axpy(-t-tauj,dc); }

    return Vektor.make(dc); }

  //--------------------------------------------------------------
  // construction
  //--------------------------------------------------------------

  private LBFGSUpdate (final int dim,
                       final int mem) {
    this.dimension = dim;
    this.maxHistory = mem;
    this.dg = new ArrayDeque<Vektor>(mem);
    this.dx = new ArrayDeque<Vektor>(mem); 
    this.dxdg = new DoubleArrayDeque(mem); 
    this.scale = 1.0;
    this.tmp = new double[mem]; }

  //--------------------------------------------------------------

  public static final LBFGSUpdate make (final int dim,
                                        final int mem) {
    assert (dim >= mem) :
      "Memory (" + mem + ") > dim (" + dim +")";
    //assert (mem >= 3) : "Memory (" + mem + ") must be >=3";
    return new LBFGSUpdate(dim,mem); }

  //--------------------------------------------------------------
} // end class
//--------------------------------------------------------------
