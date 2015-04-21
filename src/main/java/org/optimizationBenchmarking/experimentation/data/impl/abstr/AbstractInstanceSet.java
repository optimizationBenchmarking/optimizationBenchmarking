package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet}
 * interface.
 */
public class AbstractInstanceSet extends AbstractNamedElementSet implements
    IInstanceSet {

  /** the owning experiment set */
  IExperimentSet m_owner;

  /**
   * create the abstract instance set
   * 
   * @param owner
   *          the owner
   */
  protected AbstractInstanceSet(final IExperimentSet owner) {
    super();
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Owning IExperimentSet of AbstractInstanceSet must not be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;
  }

  /**
   * Create an abstract instance set without an owner. You must later set
   * the owner via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractInstanceSet)}
   * .
   */
  protected AbstractInstanceSet() {
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
  public ArrayListView<? extends IInstance> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IInstance find(final String name) {
    return ((IInstance) (super.find(name)));
  }

}
