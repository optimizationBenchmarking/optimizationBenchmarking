package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentInput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileInputTool;

/**
 * A file-based experiment input tool.
 */
public class ExperimentFileInput extends
    FileInputTool<ExperimentSetContext> implements IExperimentInput {

  /** create */
  protected ExperimentFileInput() {
    super();
  }

}
