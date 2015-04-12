package org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IAppendixJobBuilder;

/** The appendix job builder implementation */
final class _AppendixJobBuilder
    extends
    _EvaluationJobBuilder<ExperimentSet, AppendixModule, AppendixJob, _AppendixJobBuilder>
    implements IAppendixJobBuilder {

  /**
   * create the job builder
   * 
   * @param module
   *          the module
   */
  _AppendixJobBuilder(final AppendixModule module) {
    super(module);
  }
}
