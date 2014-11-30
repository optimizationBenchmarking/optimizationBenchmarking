package org.optimizationBenchmarking.utils.tools.impl.R;

/** an identifier created from an {@code R} engine */
public final class RID {

  /** the id */
  final String m_id;

  /**
   * create
   * 
   * @param id
   *          the id
   */
  RID(final String id) {
    super();
    this.m_id = id;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_id;
  }
}
