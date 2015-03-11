package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.io.spec.IExperimentOutput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;

/**
 * An XMLFileType-based experiment output tool.
 * 
 * @param <T>
 *          the experiment data type supported for output
 */
public class ExperimentXMLOutput<T> extends XMLOutputTool<T> implements
    IExperimentOutput<T> {

  /** create */
  protected ExperimentXMLOutput() {
    super();
  }

}
