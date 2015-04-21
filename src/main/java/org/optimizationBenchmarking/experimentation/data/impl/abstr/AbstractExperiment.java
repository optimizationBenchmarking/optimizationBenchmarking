package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IParameterSetting;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment}
 * interface.
 */
public class AbstractExperiment extends AbstractElementSet implements
    IExperiment {

  /** the owner */
  IExperimentSet m_owner;

  /**
   * create
   * 
   * @param owner
   *          the owner
   */
  protected AbstractExperiment(final IExperimentSet owner) {
    super();
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Owning IExperimentSet of AbstractExperiment must not be null."); //$NON-NLS-1$
    }
    this.m_owner = owner;
  }

  /**
   * Create an abstract experiment without an owner. You must later set the
   * owner via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractExperimentSet#own(AbstractExperiment)}
   * .
   */
  protected AbstractExperiment() {
    super();
  }

  /**
   * Own an
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceRuns}
   * . The run must have been created with the parameter-less constructor
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceRuns#AbstractInstanceRuns()}
   * .
   * 
   * @param runs
   *          the runs to own
   */
  protected final void own(final AbstractInstanceRuns runs) {
    if (runs == null) {
      throw new IllegalArgumentException(//
          "AbstractInstanceRuns to be owned by AbstractExperiment cannot be null."); //$NON-NLS-1$
    }
    synchronized (runs) {
      if (runs.m_owner != null) {
        throw new IllegalArgumentException(//
            "AbstractInstanceRuns to be owned by AbstractExperiment already owned.");//$NON-NLS-1$
      }
      runs.m_owner = this;
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
  public ArraySetView<? extends IInstanceRuns> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IParameterSetting getParameterSetting() {
    return new AbstractParameterSetting();
  }
}
