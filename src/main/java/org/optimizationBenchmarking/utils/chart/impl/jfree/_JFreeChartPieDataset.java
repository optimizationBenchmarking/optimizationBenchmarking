package org.optimizationBenchmarking.utils.chart.impl.jfree;

import org.jfree.data.general.PieDataset;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataScalar;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** A pie data set */
final class _JFreeChartPieDataset extends
    _JFreeChartDataset<CompiledDataScalar> implements PieDataset {

  /**
   * create
   *
   * @param data
   *          the data
   */
  _JFreeChartPieDataset(final ArrayListView<CompiledDataScalar> data) {
    super(data);
  }

  /** {@inheritDoc} */
  @Override
  public final Number getValue(final Comparable key) {
    final int idx;

    idx = this.getIndex(key);

    if (idx >= 0) {
      return _JFreeChartPieDataset.__value(this.m_data.get(idx).getData());
    }

    throw new IllegalArgumentException("Key '" + key + //$NON-NLS-1$
        "' does not exist in pie chart."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final Number getValue(final int index) {
    return _JFreeChartPieDataset.__value(this.m_data.get(index).getData());
  }

  /**
   * prepare a numerical value
   *
   * @param in
   *          the input
   * @return the return value
   */
  private static final Number __value(final Number in) {
    final double a, b;
    if ((in instanceof Double) || (in instanceof Float)) {
      a = in.doubleValue();
      b = _JFreeChartDataset._f(a);
      if (b != a) {
        return Double.valueOf(b);
      }
    }
    return in;
  }
}
