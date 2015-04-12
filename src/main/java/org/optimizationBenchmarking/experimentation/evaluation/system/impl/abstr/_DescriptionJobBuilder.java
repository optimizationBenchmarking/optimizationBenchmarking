package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IDescriptionJobBuilder;

/** The description job builder implementation */
final class _DescriptionJobBuilder
    extends
    _EvaluationJobBuilder<ExperimentSet, DescriptionModule, DescriptionJob, _DescriptionJobBuilder>
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
