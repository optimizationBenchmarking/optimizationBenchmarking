package org.optimizationBenchmarking.experimentation.data;

import java.io.Serializable;

/** the unspecified parameter value */
final class _PropertyValueUnspecified implements Serializable,
    Comparable<Object> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the unspecified name */
  static final String NAME = "#unspecified#"; //$NON-NLS-1$
  /** the globally shared instance */
  static final Object INSTANCE = new _PropertyValueUnspecified();

  /** the id of unspecified values */
  static final int ID = (_PropertyValueGeneralized.ID - 1);

  /** create */
  _PropertyValueUnspecified() {
    super();
  }

  /**
   * read resolve
   * 
   * @return {@link #INSTANCE}
   */
  private final Object readResolve() {
    return _PropertyValueUnspecified.INSTANCE;
  }

  /**
   * write replace
   * 
   * @return {@link #INSTANCE}
   */
  private final Object writeReplace() {
    return _PropertyValueUnspecified.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || (o instanceof _PropertyValueUnspecified));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return _PropertyValueUnspecified.ID;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return _PropertyValueUnspecified.NAME;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final Object o) {
    if (o instanceof _PropertyValueGeneralized) {
      return 1;
    }
    if (o instanceof _PropertyValueUnspecified) {
      return 0;
    }
    return (-1);
  }
}
