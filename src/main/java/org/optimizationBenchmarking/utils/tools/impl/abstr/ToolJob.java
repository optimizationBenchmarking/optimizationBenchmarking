package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/** A base class to derive tool jobs. */
public abstract class ToolJob implements IToolJob {

  /** the logger to use */
  protected final Logger m_logger;

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
}
