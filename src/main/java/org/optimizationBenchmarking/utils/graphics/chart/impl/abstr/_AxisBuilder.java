package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.CompoundAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all axes
 */
final class _AxisBuilder extends _TitledElementBuilder implements IAxis {

  /** the minimum has been set */
  static final int FLAG_HAS_MIN = (_TitledElementBuilder.FLAG_HAS_TITLE << 1);
  /** the maximum has been set */
  static final int FLAG_HAS_MAX = (_AxisBuilder.FLAG_HAS_MIN << 1);

  /** the minimum aggregate */
  private ScalarAggregate m_minAg;

  /** the maximum aggregate */
  private ScalarAggregate m_maxAg;

  /** the minimum value */
  private double m_min;

  /** the maximum value */
  private double m_max;

  /** the aggregate */
  private IAggregate m_agg;

  /** the column this axis is responsible for */
  private final int m_col;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   * @param col
   *          the column this axis is responsible for
   */
  _AxisBuilder(final _ChartElementBuilder owner, final int col) {
    super(owner);
    this.m_col = col;
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_MIN: {
        append.append("minimumSet");break;} //$NON-NLS-1$
      case FLAG_HAS_MAX: {
        append.append("maximumSet");break;} //$NON-NLS-1$
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMinimumAggregate(final ScalarAggregate min) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    if (min == null) {
      throw new IllegalArgumentException(
          "Minimum aggregate cannot be null.");//$NON-NLS-1$
    }
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _AxisBuilder.FLAG_HAS_MIN, _AxisBuilder.FLAG_HAS_MIN,
        FSM.FLAG_NOTHING);
    this.m_minAg = min;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMaximumAggregate(final ScalarAggregate max) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    if (max == null) {
      throw new IllegalArgumentException(
          "Maximum aggregate cannot be null.");//$NON-NLS-1$
    }
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _AxisBuilder.FLAG_HAS_MAX, _AxisBuilder.FLAG_HAS_MAX,
        FSM.FLAG_NOTHING);
    this.m_maxAg = max;
  }

  /**
   * check a minimum value
   * 
   * @param min
   *          the minimum value
   */
  static final void _assertMin(final double min) {
    if ((min != min) || (min >= Double.POSITIVE_INFINITY)) {
      throw new IllegalArgumentException(min
          + " is not a valid minimum value.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMinimum(final double min) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    _AxisBuilder._assertMin(min);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _AxisBuilder.FLAG_HAS_MIN, _AxisBuilder.FLAG_HAS_MIN,
        FSM.FLAG_NOTHING);
    this.m_min = min;
  }

  /**
   * check a maximum value
   * 
   * @param max
   *          the maximum value
   */
  static final void _assertMax(final double max) {
    if ((max != max) || (max <= Double.NEGATIVE_INFINITY)) {
      throw new IllegalArgumentException(max
          + " is not a valid maximum value.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setMaximum(final double max) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    _AxisBuilder._assertMax(max);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _AxisBuilder.FLAG_HAS_MAX, _AxisBuilder.FLAG_HAS_MAX,
        FSM.FLAG_NOTHING);
    this.m_max = max;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.fsmFlagsAssertTrue(_AxisBuilder.FLAG_HAS_MAX
        | _AxisBuilder.FLAG_HAS_MIN);

    if (this.m_maxAg != null) {
      if (this.m_minAg != null) {
        this.m_agg = CompoundAggregate.combine(this.m_minAg, this.m_maxAg);
      } else {
        this.m_agg = this.m_maxAg;
      }
    } else {
      this.m_agg = this.m_minAg;
    }

    super.onClose();
  }

  /**
   * Register some data
   * 
   * @param data
   *          the data set
   */
  final void _registerData(final IMatrix data) {
    if (this.m_agg != null) {
      data.aggregateColumn(this.m_col, this.m_agg);
    }
  }

  /**
   * get the axis
   * 
   * @return the axis
   */
  final Axis _getAxis() {
    return new Axis(
        this.m_title,
        ((this.m_minAg != null) ? this.m_minAg.doubleValue() : this.m_min),
        ((this.m_maxAg != null) ? this.m_maxAg.doubleValue() : this.m_max));
  }
}
