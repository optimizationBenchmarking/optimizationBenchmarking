package org.optimizationBenchmarking.utils.reflection;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/** the type set */
final class _PrimitiveTypeSet extends ArraySetView<EPrimitiveType> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the type set
   *
   * @param data
   *          the data
   */
  _PrimitiveTypeSet(final EPrimitiveType[] data) {
    super(data);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return EPrimitiveType.TYPES;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return EPrimitiveType.TYPES;
  }
}
