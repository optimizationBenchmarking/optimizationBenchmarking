package org.optimizationBenchmarking.utils.config;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A common base class for definitions and dumps.
 *
 * @param <T>
 *          the element type
 */
abstract class _DefSet<T> extends ArrayListView<T> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Are more parameters allowed than defined here? */
  final boolean m_allowsMore;

  /**
   * Create
   *
   * @param data
   *          the data
   * @param allowsMore
   *          are more parameters allowed than defined here?
   */
  _DefSet(final T[] data, final boolean allowsMore) {
    super(data);
    this.m_allowsMore = allowsMore;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("rawtypes")
  public final boolean equals(final Object o) {
    final _DefSet other;
    if (o == this) {
      return true;
    }
    if ((o != null) & (o.getClass() == this.getClass())) {
      other = ((_DefSet) o);
      return ((other.m_allowsMore == this.m_allowsMore) && //
      (super.equals(other)));
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_allowsMore),
        super.calcHashCode());
  }

  /**
   * Are more parameters allowed than defined here?
   *
   * @return {@code true} if additional parameters may be specified,
   *         {@code false} otherwise
   */
  public final boolean allowsMore() {
    return this.m_allowsMore;
  }
}
