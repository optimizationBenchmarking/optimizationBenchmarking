package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineTool;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The base class for math tools
 */
public abstract class MathEngineTool extends Tool implements
    IMathEngineTool {

  /** create */
  protected MathEngineTool() {
    super();
  }

}
