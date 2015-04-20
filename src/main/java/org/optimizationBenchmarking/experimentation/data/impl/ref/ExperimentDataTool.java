package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.compiler.JavaCompilerTool;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** The entry point for the tooling API to experiment data generation */
public final class ExperimentDataTool extends Tool {

  /** create */
  ExperimentDataTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentDataJobBuilder use() {
    this.checkCanUse();
    return new ExperimentDataJobBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public void checkCanUse() {
    JavaCompilerTool.getInstance().checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return JavaCompilerTool.getInstance().canUse();
  }

  /**
   * Get the globally shared experiment data tool instance
   * 
   * @return the globally shared experiment data tool instance
   */
  public static final ExperimentDataTool getInstance() {
    return __ExperimentDataToolLoader.INSTANCE;
  }

  /** the loader class */
  private static final class __ExperimentDataToolLoader {
    /** the globally shared experiment data tool instance */
    static final ExperimentDataTool INSTANCE = new ExperimentDataTool();
  }
}
