package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetOutput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileOutputTool;

/**
 * A file-based experiment output tool.
 *
 * @param <T>
 *          the experiment data type supported for output
 */
public class ExperimentSetFileOutput<T> extends FileOutputTool<T>
implements IExperimentSetOutput<T> {

  /** create */
  protected ExperimentSetFileOutput() {
    super();
  }

}
