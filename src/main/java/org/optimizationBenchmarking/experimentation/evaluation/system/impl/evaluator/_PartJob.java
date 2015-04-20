package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationJob;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/**
 * The holder for jobs which add document parts: The sets of description
 * jobs, experiment jobs, and experiment set jobs
 */
abstract class _PartJob extends _PseudoJob {
  /** the data */
  private final IExperimentSet m_data;

  /**
   * create the descriptions module
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _PartJob(final IExperimentSet data, final Logger logger,
      final IEvaluationJob[] children) {
    super(logger, children);
    this.m_data = data;
  }

  /** {@inheritDoc} */
  @Override
  String _getName() {
    return "Document Part Job"; //$NON-NLS-1$
  }

  /**
   * Write the introduction of the section containing all sub-jobs.
   * 
   * @param data
   *          the data
   * @param body
   *          the section body
   */
  void _writeSectionIntroduction(final IExperimentSet data,
      final ISectionBody body) {
    //
  }

  /**
   * Write the title of the section containing all sub-jobs.
   * 
   * @param data
   *          the data
   * @param title
   *          the title
   */
  void _writeSectionTitle(final IExperimentSet data, final IPlainText title) {
    //
  }

  /**
   * A new section is created if at least this many sub-jobs are founds
   * 
   * @return the minimum number of sub-sections required to justify
   *         grouping them into a section
   */
  int _getMinSectionsToOpenNewSection() {
    return 2;
  }

  /** {@inheritDoc} */
  @Override
  public final void main(final ISectionContainer dest) {

    if ((this.m_children != null) && (this.m_children.length > 0)) {

      if (this.m_children.length >= this._getMinSectionsToOpenNewSection()) {
        try (final ISection section = dest.section(null)) {
          try (final IPlainText title = section.title()) {
            this._writeSectionTitle(this.m_data, title);
          }
          try (final ISectionBody body = section.body()) {
            this._writeSectionIntroduction(this.m_data, body);
            this._runSubJobs(body);
          }
        }
      } else {
        this._runSubJobs(dest);
      }
    }
  }
}
