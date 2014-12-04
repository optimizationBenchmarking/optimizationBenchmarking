package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngineBuilder;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * The base class for math engine builders
 * 
 * @param <J>
 *          the math engine type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class MathEngineBuilder<J extends MathEngine, R extends MathEngineBuilder<J, R>>
    extends ToolJobBuilder<J, R> implements IMathEngineBuilder {

  /** create */
  protected MathEngineBuilder() {
    super();
  }

}
