package org.optimizationBenchmarking.utils.graphics.chart.spec;

import java.awt.Font;

import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;

/** The interface for defining an axis */
public interface IAxis extends ITitledElement {
  /**
   * set the title of the axis
   * 
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

  /**
   * set the title font of the axis (can only be used after
   * {@link #setTitle(String)})
   * 
   * @param titleFont
   *          the title font
   */
  @Override
  public abstract void setTitleFont(final Font titleFont);

  /**
   * Set the font to be used for the ticks of the axis
   * 
   * @param tickFont
   *          the font to be used for the ticks of the axis
   */
  public abstract void setTickFont(final Font tickFont);

  /**
   * Set the aggregate used to compute the minimum axis value
   * 
   * @param min
   *          the aggregate used to compute the minimum axis value
   */
  public abstract void setMinimumAggregate(final ScalarAggregate min);

  /**
   * Set the minimum value for the axis
   * 
   * @param min
   *          the minimum value for the axis
   */
  public abstract void setMinimum(final double min);

  /**
   * Set the aggregate used to compute the maximum axis value
   * 
   * @param max
   *          the aggregate used to compute the maximum axis value
   */
  public abstract void setMaximumAggregate(final ScalarAggregate max);

  /**
   * Set the maximum value for the axis
   * 
   * @param max
   *          the maximum value for the axis
   */
  public abstract void setMaximum(final double max);
}
