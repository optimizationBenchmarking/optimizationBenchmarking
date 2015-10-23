package org.optimizationBenchmarking.utils.math;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A wrapper which wraps around a
 * {@link org.optimizationBenchmarking.utils.math.BasicNumber} and forwards
 * all method calls to it.
 */
public final class BasicNumberWrapper extends BasicNumber {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the wrapped basic number */
  private final BasicNumber m_wrapped;

  /**
   * create
   *
   * @param wrapped
   *          the wrapped basic number
   */
  public BasicNumberWrapper(final BasicNumber wrapped) {
    super();
    if (wrapped == null) {
      throw new IllegalArgumentException("Wrapped number cannot be null."); //$NON-NLS-1$
    }
    this.m_wrapped = wrapped;
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return this.m_wrapped.getState();
  }

  /** {@inheritDoc} */
  @Override
  public final int sign() {
    return this.m_wrapped.sign();
  }

  /** {@inheritDoc} */
  @Override
  public final int intValue() {
    return this.m_wrapped.intValue();
  }

  /** {@inheritDoc} */
  @Override
  public final short shortValue() {
    return this.m_wrapped.shortValue();
  }

  /** {@inheritDoc} */
  @Override
  public final byte byteValue() {
    return this.m_wrapped.byteValue();
  }

  /** {@inheritDoc} */
  @Override
  public final float floatValue() {
    return this.m_wrapped.floatValue();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_wrapped.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || (this.m_wrapped.equals(o)) || super.equals(o));
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    this.m_wrapped.toText(textOut);
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final Number number) {
    return this.m_wrapped.compareTo(number);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return this.m_wrapped.toString();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isReal() {
    return this.m_wrapped.isReal();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isInteger() {
    return this.m_wrapped.isInteger();
  }

  /** {@inheritDoc} */
  @Override
  public final Number toNumber() {
    return this.m_wrapped.toNumber();
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return this.m_wrapped.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    return this.m_wrapped.doubleValue();
  }
}
