package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;

/** the holder class for the modules */
final class _MainJob extends _PseudoJob {

  /** the descriptions */
  private final _DescriptionJobs m_descriptionJobs;
  /** the experiment statistics */
  private final _ExperimentJobs m_experimentJobs;
  /** the experiment set statistics */
  private final _ExperimentSetJobs m_experimentSetJobs;
  /** the appendixJobs */
  private final _AppendixJobs m_appendixJobs;

  /**
   * create the module job
   * 
   * @param logger
   *          the logger
   * @param descriptionJobs
   *          the descriptions
   * @param experimentJobs
   *          the experiment statistics
   * @param experimentSetJobs
   *          the experiment set statistics
   * @param appendixJobs
   *          the appendixJobs
   */
  _MainJob(final Logger logger, final _DescriptionJobs descriptionJobs,
      final _ExperimentJobs experimentJobs,
      final _ExperimentSetJobs experimentSetJobs,
      final _AppendixJobs appendixJobs) {
    super(logger);

    int size;

    size = 0;

    if ((descriptionJobs == null) || (descriptionJobs.m_children == null)
        || (descriptionJobs.m_children.length <= 0)) {
      this.m_descriptionJobs = null;
    } else {
      this.m_descriptionJobs = descriptionJobs;
      size++;
    }

    if ((experimentJobs == null) || (experimentJobs.m_children == null)
        || (experimentJobs.m_children.length <= 0)) {
      this.m_experimentJobs = null;
    } else {
      this.m_experimentJobs = experimentJobs;
      size++;
    }

    if ((experimentSetJobs == null)
        || (experimentSetJobs.m_children == null)
        || (experimentSetJobs.m_children.length <= 0)) {
      this.m_experimentSetJobs = null;
    } else {
      this.m_experimentSetJobs = experimentSetJobs;
      size++;
    }

    if ((appendixJobs == null) || (appendixJobs.m_children == null)
        || (appendixJobs.m_children.length <= 0)) {
      this.m_appendixJobs = null;
    } else {
      this.m_appendixJobs = appendixJobs;
      size++;
    }

    this.m_children = new _PseudoJob[size];
    if (this.m_appendixJobs != null) {
      this.m_children[--size] = this.m_appendixJobs;
    }
    if (this.m_experimentSetJobs != null) {
      this.m_children[--size] = this.m_experimentSetJobs;
    }
    if (this.m_experimentJobs != null) {
      this.m_children[--size] = this.m_experimentJobs;
    }
    if (this.m_descriptionJobs != null) {
      this.m_children[--size] = this.m_descriptionJobs;
    }
  }

  /** {@inheritDoc} */
  @Override
  final String _getName() {
    return "Main Job"; //$NON-NLS-1$
  }

  /**
   * do all the body jobs
   * 
   * @param body
   *          the document body
   */
  final void _bodyJobs(final IDocumentBody body) {

    if ((this.m_logger != null) && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Beginning to write document body."); //$NON-NLS-1$
    }

    if (this.m_descriptionJobs != null) {
      this.m_descriptionJobs.main(body);
    }
    if (this.m_experimentJobs != null) {
      this.m_experimentJobs.main(body);
    }
    if (this.m_experimentSetJobs != null) {
      this.m_experimentSetJobs.main(body);
    }

    if ((this.m_logger != null) && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Finished writing document body."); //$NON-NLS-1$
    }
  }

  /**
   * do all the footer jobs
   * 
   * @param footer
   *          the document footer
   */
  final void _footerJobs(final IDocumentBody footer) {
    if (this.m_appendixJobs != null) {

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINE))) {
        this.m_logger.fine("Beginning to write document footer."); //$NON-NLS-1$
      }

      this.m_appendixJobs.main(footer);

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.FINE))) {
        this.m_logger.fine("Finished writing document footer."); //$NON-NLS-1$
      }
    }
  }
}
