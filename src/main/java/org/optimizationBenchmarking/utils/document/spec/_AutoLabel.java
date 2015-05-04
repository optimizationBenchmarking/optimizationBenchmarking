package org.optimizationBenchmarking.utils.document.spec;

import java.io.Serializable;

/** the auto label */
final class _AutoLabel implements Serializable, ILabel {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _AutoLabel() {
    super();
  }

  /**
   * read resolve
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return ELabelType.AUTO;
  }

  /**
   * write replace
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return ELabelType.AUTO;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof _AutoLabel);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "AUTO"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return 5;
  }
}
