package org.optimizationBenchmarking.utils.chart.impl.jfree;

import org.jfree.data.DomainOrder;
import org.jfree.data.xy.XYDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLine2D;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** a 2D line for jfreechart */
final class _JFreeChartXYDataset extends
    _JFreeChartDataset<CompiledLine2D> implements XYDataset {

  /** the shortcut to the data matrices */
  private final IMatrix[] m_matrices;
  /** the type switch */
  private final int[] m_typeSwitches;
  /** the domain order */
  private DomainOrder m_order;

  /**
   * create the line dataset
   * 
   * @param lines
   *          the lines
   */
  _JFreeChartXYDataset(final ArrayListView<CompiledLine2D> lines) {
    super(lines);

    final int size;
    CompiledLine2D line;
    int i;

    size = lines.size();
    this.m_matrices = new IMatrix[size];
    this.m_typeSwitches = new int[size];

    for (i = size; (--i) >= 0;) {
      line = lines.get(i);
      this.m_matrices[i] = line.getData();

      switch (line.getType()) {
        case STAIRS_KEEP_LEFT: {
          this.m_typeSwitches[i] = 1;
          break;
        }
        case STAIRS_PREVIEW_RIGHT: {
          this.m_typeSwitches[i] = 2;
          break;
        }
        default: {
          this.m_typeSwitches[i] = 0;
        }
      }
    }

    this.m_order = null;

  }

  /** {@inheritDoc} */
  @Override
  public final DomainOrder getDomainOrder() {
    int order, i, cmp;
    double old, cur;

    if (this.m_order != null) {
      return this.m_order;
    }
    order = 0;
    for (final IMatrix mat : this.m_matrices) {
      i = mat.m();
      cur = mat.getDouble(--i, 0);

      for (; (--i) >= 0;) {
        old = cur;
        cur = mat.getDouble(i, 0);
        cmp = Double.compare(cur, old);
        if (cmp < 0) {
          if (order > 0) {
            return (this.m_order = DomainOrder.NONE);
          }
          order = (-1);
        } else {
          if (cmp > 0) {
            if (order < 0) {
              return (this.m_order = DomainOrder.NONE);
            }
            order = 1;
          }
        }
      }
    }

    switch (order) {
      case (-1): {
        return (this.m_order = DomainOrder.ASCENDING);
      }
      case 0: {
        return (this.m_order = DomainOrder.NONE);
      }
      case 1: {
        return (this.m_order = DomainOrder.DESCENDING);
      }
      default: {
        throw new IllegalStateException();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getItemCount(final int series) {
    final int count;

    count = this.m_matrices[series].m();
    return ((this.m_typeSwitches[series] <= 0) ? count
        : ((count << 1) - 1));
  }

  /** {@inheritDoc} */
  @Override
  public final Number getX(final int series, final int item) {
    final IMatrix matrix;

    matrix = this.m_matrices[series];
    if (matrix.isIntegerMatrix()) {
      return Long.valueOf(//
          matrix.getLong(this.__getXIndex(series, item), 0));
    }
    return Double.valueOf(this.getXValue(series, item));
  }

  /**
   * get the index along the x-axis
   * 
   * @param series
   *          the series
   * @param item
   *          the item
   * @return the index
   */
  private final int __getXIndex(final int series, final int item) {
    switch (this.m_typeSwitches[series]) {
      case 1: {
        return ((item + 1) >>> 1);
      }
      case 2: {
        return (item >>> 1);
      }
      default: {
        return item;
      }
    }
  }

  /**
   * get the index along the y-axis
   * 
   * @param series
   *          the series
   * @param item
   *          the item
   * @return the index
   */
  private final int __getYIndex(final int series, final int item) {
    switch (this.m_typeSwitches[series]) {
      case 1: {
        return (item >>> 1);
      }
      case 2: {
        return ((item + 1) >>> 1);
      }
      default: {
        return item;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double getXValue(final int series, final int item) {
    return _JFreeChartDataset._f(this.m_matrices[series].getDouble(
        this.__getXIndex(series, item), 0));
  }

  /** {@inheritDoc} */
  @Override
  public final Number getY(final int series, final int item) {
    final IMatrix matrix;

    matrix = this.m_matrices[series];
    if (matrix.isIntegerMatrix()) {
      return Long.valueOf(//
          matrix.getLong(this.__getYIndex(series, item), 1));
    }
    return Double.valueOf(this.getYValue(series, item));
  }

  @Override
  public final double getYValue(final int series, final int item) {
    return _JFreeChartDataset._f(this.m_matrices[series].getDouble(
        this.__getYIndex(series, item), 1));
  }

}
