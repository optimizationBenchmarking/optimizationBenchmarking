package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.data.DataSet;
import org.optimizationBenchmarking.experimentation.data.ExperimentSet;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** The holder for the descriptions */
abstract class _Part extends _PseudoModule {

  /** the label */
  private ILabel m_label;

  /**
   * create the descriptions module
   * 
   * @param logger
   *          the logger
   * @param children
   *          the children
   */
  _Part(final Logger logger, final _PseudoModule[] children) {
    super(logger, children);
  }

  /** {@inheritDoc} */
  @Override
  String _getName() {
    return "Document Part"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final void _doInitJobs(final ExperimentSet data, final IDocument document) {
    final ILabel label;

    if (this.m_label != null) {
      throw new IllegalStateException("Label has already been set."); //$NON-NLS-1$
    }

    if ((this.m_children != null) && (this.m_children.length > 0)) {
      label = document.createLabel(ELabelType.SECTION);
      if (label == null) {
        throw new IllegalArgumentException("Label must not be null.");//$NON-NLS-1$
      }
      this.m_label = label;
    }

    super._doInitJobs(data, document);
  }

  /**
   * Write the introduction
   * 
   * @param data
   *          the data
   * @param body
   *          the section body
   */
  abstract void _writeIntro(final ExperimentSet data,
      final ISectionBody body);

  /**
   * Write the title
   * 
   * @param data
   *          the data
   * @param title
   *          the title
   */
  abstract void _title(final ExperimentSet data, final IPlainText title);

  /**
   * Write the body
   * 
   * @param data
   *          the data
   * @param body
   *          the body
   */
  void _body(final ExperimentSet data, final ISectionBody body) {
    super._doJobs(data, body);
  }

  /** {@inheritDoc} */
  @Override
  final void _doJobs(final DataSet<?> data, final ISectionContainer dest) {
    final ExperimentSet experiments;

    if ((this.m_children != null) && (this.m_children.length > 0)) {
      if ((data != null) && (data instanceof ExperimentSet)) {
        experiments = ((ExperimentSet) data);

        if (this.m_label == null) {
          throw new IllegalStateException(//
              "Initialization jobs have not been executed."); //$NON-NLS-1$
        }

        try (final ISection section = dest.section(this.m_label)) {
          try (final IPlainText title = section.title()) {
            this._title(experiments, title);
          }
          try (final ISectionBody body = section.body()) {
            this._writeIntro(experiments, body);
            this._body(experiments, body);
          }
        }
      } else {
        throw new IllegalArgumentException(
            "Data must be an experiment set."); //$NON-NLS-1$
      }
    }
  }
}
