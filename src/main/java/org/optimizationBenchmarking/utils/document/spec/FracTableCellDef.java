package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A fractional table.
 */
public final class FracTableCellDef extends TableCellDef {

  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /** the alignment */
  private final TableCellDef m_alignment;

  /** the fraction */
  private final double m_fraction;

  /**
   * Create the fractional table cell
   * 
   * @param alignment
   *          the alignment
   * @param fraction
   *          the fraction
   */
  public FracTableCellDef(final TableCellDef alignment,
      final double fraction) {
    super();

    if ((alignment != TableCellDef.CENTER)
        && (alignment != TableCellDef.LEFT)
        && (alignment != TableCellDef.RIGHT)) {
      throw new IllegalArgumentException(//
          "Table cell alignment must be either centered, left, or right, but is " //$NON-NLS-1$
              + alignment);
    }

    if ((fraction <= 0d) || (fraction >= 1d)) {
      throw new IllegalArgumentException(
          "Fractional table cell space must be execlusively-between 0 and 1, but is " //$NON-NLS-1$
              + fraction);
    }

    this.m_alignment = alignment;
    this.m_fraction = fraction;
  }

  /**
   * Get the table cell alignment
   * 
   * @return the table cell alignment
   */
  public final TableCellDef getAlignment() {
    return this.m_alignment;
  }

  /**
   * Get the table cell occupied space, in fractional relationship to the
   * overall table width
   * 
   * @return the table cell occupied space, in fractional relationship to
   *         the overall table width
   */
  public final double getFraction() {
    return this.m_fraction;
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_alignment.toText(textOut);
    textOut.append('[');
    textOut.append(this.m_fraction);
    textOut.append(']');
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(this.m_alignment.hashCode(),
        HashUtils.hashCode(this.m_fraction));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    FracTableCellDef c;

    if (o == this) {
      return true;
    }
    if (o instanceof FracTableCellDef) {
      c = ((FracTableCellDef) o);
      return (this.m_alignment.equals(c.m_alignment) && (this.m_fraction == c.m_fraction));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mo;
    mo = new MemoryTextOutput(16);
    this.toText(mo);
    return mo.toString();
  }
}
