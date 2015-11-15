package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A parameter renderer which prints constant double values as parameters.
 */
public final class DoubleConstantParameters
    extends AbstractParameterRenderer {

  /** the parameter values */
  private final double[] m_parameters;

  /**
   * Create the double constant parameter renderer
   *
   * @param parameters
   *          the constant parameter values
   */
  public DoubleConstantParameters(final double[] parameters) {
    super();
    if (parameters == null) {
      throw new IllegalArgumentException(
          "Parameter array must not be null."); //$NON-NLS-1$
    }
    this.m_parameters = parameters;
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index,
      final ITextOutput out) {
    if ((index >= 0) && (index < this.m_parameters.length)) {
      out.append(this.m_parameters[index]);
    } else {
      AbstractParameterRenderer.throwInvalidParameterIndex(index,
          this.m_parameters.length);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index, final IMath out) {
    if ((index >= 0) && (index < this.m_parameters.length)) {
      try (final IText text = out.number()) {
        text.append(this.m_parameters[index]);
      }
    } else {
      AbstractParameterRenderer.throwInvalidParameterIndex(index,
          this.m_parameters.length);
    }
  }
}
