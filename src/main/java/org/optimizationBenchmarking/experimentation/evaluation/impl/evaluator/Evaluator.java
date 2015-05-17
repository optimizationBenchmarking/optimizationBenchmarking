package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationBuilder;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluator;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentConfigurationBuilder;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolSuite;

/**
 * The evaluator main component.
 */
public final class Evaluator extends ToolSuite implements IEvaluator {

  /** the parameter for parallel execution */
  public static final String PARAM_PARALLEL = "parallel"; //$NON-NLS-1$

  /** the driver to be used for loading the input data */
  public static final String PARAM_INPUT_DRIVER = (IOTool.INPUT_PARAM_PREFIX + "Driver"); //$NON-NLS-1$

  /** the driver to be used for storing the output data */
  public static final String PARAM_OUTPUT_DRIVER = DocumentConfigurationBuilder.PARAM_DOCUMENT_DRIVER;

  /** the authors */
  public static final String PARAM_AUTHORS = "authors"; //$NON-NLS-1$

  /** the evaluation source suffix */
  public static final String PARAM_EVALUATION_SETUP = (EvaluationXML.PARAM_EVALUATION_PREFIX + EvaluationXML.PARAM_EVALUATION_SUFFIX);

  /** create */
  Evaluator() {
    super();
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
