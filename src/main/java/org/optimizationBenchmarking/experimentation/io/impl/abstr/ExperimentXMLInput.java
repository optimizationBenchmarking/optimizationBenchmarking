package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentInput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/**
 * An XML-based experiment input tool.
 */
public class ExperimentXMLInput extends XMLInputTool<ExperimentSetContext>
    implements IExperimentInput {

  /** create */
  protected ExperimentXMLInput() {
    super();
  }

}
