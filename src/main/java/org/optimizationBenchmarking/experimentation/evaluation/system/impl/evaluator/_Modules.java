package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;

/** the holder class for the modules */
final class _Modules extends _PseudoModule {

  /** the descriptions */
  private final _Descriptions m_desc;
  /** the experiment statistics */
  private final _ExperimentStatistics m_experimentStatistics;
  /** the experiment set statistics */
  private final _ExperimentSetStatistics m_experimentSetStatistics;
  /** the appendices */
  private final _Appendices m_appendices;

  /**
   * create the module job
   * 
   * @param logger
   *          the logger
   * @param desc
   *          the descriptions
   * @param experimentStatistics
   *          the experiment statistics
   * @param experimentSetStatistics
   *          the experiment set statistics
   * @param appendices
   *          the appendices
   */
  _Modules(final Logger logger, final _Descriptions desc,
      final _ExperimentStatistics experimentStatistics,
      final _ExperimentSetStatistics experimentSetStatistics,
      final _Appendices appendices) {
    super(logger);

    int size;
    boolean have;

    size = 0;
    have = false;

    if ((desc == null) || (desc.m_children == null)
        || (desc.m_children.length <= 0)) {
      this.m_desc = null;
    } else {
      this.m_desc = desc;
      size++;
    }

    if ((experimentStatistics == null)
        || (experimentStatistics.m_children == null)
        || (experimentStatistics.m_children.length <= 0)) {
      this.m_experimentStatistics = null;
    } else {
      this.m_experimentStatistics = experimentStatistics;
      size++;
      have = true;
    }

    if ((experimentSetStatistics == null)
        || (experimentSetStatistics.m_children == null)
        || (experimentSetStatistics.m_children.length <= 0)) {
      this.m_experimentSetStatistics = null;
    } else {
      this.m_experimentSetStatistics = experimentSetStatistics;
      size++;
      have = true;
    }

    if ((appendices == null) || (appendices.m_children == null)
        || (appendices.m_children.length <= 0)) {
      this.m_appendices = null;
    } else {
      this.m_appendices = appendices;
      size++;
    }

    if (!have) {
      _ModulesBuilder._noStatisticModuleError();
    }

    this.m_children = new _PseudoModule[size];
    if (this.m_appendices != null) {
      this.m_children[--size] = this.m_appendices;
    }
    if (this.m_experimentSetStatistics != null) {
      this.m_children[--size] = this.m_experimentSetStatistics;
    }
    if (this.m_experimentStatistics != null) {
      this.m_children[--size] = this.m_experimentStatistics;
    }
    if (this.m_desc != null) {
      this.m_children[--size] = this.m_desc;
    }
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Root Module"; //$NON-NLS-1$
  }

  /**
   * do all the body jobs
   * 
   * @param data
   *          the data
   * @param body
   *          the document body
   */
  final void _bodyJobs(final ExperimentSet data, final IDocumentBody body) {

    if ((this.m_logger != null) && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Beginning to write output document body."); //$NON-NLS-1$
    }

    if (this.m_desc != null) {
      this.m_desc._doJobs(data, body);
    }
    if (this.m_experimentStatistics != null) {
      this.m_experimentStatistics._doJobs(data, body);
    }
    if (this.m_experimentSetStatistics != null) {
      this.m_experimentSetStatistics._doJobs(data, body);
    }

    if ((this.m_logger != null) && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Finished writing output document body."); //$NON-NLS-1$
    }
  }

  /**
   * do all the footer jobs
   * 
   * @param data
   *          the data
   * @param footer
   *          the document footer
   */
  final void _footerJobs(final ExperimentSet data,
      final IDocumentBody footer) {
    if (this.m_appendices != null) {

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINE))) {
        this.m_logger.fine("Beginning to write output document footer."); //$NON-NLS-1$
      }

      this.m_appendices._doJobs(data, footer);

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINE))) {
        this.m_logger.fine("Finished writing output document footer."); //$NON-NLS-1$
      }
    }
  }
}
