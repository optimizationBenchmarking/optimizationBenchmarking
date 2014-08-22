package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the table cell should be right-aligned */
final class _TableCellRight extends TableCellDef {

  /** the serial version */
  private static final long serialVersionUID = 1L;

  /** create */
  _TableCellRight() {
    super();
  }

  /**
   * read resolve
   * 
   * @return the resolved object
   */
  private final Object readResolve() {
    return TableCellDef.RIGHT;
  }

  /**
   * write replace
   * 
   * @return the replace object
   */
  private final Object writeReplace() {
    return TableCellDef.RIGHT;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 3;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof _TableCellRight);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "r"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    textOut.append('r');
  }
}
