package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetOutput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLOutputTool;

/**
 * An XMLFileType-based experiment output tool.
 * 
 * @param <T>
 *          the experiment data type supported for output
 */
public class ExperimentSetXMLOutput<T> extends XMLOutputTool<T> implements
    IExperimentSetOutput<T> {

  /** create */
  protected ExperimentSetXMLOutput() {
    super();
  }

}
