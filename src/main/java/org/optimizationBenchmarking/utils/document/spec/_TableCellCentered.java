package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the table cell should be centered */
final class _TableCellCentered extends TableCellDef {

  /** the serial version */
  private static final long serialVersionUID = 1L;

  /** create */
  _TableCellCentered() {
    super();
  }

  /**
   * read resolve
   * 
   * @return the resolved object
   */
  private final Object readResolve() {
    return TableCellDef.CENTER;
  }

  /**
   * write replace
   * 
   * @return the replace object
   */
  private final Object writeReplace() {
    return TableCellDef.CENTER;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 2;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof _TableCellCentered);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "c"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append('c');
  }
}
