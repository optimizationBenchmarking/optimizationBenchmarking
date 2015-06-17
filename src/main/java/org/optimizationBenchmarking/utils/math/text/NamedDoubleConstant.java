package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A named double constant */
public final class NamedDoubleConstant extends NamedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the value */
  private final double m_value;

  /** the name */
  private final String m_name;

  /**
   * create the double constant
   * 
   * @param value
   *          the value
   * @param name
   *          the name of the constant
   */
  NamedDoubleConstant(final double value, final String name) {
    super();
    if (name == null) {
      throw new IllegalArgumentException(//
          "Name of double constant cannot be null."); //$NON-NLS-1$
    }
    this.m_value = value;
    this.m_name = name;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(ITextOutput out, IParameterRenderer renderer) {
    out.append(this.m_name);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(IMath out, IParameterRenderer renderer) {
    try (final IText name = out.name()) {
      name.append(this.m_name);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int getState() {
    return BasicNumber.STATE_DOUBLE;
  }

  /** {@inheritDoc} */
  @Override
  public final long longValue() {
    return ((long) (this.m_value));
  }

  /** {@inheritDoc} */
  @Override
  public final double doubleValue() {
    return this.m_value;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_value),
        HashUtils.hashCode(this.m_name));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final NamedDoubleConstant other;
    if (o == this) {
      return true;
    }
    if (o instanceof NamedDoubleConstant) {
      other = ((NamedDoubleConstant) o);
      return (((EComparison.compareDoubles(this.m_value, other.m_value) == 0)//
      && this.m_name.equals(other.m_name)));
    }
    return false;
  }
}
