package org.optimizationBenchmarking.experimentation.evaluation.impl.evaluator;

import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** A wrapper aroung a section container. */
final class _DelayedSectionContainer implements ISectionContainer {

  /** the actual section container */
  private final ISectionContainer m_real;

  /** the section */
  private _DelayedSection m_section;

  /**
   * create a delayed section
   *
   * @param real
   *          the real section
   */
  _DelayedSectionContainer(final ISectionContainer real) {
    super();
    this.m_real = real;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    throw new IllegalStateException(//
        "This method should not be called."); //$NON-NLS-1$
  }

  /**
   * Obtain the container for the sub-sections. Do not close this
   * container.
   *
   * @return the container for the sub-sections
   */
  final ISectionContainer _getContainerForSubSections() {
    if (this.m_section != null) {
      if (this.m_section.m_body != null) {
        return this.m_section.m_body;
      }
    }
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized ISection section(final ILabel useLabel) {
    if (this.m_section != null) {
      throw new IllegalStateException(//
          "Each module can only create one section."); //$NON-NLS-1$
    }
    this.m_section = new _DelayedSection(this.m_real.section(useLabel));
    return this.m_section;
  }

  /** close */
  final void _close() {
    if (this.m_section != null) {
      this.m_section._close();
    }
  }
}
