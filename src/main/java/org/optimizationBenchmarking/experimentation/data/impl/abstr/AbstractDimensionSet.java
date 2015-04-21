package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet}
 * interface.
 */
public class AbstractDimensionSet extends AbstractNamedElementSet
    implements IDimensionSet {

  /** the owning experiment set */
  IExperimentSet m_owner;

  /**
   * create the abstract dimension set
   * 
   * @param owner
   *          the owner
   */
  protected AbstractDimensionSet(final IExperimentSet owner) {
    super();
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Owning IExperimentSet of AbstractDimensionSet must not be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;
  }

  /**
   * Create an abstract dimension set without an owner. You must later set
   * the owner via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractDimensionSet)}
   * .
   */
  protected AbstractDimensionSet() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArraySetView<? extends IDimension> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IDimension find(final String name) {
    return ((IDimension) (super.find(name)));
  }

}
