package org.optimizationBenchmarking.utils.chart.impl.jfree;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledLine2D;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** a 2D line for jfreechart */
final class _JFreeChartLines2D implements XYDataset {

  /** the maximum allowed coordinate */
  private static final double MAX_COORD = (1e20);
  /** the minimum allowed coordinate */
  private static final double MIN_COORD = (-_JFreeChartLines2D.MAX_COORD);

  /** the internally shared group */
  private static final DatasetGroup GROUP = new DatasetGroup();

  /** the shortcut to the data matrices */
  private final IMatrix[] m_matrices;
  /** the keys */
  private final String[] m_keys;
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
  _JFreeChartLines2D(final ArrayListView<CompiledLine2D> lines) {
    super();

    final int size;
    CompiledLine2D line;
    int i;
    String key;

    size = lines.size();
    this.m_matrices = new IMatrix[size];
    this.m_keys = new String[size];
    this.m_typeSwitches = new int[size];

    for (i = size; (--i) >= 0;) {
      line = lines.get(i);
      this.m_matrices[i] = line.getData();
      key = line.getTitle();
      if (key == null) {
        key = Integer.toString(line.getID());
      }
      this.m_keys[i] = key;

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

  /**
   * format a double
   * 
   * @param d
   *          the double
   * @return the formatted double
   */
  private static final double _f(final double d) {
    return ((d <= _JFreeChartLines2D.MIN_COORD) ? _JFreeChartLines2D.MIN_COORD
        : ((d >= _JFreeChartLines2D.MAX_COORD) ? _JFreeChartLines2D.MAX_COORD
            : d));
  }

  /** {@inheritDoc} */
  @Override
  public final int getSeriesCount() {
    return this.m_matrices.length;
  }

  /** {@inheritDoc} */
  @Override
  public final Comparable getSeriesKey(final int series) {
    return this.m_keys[series];
  }

  /** {@inheritDoc} */
  @Override
  public final int indexOf(final Comparable seriesKey) {
    int i;

    for (i = this.m_keys.length; (--i) >= 0;) {
      if (EComparison.equals(seriesKey, this.m_keys[i])) {
        return i;
      }
    }
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final void addChangeListener(final DatasetChangeListener listener) {
    // do nothing, since there won't be any changes
  }

  /** {@inheritDoc} */
  @Override
  public final void removeChangeListener(
      final DatasetChangeListener listener) {
    // do nothing, since there won't be any changes
  }

  /** {@inheritDoc} */
  @Override
  public final DatasetGroup getGroup() {
    return _JFreeChartLines2D.GROUP;
  }

  /** {@inheritDoc} */
  @Override
  public final void setGroup(final DatasetGroup group) {
    throw new UnsupportedOperationException();
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
    return Double.valueOf(this.getXValue(series, item));
  }

  /** {@inheritDoc} */
  @Override
  public final double getXValue(final int series, final int item) {
    final int idx;

    switch (this.m_typeSwitches[series]) {
      case 1: {
        idx = ((item + 1) >>> 1);
        break;
      }
      case 2: {
        idx = (item >>> 1);
        break;
      }
      default: {
        idx = item;
        break;
      }
    }

    return _JFreeChartLines2D
        ._f(this.m_matrices[series].getDouble(idx, 0));
  }

  /** {@inheritDoc} */
  @Override
  public final Number getY(final int series, final int item) {
    return Double.valueOf(this.getYValue(series, item));
  }

  @Override
  public final double getYValue(final int series, final int item) {
    final int idx;

    switch (this.m_typeSwitches[series]) {
      case 1: {
        idx = (item >>> 1);
        break;
      }
      case 2: {
        idx = ((item + 1) >>> 1);
        break;
      }
      default: {
        idx = item;
        break;
      }
    }

    return _JFreeChartLines2D
        ._f(this.m_matrices[series].getDouble(idx, 1));
  }

}
