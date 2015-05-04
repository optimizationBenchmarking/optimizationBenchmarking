package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/** A base class to derive tool jobs. */
public abstract class ToolJob implements IToolJob {

  /** the logger to use */
  private final Logger m_logger;

  /**
   * create
   *
   * @param logger
   *          the logger to use, or {@code null} if no logging information
   *          should be created
   */
  protected ToolJob(final Logger logger) {
    super();
    this.m_logger = logger;
  }

  /**
   * Get the logger of this job
   *
   * @return the logger of this job
   */
  protected Logger getLogger() {
    return this.m_logger;
  }

  /**
   * create
   *
   * @param builder
   *          the builder
   */
  protected ToolJob(final ToolJobBuilder<?, ?> builder) {
    this(builder.m_logger);
  }
}
