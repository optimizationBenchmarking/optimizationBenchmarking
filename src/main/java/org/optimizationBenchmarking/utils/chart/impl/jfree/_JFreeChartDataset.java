package org.optimizationBenchmarking.utils.chart.impl.jfree;

import java.util.List;

import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.optimizationBenchmarking.utils.chart.impl.abstr.CompiledDataElement;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * a base class for JFreeChart data sets.
 * 
 * @param <T>
 *          the data element type
 */
class _JFreeChartDataset<T extends CompiledDataElement> implements Dataset {

  /** the maximum allowed coordinate */
  static final double MAX_COORD = (1e20);
  /** the minimum allowed coordinate */
  static final double MIN_COORD = (-_JFreeChartDataset.MAX_COORD);

  /** the internally shared group */
  static final DatasetGroup GROUP = new DatasetGroup();

  /** the data list */
  final ArrayListView<T> m_data;

  /** the set of keys */
  private final ArraySetView<Integer> m_keySet;

  /**
   * create the dataset
   * 
   * @param data
   *          the data
   */
  _JFreeChartDataset(final ArrayListView<T> data) {
    super();

    final int size;
    final Integer[] keys;
    int i;

    this.m_data = data;

    size = data.size();
    keys = new Integer[size];

    for (i = size; (--i) >= 0;) {
      keys[i] = Integer.valueOf(data.get(i).getID());
    }
    this.m_keySet = new ArraySetView<>(keys);
  }

  /**
   * Get the number of items
   * 
   * @return the number of items
   */
  public final int getItemCount() {
    return this.m_data.size();
  }

  /**
   * format a double
   * 
   * @param d
   *          the double
   * @return the formatted double
   */
  static final double _f(final double d) {
    return ((d <= _JFreeChartDataset.MIN_COORD) ? _JFreeChartDataset.MIN_COORD
        : ((d >= _JFreeChartDataset.MAX_COORD) ? _JFreeChartDataset.MAX_COORD
            : d));
  }

  /**
   * Get the key of the given index
   * 
   * @param index
   *          the index
   * @return the key
   */
  @SuppressWarnings("rawtypes")
  public final Comparable getKey(final int index) {
    return this.m_keySet.get(index);
  }

  /**
   * Get the index of the given key
   * 
   * @param key
   *          the key
   * @return the index
   */
  @SuppressWarnings("rawtypes")
  public final int getIndex(final Comparable key) {
    return this.m_keySet.indexOf(key);
  }

  /**
   * Get the list of keys
   * 
   * @return the list of keys
   */
  @SuppressWarnings("rawtypes")
  public List getKeys() {
    return this.m_keySet;
  }

  /**
   * Get the number of series
   * 
   * @return the number of series
   */
  public final int getSeriesCount() {
    return this.m_data.size();
  }

  /**
   * Get the series key
   * 
   * @param series
   *          the series
   * @return the series key
   */
  @SuppressWarnings("rawtypes")
  public final Comparable getSeriesKey(final int series) {
    return this.m_keySet.get(series);
  }

  /**
   * get the index of the given key
   * 
   * @param seriesKey
   *          the key
   * @return its index
   */
  @SuppressWarnings("rawtypes")
  public final int indexOf(final Comparable seriesKey) {
    return this.m_keySet.indexOf(seriesKey);
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
    return _JFreeChartDataset.GROUP;
  }

  /** {@inheritDoc} */
  @Override
  public final void setGroup(final DatasetGroup group) {
    throw new UnsupportedOperationException();
  }
}
