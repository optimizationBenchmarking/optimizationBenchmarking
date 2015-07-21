package org.optimizationBenchmarking.experimentation.attributes.statistics;

import org.optimizationBenchmarking.experimentation.attributes.statistics.selection.SelectionCriterion;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.EAttributeType;
import org.optimizationBenchmarking.experimentation.data.spec.IDataPoint;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.experimentation.data.spec.IRun;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.statistics.parameters.StatisticalParameter;

/**
 * A statistic parameter is computed at a specified selection of an
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * set.
 */
public class InstanceRunsStatisticsAtSelection extends
    Attribute<IInstanceRuns, Number> {

  /** the selection criterion */
  private final SelectionCriterion m_selection;

  /** the dimension index */
  private final int m_dimensionIndex;

  /** is the dimension we aggregate an integer dimension? */
  private final boolean m_isDimensionInt;

  /**
   * the parameter to compute per
   * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
   */
  private final StatisticalParameter m_parameter;

  /**
   * Create a statistics computer for a given selection criterion
   *
   * @param selection
   *          the selection criterion
   * @param dimension
   *          the dimension over which to compute the statistics
   * @param parameter
   *          the parameter to compute per
   *          {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
   */
  public InstanceRunsStatisticsAtSelection(
      final SelectionCriterion selection, final IDimension dimension,
      final StatisticalParameter parameter) {
    super(EAttributeType.TEMPORARILY_STORED);

    if (selection == null) {
      throw new IllegalArgumentException(//
          "Selection criterion cannot be null for (InstanceRuns)StatisticsAtSelection."); //$NON-NLS-1$
    }
    if (dimension == null) {
      throw new IllegalArgumentException(//
          "Dimension cannot be null for (InstanceRuns)StatisticsAtSelection.");//$NON-NLS-1$
    }
    if (parameter == null) {
      throw new IllegalArgumentException(//
          "Statistical parameter to compute cannot be null for (InstanceRuns)StatisticsAtSelection."); //$NON-NLS-1$
    }
    this.m_selection = selection;
    this.m_dimensionIndex = dimension.getIndex();
    this.m_isDimensionInt = dimension.getDataType().isInteger();
    this.m_parameter = parameter;
  }

  /** {@inheritDoc} */
  @Override
  protected final Number compute(final IInstanceRuns data) {
    final ScalarAggregate paramAggregate;
    IDataPoint point;

    paramAggregate = this.m_parameter.createSampleAggregate();
    for (final IRun run : data.getData()) {
      point = this.m_selection.get(run);
      if (point != null) {
        if (this.m_isDimensionInt) {
          paramAggregate.append(point.getLong(this.m_dimensionIndex));
        } else {
          paramAggregate.append(point.getDouble(this.m_dimensionIndex));
        }
      }
    }
    return paramAggregate.toNumber();
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.hashCode(this.m_dimensionIndex),//
        HashUtils.hashCode(this.m_parameter)),//
        HashUtils.hashCode(this.m_selection));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final InstanceRunsStatisticsAtSelection r;
    if (o == this) {
      return true;
    }
    if (o instanceof InstanceRunsStatisticsAtSelection) {
      r = ((InstanceRunsStatisticsAtSelection) o);
      return ((r.m_dimensionIndex == this.m_dimensionIndex) && //
          EComparison.equals(r.m_parameter, this.m_parameter) && //
      EComparison.equals(r.m_selection, this.m_selection));
    }
    return false;
  }
}
