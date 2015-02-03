package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.concurrent.RecursiveAction;

import org.optimizationBenchmarking.experimentation.data.DataSet;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** the job task */
final class _DoJobsTask extends RecursiveAction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the module */
  private final _PseudoModule m_module;

  /** the data */
  private final DataSet<?> m_data;

  /** the section container */
  private final ISectionContainer m_container;

  /**
   * create the task
   * 
   * @param module
   *          the module
   * @param data
   *          the data
   * @param container
   *          the container
   */
  _DoJobsTask(final _PseudoModule module, final DataSet<?> data,
      final ISectionContainer container) {
    super();
    this.m_module = module;
    this.m_data = data;
    this.m_container = container;
  }

  /** {@inheritDoc} */
  @Override
  protected final void compute() {
    this.m_module._doJobs(this.m_data, this.m_container);
  }

  /**
   * Get the string representation of the task.
   * 
   * @return the string representation of the task
   */
  @Override
  public final String toString() {
    return String.valueOf(this.m_module);
  }
}
