package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileInputTool;

/**
 * A file-based experiment input tool.
 */
public class ExperimentSetFileInput extends
    FileInputTool<ExperimentSetContext> implements IExperimentSetInput {

  /** create */
  protected ExperimentSetFileInput() {
    super();
  }

}
