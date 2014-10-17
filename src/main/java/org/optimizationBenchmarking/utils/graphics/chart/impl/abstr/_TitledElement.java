package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.text.TextUtils;

/** The base class for fully constructed titled elements */
class _TitledElement {

  /** the title */
  private final String m_title;

  /**
   * Create a titled element
   * 
   * @param title
   *          the title
   */
  protected _TitledElement(final String title) {
    super();
    this.m_title = TextUtils.normalize(title);
  }

  /**
   * Get the title of this titled element
   * 
   * @return the title of this titled element
   */
  public final String getTitle() {
    return this.m_title;
  }
}
