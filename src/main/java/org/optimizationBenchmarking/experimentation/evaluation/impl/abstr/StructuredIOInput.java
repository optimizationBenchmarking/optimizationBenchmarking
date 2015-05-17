package org.optimizationBenchmarking.experimentation.evaluation.impl.abstr;

import java.io.IOException;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.spec.IEvaluationInput;
import org.optimizationBenchmarking.utils.io.structured.spec.IInputJobBuilder;

/**
 * A wrapper for input from a structured I/O job
 */
public class StructuredIOInput implements IEvaluationInput {

  /** the input job builder to use */
  private IInputJobBuilder<ExperimentSetContext> m_builder;

  /** the logger */
  private Logger m_logger;

  /**
   * create
   *
   * @param builder
   *          the job builder
   * @param logger
   *          the logger
   */
  public StructuredIOInput(
      final IInputJobBuilder<ExperimentSetContext> builder,
      final Logger logger) {
    super();

    if (builder == null) {
      throw new IllegalArgumentException(
          "IInputJobBuilder cannot be null."); //$NON-NLS-1$
    }
    this.m_builder = builder;
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet getExperimentSet() throws IOException {
    final IInputJobBuilder<ExperimentSetContext> builder;
    final Logger logger;

    synchronized (this) {
      builder = this.m_builder;
      this.m_builder = null;
      logger = this.m_logger;
      this.m_logger = null;
    }

    if (builder == null) {
      throw new IllegalStateException("Structured IO job already used."); //$NON-NLS-1$
    }

    try (final ExperimentSetContext context = new ExperimentSetContext(
        logger)) {
      builder.setDestination(context);
      builder.create().call();
      return context.create();
    }
  }
}
