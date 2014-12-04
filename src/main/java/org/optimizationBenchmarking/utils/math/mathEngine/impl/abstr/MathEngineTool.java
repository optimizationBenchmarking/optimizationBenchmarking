package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The base class for math tools
 * 
 * @param <JB>
 *          the math engine builder type
 */
public abstract class MathEngineTool<JB extends IMathEngineBuilder>
    extends Tool<JB> {

  /** create */
  protected MathEngineTool() {
    super();
  }

}
