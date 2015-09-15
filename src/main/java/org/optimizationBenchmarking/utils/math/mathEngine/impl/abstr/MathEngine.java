package org.optimizationBenchmarking.utils.math.mathEngine.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.math.mathEngine.spec.IMathEngine;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The base class for math engines */
public abstract class MathEngine extends ToolJob implements IMathEngine {

  /**
   * create
   *
   * @param logger
   *          the logger to write log information to, or {@code null} if no
   *          log output should be created
   */
  protected MathEngine(final Logger logger) {
    super(logger);
  }
}
