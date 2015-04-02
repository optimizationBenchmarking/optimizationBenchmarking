package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * A group of values. This class is most likely used to group property
 * values.
 */
public class ValueGroup extends ArraySetView<Object> implements
    Comparable<ValueGroup> {

  /** the default serial version uid */
  private static final long serialVersionUID = 1L;

  /** the hash code */
  private final int m_hashCode;

  /**
   * create the property value group
   * 
   * @param data
   *          the data
   */
  ValueGroup(final Object[] data) {
    super(data);
    if (data.length < 1) {
      throw new IllegalArgumentException(//
          "There must be at least one value in a property value group."); //$NON-NLS-1$
    }
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
    final ValueGroup pvg;
    if (o == this) {
      return true;
    }
    if (o instanceof ValueGroup) {
      pvg = ((ValueGroup) o);
      if (pvg.m_hashCode == this.m_hashCode) {
        return Arrays.equals(this.m_data, pvg.m_data);
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final ValueGroup o) {
    final Object[] a, b;
    final int length;
    int i, res;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    a = this.m_data;
    b = o.m_data;
    length = Math.min(a.length, b.length);
    for (i = 0; i < a.length; i++) {
      res = EComparison.compareObjects(a[i], b[i]);
      if (res != 0) {
        return res;
      }
    }
    if (length < a.length) {
      return 1;
    }
    if (length < b.length) {
      return (-1);
    }
    return 0;
  }
}
