package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * A container for a sequence of reference items which all have the same
 * {@link #getType() type text}.
 */
public class ReferenceRun extends ArrayListView<Label> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the type name */
  private final String m_type;

  /**
   * Create the reference item
   * 
   * @param type
   *          the type
   * @param data
   *          the labels
   */
  public ReferenceRun(final String type, final Label[] data) {
    super(data);
    this.m_type = type;
  }

  /**
   * Get the reference type
   * 
   * @return the reference type
   */
  public final String getType() {
    return this.m_type;
  }

}
