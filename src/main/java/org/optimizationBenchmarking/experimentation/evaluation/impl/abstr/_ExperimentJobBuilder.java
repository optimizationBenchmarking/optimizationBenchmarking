package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IExperimentJobBuilder;

/** The experiment job builder implementation */
final class _ExperimentJobBuilder
    extends
    _EvaluationJobBuilder<IExperiment, ExperimentModule, _ExperimentJobBuilder>
    implements IExperimentJobBuilder {

  /**
   * create the job builder
   *
   * @param module
   *          the module
   */
  _ExperimentJobBuilder(final ExperimentModule module) {
    super(module);
  }
}
