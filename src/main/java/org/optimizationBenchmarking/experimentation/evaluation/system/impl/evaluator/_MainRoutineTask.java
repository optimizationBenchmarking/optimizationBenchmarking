package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.concurrent.RecursiveAction;

import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** the job task */
final class _MainRoutineTask extends RecursiveAction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the job */
  private final IEvaluationJob m_job;

  /** the section container */
  private final ISectionContainer m_container;

  /**
   * create the task
   *
   * @param job
   *          the job
   * @param container
   *          the container
   */
  _MainRoutineTask(final IEvaluationJob job,
      final ISectionContainer container) {
    super();
    this.m_job = job;
    this.m_container = container;
  }

  /** {@inheritDoc} */
  @Override
  protected final void compute() {
    this.m_job.main(this.m_container);
  }

  /**
   * Get the string representation of the task.
   *
   * @return the string representation of the task
   */
  @Override
  public final String toString() {
    return String.valueOf(this.m_job);
  }
}
