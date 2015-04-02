package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * The class value groups provides a set of groups containing all values
 * from a given domain.
 */
public class ValueGroups extends ArraySetView<ValueGroup> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the hash code */
  private final int m_hashCode;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  ValueGroups(final ValueGroup[] data) {
    super(data);
    this.m_hashCode = super.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hashCode;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final ValueGroups pvg;
    if (o == this) {
      return true;
    }
    if (o instanceof ValueGroups) {
      pvg = ((ValueGroups) o);
      if (pvg.m_hashCode == this.m_hashCode) {
        return Arrays.equals(this.m_data, pvg.m_data);
      }
    }
    return false;
  }
}
