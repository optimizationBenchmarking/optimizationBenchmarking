package org.optimizationBenchmarking.experimentation.data;

import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/** The tool job builder for experiment sets */
public final class ExperimentDataJobBuilder extends
    ToolJobBuilder<ExperimentSetContext, ExperimentDataJobBuilder> {

  /** create */
  ExperimentDataJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentSetContext create() throws Exception {
    return new ExperimentSetContext(this.getLogger());
  }

}
