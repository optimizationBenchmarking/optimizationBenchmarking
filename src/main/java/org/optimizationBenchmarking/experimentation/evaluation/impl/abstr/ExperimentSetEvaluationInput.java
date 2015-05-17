package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationInput;

/**
 * An implementation of the
 * {@link org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationInput}
 * interface which directly wraps around an instance of
 * {@link org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSet}
 * .
 */
public final class ExperimentSetEvaluationInput implements
    IEvaluationInput {

  /** the wrapped data set */
  private volatile IExperimentSet m_data;

  /**
   * create
   *
   * @param data
   *          the data
   */
  public ExperimentSetEvaluationInput(final IExperimentSet data) {
    super();

    if (data == null) {
      throw new IllegalArgumentException("ExperimentSet cannot be null."); //$NON-NLS-1$
    }
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getExperimentSet() {
    final IExperimentSet data;

    synchronized (this) {
      data = this.m_data;
      this.m_data = null;
    }

    if (data == null) {
      throw new IllegalStateException("IExperimentSet already taken."); //$NON-NLS-1$
    }

    return data;
  }

}
