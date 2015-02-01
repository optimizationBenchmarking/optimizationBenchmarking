package org.optimizationBenchmarking.experimentation.evaluation.system.impl.evaluator;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/** A section whose closing is delayed. */
final class _DelayedSection implements ISection {

  /** the actual section */
  private final ISection m_real;

  /** the internal section body */
  _DelayedSectionBody m_body;

  /** the title */
  private boolean m_title;

  /**
   * create a delayed section
   * 
   * @param real
   *          the real section
   */
  _DelayedSection(final ISection real) {
    super();
    this.m_real = real;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    // do nothing
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_real.getStyles();
  }

  /** {@inheritDoc} */
  @Override
  public final ILabel getLabel() {
    return this.m_real.getLabel();
  }

  /** {@inheritDoc} */
  @Override
  public final IComplexText title() {
    if (this.m_title) {
      throw new IllegalStateException(//
          "Each section can only have one title."); //$NON-NLS-1$
    }
    this.m_title = true;
    return this.m_real.title();
  }

  /** {@inheritDoc} */
  @Override
  public final ISectionBody body() {
    if (!(this.m_title)) {
      throw new IllegalStateException(//
          "The section body can only be created after the title."); //$NON-NLS-1$
    }
    if (this.m_body != null) {
      throw new IllegalStateException(//
          "Each section can only have one body."); //$NON-NLS-1$
    }
    this.m_body = new _DelayedSectionBody(this.m_real.body());
    return this.m_body;
  }

  /** Close the section for real. */
  final void _close() {
    try {
      if (this.m_body != null) {
        this.m_body._close();
      }
    } finally {
      this.m_real.close();
    }
  }

}
