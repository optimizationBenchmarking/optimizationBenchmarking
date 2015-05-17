package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IDescriptionJobBuilder;

/** The description job builder implementation */
final class _DescriptionJobBuilder
    extends
    _EvaluationJobBuilder<IExperimentSet, DescriptionModule, DescriptionJob, _DescriptionJobBuilder>
    implements IDescriptionJobBuilder {

  /**
   * create the job builder
   *
   * @param module
   *          the module
   */
  _DescriptionJobBuilder(final DescriptionModule module) {
    super(module);
  }
}
