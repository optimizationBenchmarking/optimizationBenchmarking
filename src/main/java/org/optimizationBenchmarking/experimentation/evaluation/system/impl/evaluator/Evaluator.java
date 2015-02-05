package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluator;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfigurationBuilder;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The evaluator main component.
 */
public final class Evaluator extends Tool implements IEvaluator {

  /** the parameter for parallel execution */
  public static final String PARAM_PARALLEL = "parallel"; //$NON-NLS-1$

  /** the driver to be used for loading the input data */
  public static final String PARAM_INPUT_DRIVER = (IOTool.INPUT_PARAM_PREFIX + "Driver"); //$NON-NLS-1$

  /** the driver to be used for storing the output data */
  public static final String PARAM_OUTPUT_DRIVER = DocumentConfigurationBuilder.PARAM_DOCUMENT_DRIVER;

  /** the authors */
  public static final String PARAM_AUTHORS = "authors"; //$NON-NLS-1$

  /** create */
  Evaluator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Evaluator"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final IEvaluationBuilder use() {
    return new _EvaluationBuilder();
  }

  /**
   * Get the shared instance of the evaluator tool.
   * 
   * @return the shared instance
   */
  public static final Evaluator getInstance() {
    return __EvaluatorLoader.INSTANCE;
  }

  /** the loader */
  private static final class __EvaluatorLoader {
    /** the shared instance */
    static final Evaluator INSTANCE = new Evaluator();
  }
}
