package test.junit.org.optimizationBenchmarking.utils.collections;

import org.optimizationBenchmarking.utils.predicates.IPredicate;

/** a simple selecting predicate */
final class _SelectionPredicate implements IPredicate<Object> {

  /** the selection */
  final boolean[] m_sel;

  /** the index */
  int m_idx;

  /** the number of times we returned true */
  int m_true;

  /**
   * create
   *
   * @param size
   *          the size
   */
  _SelectionPredicate(final int size) {
    super();
    this.m_sel = new boolean[size];
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Object object) {
    if (this.m_sel[this.m_idx++]) {
      this.m_true++;
      return true;
    }
    return false;
  }

}
