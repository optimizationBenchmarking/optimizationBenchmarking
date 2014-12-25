package org.optimizationBenchmarking.experimentation.evaluation.data;

import java.io.Serializable;

/** the generalized parameter value */
final class _PropertyValueGeneralized implements Serializable,
    Comparable<Object> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the generalized name */
  static final String NAME = "#generalized#"; //$NON-NLS-1$

  /** the globally shared instance */
  static final Object INSTANCE = new _PropertyValueGeneralized();

  /** the id of generalized values */
  static final int ID = (-2);

  /** create */
  _PropertyValueGeneralized() {
    super();
  }

  /**
   * read resolve
   * 
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return _PropertyValueGeneralized.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return _PropertyValueGeneralized.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || (o instanceof _PropertyValueGeneralized));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return _PropertyValueGeneralized.ID;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return _PropertyValueGeneralized.NAME;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final Object o) {
    if (o instanceof _PropertyValueGeneralized) {
      return 0;
    }
    return (-1);
  }
}
