package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DataSet;
import org.optimizationBenchmarking.experimentation.data.Experiment;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredExperimentSetModule;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IConfiguredModule;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** a module wrapper */
final class _ModuleWrapper extends _PseudoModule {

  /** the actual module to be executed */
  final IConfiguredModule m_module;

  /**
   * create the pseudo module
   * 
   * @param logger
   *          the logger
   * @param module
   *          the module
   * @param children
   *          the children
   */
  _ModuleWrapper(final Logger logger, final _PseudoModule[] children,
      final IConfiguredModule module) {
    super(logger, children);
    if (module == null) {
      throw new IllegalArgumentException("Module must not be null."); //$NON-NLS-1$
    }
    this.m_module = module;
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return ("Module " + //$NON-NLS-1$
    TextUtils.className(this.m_module.getClass()));
  }

  /** {@inheritDoc} */
  @Override
  final void _doInitJobs(final ExperimentSet data, final IDocument document) {
    final Runnable job;
    job = this.m_module.createInitJob(data, document);
    if (job != null) {
      job.run();
    }
    super._doInitJobs(data, document);
  }

  /** {@inheritDoc} */
  @Override
  final void _doSummaryJobs(final ExperimentSet data,
      final IPlainText summary) {
    final Runnable job;
    job = this.m_module.createSummaryJob(data, summary);
    if (job != null) {
      job.run();
    }
    super._doSummaryJobs(data, summary);
  }

  /**
   * do the job
   * 
   * @param data
   *          the data
   * @param dest
   *          the destination
   */
  private final void __do(final DataSet<?> data,
      final ISectionContainer dest) {
    final Runnable job;

    if (data instanceof ExperimentSet) {

      if (this.m_module instanceof IConfiguredExperimentSetModule) {
        job = ((IConfiguredExperimentSetModule) (this.m_module))
            .createMainJob(((ExperimentSet) data), dest);
        if (job != null) {
          job.run();
        }
        return;
      }

      throw new IllegalStateException(//
          "An ExperimentSet can only be processed by an IConfiguredExperimentSetModule, but you provided a " //$NON-NLS-1$
              + TextUtils.className(this.m_module.getClass()));

    }

    if (data instanceof Experiment) {

      if (this.m_module instanceof IConfiguredExperimentModule) {
        job = ((IConfiguredExperimentModule) (this.m_module))
            .createMainJob(((Experiment) data), dest);
        if (job != null) {
          job.run();
        }
        return;
      }

      throw new IllegalStateException(//
          "An Experiment can only be processed by an IConfiguredExperimentModule, but you provided a " //$NON-NLS-1$
              + TextUtils.className(this.m_module.getClass()));
    }
    throw new IllegalArgumentException(//
        "Only ExperimentSets or Experiments can be processed, but you passed in a " + //$NON-NLS-1$
            ((data != null) ? TextUtils.className(data.getClass()) : null));
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  final void _doJobs(final DataSet<?> data, final ISectionContainer dest) {
    final _DelayedSectionContainer delay;

    if (this.m_children == null) {
      this.__do(data, dest);
    } else {
      delay = new _DelayedSectionContainer(dest);
      try {
        this.__do(data, delay);
        super._doJobs(data, delay._getContainerForSubSections());
      } finally {
        delay._close();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (TextUtils.className(this.m_module.getClass()) + " (" + //$NON-NLS-1$
        this.m_module.toString() + ')');
  }
}
