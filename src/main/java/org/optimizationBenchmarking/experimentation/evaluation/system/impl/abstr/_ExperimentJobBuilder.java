package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.impl.ref.Experiment;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IExperimentJobBuilder;

/** The experiment job builder implementation */
final class _ExperimentJobBuilder
    extends
    _EvaluationJobBuilder<Experiment, ExperimentModule, ExperimentJob, _ExperimentJobBuilder>
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
