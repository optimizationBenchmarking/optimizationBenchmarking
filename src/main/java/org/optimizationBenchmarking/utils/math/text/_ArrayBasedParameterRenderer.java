package org.optimizationBenchmarking.utils.math.text;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The base class for everything which renders parameters provided as array
 * of instances of
 * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable}.
 */
class _ArrayBasedParameterRenderer extends AbstractParameterRenderer {

  /** the renderer for the parameters of {@link #m_parameters} */
  private final IParameterRenderer m_renderer;

  /** the immediate parameters */
  private final IMathRenderable[] m_parameters;

  /** the hash code */
  private int m_hashCode;

  /**
   * Create the array-based parameter renderer
   *
   * @param renderer
   *          the renderer to be used for the parameters of the parameters
   * @param useThisAsRenderer
   *          should {@code this} be used as renderer for children?
   * @param parameters
   *          the immediate parameters to render
   */
  _ArrayBasedParameterRenderer(final IParameterRenderer renderer,
      final boolean useThisAsRenderer, final IMathRenderable... parameters) {
    super();

    int hc;

    if (useThisAsRenderer) {
      this.m_renderer = this;
    } else {
      if (renderer == null) {
        throw new IllegalArgumentException(//
            "The parameter renderer to be used for the parameters of the parameters of a ParameterRenderBridge cannot be null."); //$NON-NLS-1$
      }
      this.m_renderer = renderer;
    }

    if (parameters == null) {
      throw new IllegalArgumentException(//
          "The array with the immediate parameters of a ParameterRenderBridge cannot be null."); //$NON-NLS-1$
    }

    this.m_parameters = parameters;

    hc = HashUtils.hashCode(parameters);
    if (this.m_renderer != this) {
      this.m_hashCode = HashUtils.combineHashes(
          HashUtils.hashCode(renderer), hc);
    } else {
      this.m_hashCode = hc;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index, final ITextOutput out) {
    if ((index >= 0) && (index < this.m_parameters.length)) {
      this.m_parameters[index].mathRender(out, this.m_renderer);
    } else {
      AbstractParameterRenderer.throwInvalidParameterIndex(index,
          this.m_parameters.length);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index, final IMath out) {
    if ((index >= 0) && (index < this.m_parameters.length)) {
      this.m_parameters[index].mathRender(out, this.m_renderer);
    } else {
      AbstractParameterRenderer.throwInvalidParameterIndex(index,
          this.m_parameters.length);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hashCode;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _ArrayBasedParameterRenderer other;
    if (o == this) {
      return true;
    }
    if (o instanceof _ArrayBasedParameterRenderer) {
      other = ((_ArrayBasedParameterRenderer) o);

      return ((this.m_hashCode == other.m_hashCode) && //
          ((this == this.m_renderer) || //
          this.m_renderer.equals(other.m_renderer))//
      && Arrays.equals(this.m_parameters, other.m_parameters));
    }

    return false;
  }
}
