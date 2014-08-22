package org.optimizationBenchmarking.utils.comparison;

/**
 * A comparator encapsulating comparable comparison routines
 */
@SuppressWarnings("rawtypes")
public final class ComparableComparator extends
    PreciseComparator<Comparable> {

  /** the globally shared instance of the comparable comparator */
  public static final PreciseComparator<Comparable> INSTANCE = new ComparableComparator();

  /** create */
  private ComparableComparator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final EComparison doPreciseCompare(final Comparable a,
      final Comparable b) {
    final int r;

    r = a.compareTo(b);
    return ((r < 0) ? EComparison.LESS : ((r > 0) ? EComparison.GREATER
        : EComparison.EQUAL));
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return ComparableComparator.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return ComparableComparator.INSTANCE;
  }
}
