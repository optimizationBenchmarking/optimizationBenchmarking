package org.optimizationBenchmarking.experimentation.data;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * the hierarchical experiment set element
 * 
 * @param <CET>
 *          the collection element type
 * @param <CCT>
 *          the compiled collection element type
 * @param <RT>
 *          the result of processing the collection
 */
abstract class _HierarchicalCollection<CCT, CET extends _Context<CCT>, RT>
    extends _Context<RT> {

  /** the collection */
  private volatile ArrayList<Object> m_collection;

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  _HierarchicalCollection(final _FSM owner) {
    super(owner);
    this.m_collection = new ArrayList<>();
  }

  /**
   * get the allowed child type
   * 
   * @return the allowed child type
   */
  abstract Class<CET> _getChildType();

  /**
   * process the collection
   * 
   * @param data
   *          the data
   * @return the result of processing the collection
   */
  RT _doCompile(final ArrayList<CCT> data) {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  final RT _doCompile() {
    ArrayList<Object> list;
    Class<CET> clazz;
    CCT r;
    Object o;
    int i;

    list = this.m_collection;
    this.m_collection = null;

    clazz = this._getChildType();
    i = list.size();

    if (i <= 0) {
      throw new IllegalStateException("Each " + //$NON-NLS-1$
          this.getClass().getSimpleName()
          + " must have at least one child of class " + //$NON-NLS-1$
          clazz.getSimpleName() + ", but " + this + //$NON-NLS-1$
          " has none."); //$NON-NLS-1$
    }

    for (; (--i) >= 0;) {
      o = list.get(i);
      if (o == null) {
        throw new IllegalStateException("No child of " + this//$NON-NLS-1$
            + " can be null, but the one at index " + i//$NON-NLS-1$
            + " is."); //$NON-NLS-1$
      }
      r = clazz.cast(o)._compile();
      if (r == null) {
        throw new IllegalStateException("No compiled child of " + this//$NON-NLS-1$
            + " can be null, but the one at index " + i//$NON-NLS-1$
            + " (" + o + //$NON-NLS-1$
            ") compiles to null."); //$NON-NLS-1$
      }
      list.set(i, r);
    }

    return this._doCompile((ArrayList<CCT>) list);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    this.fsmStateAssert(_FSM.STATE_OPEN);
    if (!(this._getChildType().isInstance(child))) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.afterChildOpened(child, hasOtherChildren);
    this.fsmStateAssert(_FSM.STATE_OPEN);
    if (!(this._getChildType().isInstance(child))) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    super.afterChildClosed(child);
    this.fsmStateAssert(_FSM.STATE_OPEN);
    if (this._getChildType().isInstance(child)) {
      this.m_collection.add(child);
    } else {
      this.throwChildNotAllowed(child);
    }
  }
}
