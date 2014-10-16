package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.ITitleElement;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The base class for all chart items.
 */
public class TitleElement extends ChartElement implements ITitleElement {

  /** the title of this element */
  String m_title;

  /**
   * create the chart item
   * 
   * @param owner
   *          the owner
   */
  protected TitleElement(final ChartElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setTitle(final String title) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_title = TextUtils.normalize(title);
  }

}
