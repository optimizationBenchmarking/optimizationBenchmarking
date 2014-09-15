package org.optimizationBenchmarking.utils.document.spec;

import java.awt.geom.Dimension2D;

import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * Dimensions of a page of output
 */
public class PageDimension extends PhysicalDimension {

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
    this.m_columnWidth = columnWidth;
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
}
