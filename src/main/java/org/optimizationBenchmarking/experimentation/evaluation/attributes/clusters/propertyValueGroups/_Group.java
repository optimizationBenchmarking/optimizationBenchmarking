package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

/** The internal class to represent groups */
final class _Group implements Cloneable {
  /** the lower bound */
  Object m_lower;
  /** the upper bound */
  Object m_upper;
  /** is the upper bound exclusive */
  boolean m_isUpperExclusive;
  /** the group size */
  int m_size;

  /** {@inheritDoc} */
  @Override
  public final _Group clone() {
    try {
      return ((_Group) (super.clone()));
    } catch (final Throwable t) {
      throw new UnsupportedOperationException(t);
    }
  }
}
