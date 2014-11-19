package org.optimizationBenchmarking.utils.tools.impl.abstr;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;
import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/**
 * The base class for tool job builders
 * 
 * @param <J>
 *          the job type
 * @param <R>
 *          the return type of the setter methods
 */
public abstract class ToolJobBuilder<J extends IToolJob, R extends ToolJobBuilder<J, R>>
    implements IToolJobBuilder {

  /** the logger */
  protected Logger m_logger;

  /** create the tool job builder */
  protected ToolJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final R setLogger(final Logger logger) {
    this.m_logger = logger;
    return ((R) this);
  }

  /**
   * Do the work of creating the object
   * 
   * @return the object
   */
  protected abstract J doCreate();

  /**
   * Check whether all fields have been set.
   * 
   * @throws IllegalArgumentException
   *           otherwise
   */
  protected void validate() {
    /** */
  }

  /** {@inheritDoc} */
  @Override
  public final J create() {
    this.validate();
    return this.doCreate();
  }
}
