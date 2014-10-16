package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.awt.Color;
import java.awt.Stroke;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IDataSeries;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/**
 * The base class for all data series
 */
public class DataSeries extends TitleElement implements IDataSeries {

  /** the color of this element */
  Color m_color;

  /** the stroke of this element */
  Stroke m_stroke;

  /** the data matrix */
  IMatrix m_data;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected DataSeries(final ChartElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setColor(final Color color) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_color = color;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setStroke(final Stroke stroke) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_stroke = stroke;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setData(final IMatrix matrix) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_data = matrix;
  }

}
