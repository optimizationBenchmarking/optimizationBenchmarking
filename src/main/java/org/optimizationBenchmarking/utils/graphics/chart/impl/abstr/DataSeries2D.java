package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IDataSeries2D;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * The base class for all 2-dimensional data series
 */
public class DataSeries2D extends DataSeries implements IDataSeries2D {

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected DataSeries2D(final ChartElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setData(final IMatrix matrix) {
    final int n;

    if ((n = matrix.n()) != 2) {
      throw new IllegalArgumentException(//
          "2-D data series must have 2 columns but has " + n); //$NON-NLS-1$
    }
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_data = matrix;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setStart(final double x, final double y) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setEnd(final double x, final double y) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
  }
}
