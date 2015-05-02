package org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.function;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * A container for an experiment function
 */
public final class ExperimentFunction {
  /** the experiment */
  private final IExperiment m_experiment;
  /** the function */
  private final IMatrix m_function;

  /**
   * Create the experiment function
   * 
   * @param experiment
   *          the experiment
   * @param function
   *          the function
   */
  ExperimentFunction(final IExperiment experiment, final IMatrix function) {
    super();
    this.m_experiment = experiment;
    this.m_function = function;
  }

  /**
   * Get the experiment.
   * 
   * @return the experiment from which the {@link #getFunction() function}
   *         was computed
   */
  public final IExperiment getExperiment() {
    return this.m_experiment;
  }

  /**
   * Get the function
   * 
   * @return the function over the {@link #getExperiment() experiment}
   */
  public final IMatrix getFunction() {
    return this.m_function;
  }
}
