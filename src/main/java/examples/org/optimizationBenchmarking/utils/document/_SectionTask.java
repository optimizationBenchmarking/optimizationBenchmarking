package examples.org.optimizationBenchmarking.utils.document;

import java.util.concurrent.RecursiveAction;

import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;

/** the section task */
final class _SectionTask extends RecursiveAction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the document example */
  private final RandomDocumentExample m_de;

  /** the section container */
  private final ISectionContainer m_sc;

  /** the depth */
  private final int m_sectionDepth;

  /**
   * The section depth
   *
   * @param de
   *          the owning example
   * @param sc
   *          the container
   * @param sectionDepth
   *          the depth
   */
  _SectionTask(final RandomDocumentExample de, final ISectionContainer sc,
      final int sectionDepth) {
    super();
    this.m_de = de;
    this.m_sc = sc;
    this.m_sectionDepth = sectionDepth;
  }

  /** {@inheritDoc} */
  @Override
  protected final void compute() {
    this.m_de._createSection(this.m_sc, this.m_sectionDepth);
  }
}
