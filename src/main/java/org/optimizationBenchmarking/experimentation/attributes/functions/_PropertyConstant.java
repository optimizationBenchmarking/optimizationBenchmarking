package org.optimizationBenchmarking.experimentation.attributes.functions;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A constant which can be updated based on a property, i.e., either an
 * experiment parameter or instance feature.
 */
abstract class _PropertyConstant extends _DataBasedConstant {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the property */
  final IProperty m_property;

  /**
   * create the property constant
   *
   * @param property
   *          the property
   */
  _PropertyConstant(final IProperty property) {
    super();

    if (property == null) {
      throw new IllegalArgumentException(//
          "Property cannot be null."); //$NON-NLS-1$
    }
    this.m_property = property;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_property.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_property.mathRender(out, renderer);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.m_property.getName();
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (this.m_property.hashCode() ^ 3453479);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o == this) || ((o instanceof _PropertyConstant) && //
    (this.m_property.equals(((_PropertyConstant) o).m_property))));
  }
}
