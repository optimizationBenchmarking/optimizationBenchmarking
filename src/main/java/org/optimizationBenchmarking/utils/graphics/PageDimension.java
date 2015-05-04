package org.optimizationBenchmarking.utils.graphics;

import java.awt.geom.Dimension2D;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.units.ELength;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * Dimensions of a page of output
 */
public class PageDimension extends PhysicalDimension {

  /** "in" */
  private static final char[] IN = { ' ', 'i', 'n', ' ' };

  /** "columns " */
  private static final char[] COLS = { ' ', 'c', 'o', 'l', 'u', 'm', 'n',
    's', ' ', '\u00e0', ' ' };

  /** the number of columns */
  private final int m_columnCount;

  /** the width of a column */
  private final double m_columnWidth;

  /**
   * create a new page dimension
   *
   * @param width
   *          the width
   * @param height
   *          the height
   * @param columnCount
   *          the number of columns
   * @param columnWidth
   *          the width of a column
   * @param unit
   *          the length unit
   */
  public PageDimension(final double width, final double height,
      final int columnCount, final double columnWidth, final ELength unit) {
    super(width, height, unit);

    final double d;

    if (columnCount <= 0) {
      throw new IllegalArgumentException(//
          "Number of columns must be positive, but is " //$NON-NLS-1$
          + columnCount);
    }
    if ((d = (columnCount * columnWidth)) > width) {
      throw new IllegalArgumentException("Total width " + d + //$NON-NLS-1$
          " occupied by " + columnCount + //$NON-NLS-1$
          " columns (" + columnWidth + //$NON-NLS-1$
          " wide each) is bigger than paper width " + //$NON-NLS-1$
          width);
    }

    this.m_columnCount = columnCount;
    this.m_columnWidth = ((columnCount > 1) ? columnWidth : width);
  }

  /**
   * create a new page dimension
   *
   * @param width
   *          the width
   * @param height
   *          the height
   * @param unit
   *          the length unit
   */
  public PageDimension(final double width, final double height,
      final ELength unit) {
    this(width, height, 1, width, unit);
  }

  /**
   * create a new page dimension
   *
   * @param copy
   *          the physical dimension to copy
   */
  public PageDimension(final PhysicalDimension copy) {
    this(copy.getWidth(), copy.getHeight(), copy.getUnit());
  }

  /**
   * create a new page dimension
   *
   * @param copy
   *          the dimension to copy
   * @param unit
   *          the length unit
   */
  public PageDimension(final Dimension2D copy, final ELength unit) {
    this(copy.getWidth(), copy.getHeight(), unit);
  }

  /**
   * Get the number of columns
   *
   * @return the number of columns
   */
  public final int getColumnCount() {
    return this.m_columnCount;
  }

  /**
   * Get the width of a column
   *
   * @return the width of a column
   */
  public final double getColumnWidth() {
    return this.m_columnWidth;
  }

  /** {@inheritDoc} */
  @Override
  public PageDimension convertTo(final ELength unit) {
    final ELength u;

    u = this.getUnit();
    if (unit == u) {
      return this;
    }
    if (unit == null) {
      throw new IllegalArgumentException("Length unit must not be null."); //$NON-NLS-1$
    }
    return new PageDimension(//
        u.convertTo(this.getWidth(), unit),//
        u.convertTo(this.getHeight(), unit),//
        this.m_columnCount,//
        u.convertTo(this.m_columnWidth, unit),//
        unit);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final PageDimension dim;

    if (o == this) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (o instanceof PageDimension) {
      dim = ((PageDimension) o);

      return ((this.m_columnCount == dim.m_columnCount)//
          && (EComparison.EQUAL.compare(this.m_width, dim.m_width))//
          && (EComparison.EQUAL.compare(this.m_height, dim.m_height))//
          && (EComparison.EQUAL.compare(this.m_columnWidth,
              dim.m_columnWidth))//
              && EComparison.equals(this.m_unit, dim.m_unit));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_height),//
                HashUtils.hashCode(this.m_width)),//
                HashUtils.combineHashes(//
                    HashUtils.hashCode(this.m_columnWidth),//
                    HashUtils.hashCode(this.m_columnCount))),//
                    HashUtils.hashCode(this.m_unit));
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    final int i;

    super.toText(textOut);

    i = this.m_columnCount;
    if (i > 1) {
      textOut.append(PageDimension.IN);
      textOut.append(i);
      textOut.append(PageDimension.COLS);
      textOut.append(this.m_columnWidth);
      textOut.append(this.m_unit.getShortcut());
    }
  }

}
