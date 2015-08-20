package org.optimizationBenchmarking.experimentation.io.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.XMLInputTool;

/**
 * An XML-based experiment input tool.
 */
public class ExperimentSetXMLInput extends
    XMLInputTool<ExperimentSetContext> implements IExperimentSetInput {

  /** create */
  protected ExperimentSetXMLInput() {
    super();
  }

}
