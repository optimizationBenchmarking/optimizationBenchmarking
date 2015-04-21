package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * interface.
 */
public abstract class AbstractInstanceRuns extends AbstractElementSet
    implements IInstanceRuns {

  /** the owner */
  IExperiment m_owner;

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  protected AbstractInstanceRuns(final IExperiment owner) {
    super();
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Owning IExperiment of AbstractInstanceRuns must not be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;
  }

  /**
   * Create an abstract instance runs without an owner. You must later set
   * the owner via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperiment#own(AbstractInstanceRuns)}
   * .
   */
  protected AbstractInstanceRuns() {
    super();
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractRun}
   * . The run must have been created with the parameter-less constructor
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractRun#AbstractRun()}
   * .
   * 
   * @param run
   *          the run to own
   */
  protected final void own(final AbstractRun run) {
    if (run == null) {
      throw new IllegalArgumentException(//
          "AbstractRun to be owned by AbstractInstanceRuns cannot be null."); //$NON-NLS-1$
    }
    synchronized (run) {
      if (run.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractRun to be owned by AbstractInstanceRuns already owned.");//$NON-NLS-1$
      }
      run.m_owner = this;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IExperiment getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArraySetView<? extends IRun> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }
}
