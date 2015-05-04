package org.optimizationBenchmarking.utils.hash;

/**
 * A key identifying {@code null}.
 */
final class _NullKey {

  /** the shared instance of the null key */
  static final _NullKey INSTANCE = new _NullKey();

  /** create */
  private _NullKey() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof _NullKey);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 0;
  }

  /**
   * read resolve
   *
   * @return the key
   */
  private final Object readResolve() {
    return _NullKey.INSTANCE;
  }

  /**
   * write replace
   *
   * @return the key
   */
  private final Object writeReplace() {
    return _NullKey.INSTANCE;
  }
}
