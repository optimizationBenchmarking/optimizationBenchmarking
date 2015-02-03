package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.io.spec.IExperimentOutput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.FileOutputTool;

/**
 * A file-based experiment output tool.
 * 
 * @param <T>
 *          the experiment data type supported for output
 */
public class ExperimentFileOutput<T> extends FileOutputTool<T> implements
    IExperimentOutput<T> {

  /** create */
  protected ExperimentFileOutput() {
    super();
  }

}
