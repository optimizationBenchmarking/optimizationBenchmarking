package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.units.ELength;

/**
 * A set of pre-defined figure sizes, defined relatively to the document
 * structure (page width, height, column count, ...).
 */
public enum EFigureSize {

  /** a full page image */
  PAGE_FULL(1, 1, true),

  /**
   * a big figure spanning the page width, but not necessarily the page
   * height (with esthetically pleasing height)
   */
  PAGE_WIDE(1, -1, true),

  /** two figures per row and per page height */
  PAGE_2_BY_2(2, 2, true),

  /** two figures per row, with esthetically pleasing height */
  PAGE_2_PER_ROW(2, -1, true),

  /** three figures per row, with esthetically pleasing height */
  PAGE_3_PER_ROW(3, -1, true),

  /** four figures per row, with esthetically pleasing height */
  PAGE_4_PER_ROW(4, -1, true),

  /** five figures per row, with esthetically pleasing height */
  PAGE_5_PER_ROW(5, -1, true),

  /** six figures per row, with esthetically pleasing height */
  PAGE_6_PER_ROW(6, -1, true),

  /** a big figure spanning a whole column */
  COLUMN_FULL(1, 1, false),

  /**
   * a big figure spanning a whole column, but not necessarily the whole
   * page height (with esthetically pleasing height)
   */
  COLUMN_WIDE(1, -1, false),

  /** two figures per row in a column, with esthetically pleasing height */
  COLUMN_2_PER_ROW(2, -1, false),

  ;

  /**
   * the number of figures of that size that should fit along the x-axis,
   * {@code -1} if unspecified
   */
  private final int m_nx;
  /**
   * the number of figures of that size that should fit along the y-axis,
   * {@code -1} if unspecified
   */
  private final int m_ny;
  /**
   * does the x-axis span over all columns (i.e., the complete page,
   * {@code true}), or does it only span a single columns ({@code false})?
   */
  private final boolean m_spansAllColumns;

  /**
   * create a default figure size
   * 
   * @param nx
   *          the number of figures of that size that should fit along the
   *          x-axis, {@code -1} if unspecified
   * @param ny
   *          the number of figures of that size that should fit along the
   *          y-axis, {@code -1} if unspecified
   * @param spansAllColumns
   *          does the x-axis span over all columns (i.e., the complete
   *          page, {@code true}), or does it only span a single columns (
   *          {@code false})?
   */
  private EFigureSize(final int nx, final int ny,
      final boolean spansAllColumns) {
    this.m_nx = nx;
    this.m_ny = ny;
    this.m_spansAllColumns = spansAllColumns;
  }

  /**
   * Get the number of figures of that size that should fit along the
   * x-axis, {@code -1} if unspecified
   * 
   * @return the number of figures of that size that should fit along the
   *         x-axis, {@code -1} if unspecified
   */
  public final int getNX() {
    return this.m_nx;
  }

  /**
   * Get the number of figures of that size that should fit along the
   * y-axis, {@code -1} if unspecified
   * 
   * @return the number of figures of that size that should fit along the
   *         y-axis, {@code -1} if unspecified
   */
  public final int getNY() {
    return this.m_ny;
  }

  /**
   * Get does the x-axis span over all columns (i.e., the complete page,
   * {@code true}), or does it only span a single columns ({@code false})?
   * 
   * @return does the x-axis span over all columns (i.e., the complete
   *         page, {@code true}), or does it only span a single columns (
   *         {@code false} )?
   */
  public final boolean spansAllColumns() {
    return this.m_spansAllColumns;
  }

  /**
   * round to full integers if it makes sense
   * 
   * @param d
   *          the number
   * @return the rounded result
   */
  private static final double __round(final double d) {
    return ((d > 3d) ? Math.ceil(d) : d);
  }

  /**
   * Return a reasonable approximation of the size of a figure under a
   * given page size and column count. This approximation accounts for
   * spacing between figures, i.e., if there are more than one figure per
   * row or multiple figures in the vertical direction, we try to leave
   * some space between them. For the horizontal direction, this is quite
   * straightforward. In the vertical direction, the problem of figure
   * captions arises. Since we do not know the vertical size of a caption
   * in advance, it is impossible to compute sizes of, e.g., 2*2 figures,
   * so that all of them fit on a page. Instead, we try to leave some space
   * for captions and hope for the best. (This is why this method is called
   * {@link #approximateSize(PageDimension) approximateSize} and not
   * <code>getSize</code>).
   * 
   * @param page
   *          the page size dimensions
   * @return an approximated size of the figure
   */
  public final PhysicalDimension approximateSize(final PageDimension page) {
    final ELength pageUnit;
    final double availableWidth, availableHeight, minExtend, minWidth, minHeight;
    double width, height;
    boolean hasAvailableWidth, hasAvailableHeight, hasWidth, hasHeight;

    pageUnit = page.getUnit();

    // an image should be at least 5mm in each direction
    minExtend = 16d;

    // get the maximum permissible width
    availableWidth = pageUnit.convertTo(
        (this.m_spansAllColumns ? page.getWidth()//
            : page.getColumnWidth()), ELength.POINT);
    hasAvailableWidth = ((availableWidth > 0d) && (availableWidth < Double.MAX_VALUE));

    // now let's compute an approximation of the image width
    if (hasAvailableWidth) {
      // an image should be at least 2% of the available width
      minWidth = Math.max(minExtend,
          EFigureSize.__round(0.02d * availableWidth));
    } else {
      minWidth = minExtend;
    }

    // ok, we use a fraction of the available width
    hasWidth = ((this.m_nx > 0) && hasAvailableWidth);
    if (hasWidth) {
      width = availableWidth;

      if (this.m_nx > 1) {
        // if more than one image per row, insert some reasonable spacing
        width -= ((this.m_nx - 1) * //
        (Math.max(10d, EFigureSize.__round(0.01d * width))));
        width /= this.m_nx;
      }
    } else {
      width = Double.NaN;
    }

    // now let's compute an approximation of the image height
    availableHeight = pageUnit.convertTo(page.getHeight(), ELength.POINT);
    hasAvailableHeight = ((availableHeight > 0d) && (availableHeight < Double.MAX_VALUE));

    if (hasAvailableHeight) {
      // an image should be at least 2% of the available width
      minHeight = Math.max(minExtend,
          EFigureSize.__round(0.02d * availableHeight));
    } else {
      minHeight = minExtend;
    }

    // is the image height a fraction of the page height?
    hasHeight = (this.m_ny > 0);
    if (hasHeight) {
      // always leave some spacing for a caption: if more than one row, add
      // space for the overall figure series caption
      // -- this is the sketchy part --
      height = (availableHeight - (//
      ((this.m_ny <= 1) ? 1 : (this.m_ny + 1)) * //
      (Math.max(43d, EFigureSize.__round(0.03d * availableHeight)))));
      height /= this.m_ny;
    } else {
      height = Double.NaN;
    }

    // check if we have a width and height
    if (!hasWidth) {
      if (hasHeight) {
        // no width, but a height: use golden ration
        width = EFigureSize.__round(height / MathConstants.GOLDEN_RATIO);
      } else {
        // neither height nor width: use minimum width
        width = minWidth;
      }
    }

    if (!hasHeight) {
      if (hasWidth) {
        // no height but width: use golden ration
        height = EFigureSize.__round(width * MathConstants.GOLDEN_RATIO);
      } else {
        // neither height nor width: use minimum height
        height = minHeight;
      }
    }

    if (hasAvailableWidth) {
      // check if too wide
      width = Math.min(width, availableWidth);
    }
    width = Math.max(width, minWidth); // or too small

    if (hasAvailableHeight) {
      // check if too high
      height = Math.min(height, availableHeight);
    }
    height = Math.max(height, minHeight);// or too small

    // create dimension
    return new PhysicalDimension(width, height, ELength.POINT);
  }
}
