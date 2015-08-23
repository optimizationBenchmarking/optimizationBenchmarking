package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet}
 * interface.
 */
public abstract class AbstractDimensionSet extends AbstractNamedElementSet
    implements IDimensionSet {

  /** the owning experiment set */
  IExperimentSet m_owner;

  /**
   * Create the abstract dimension set. If {@code owner==null}, you must
   * later set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractDimensionSet)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractDimensionSet(final IExperimentSet owner) {
    super();
    this.m_owner = owner;
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimension}
   * .
   *
   * @param dimension
   *          the dimension to own
   */
  protected final void own(final AbstractDimension dimension) {
    if (dimension == null) {
      throw new IllegalArgumentException(//
          "AbstractDimension to be owned by AbstractDimensionSet cannot be null."); //$NON-NLS-1$
    }
    synchronized (dimension) {
      if (dimension.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractDimension to be owned by AbstractDimensionSet already owned.");//$NON-NLS-1$
      }
      dimension.m_owner = this;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends IDimension> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IDimension find(final String name) {
    return ((IDimension) (super.find(name)));
  }

}
