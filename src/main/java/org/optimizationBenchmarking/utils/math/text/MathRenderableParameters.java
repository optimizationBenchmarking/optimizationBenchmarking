package org.optimizationBenchmarking.utils.math.text;

/**
 * This parameter renderer renders a set of
 * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable}s as
 * parameters.
 */
public final class MathRenderableParameters extends
    _ArrayBasedParameterRenderer {

  /**
   * Create the parameter renderer
   *
   * @param parameters
   *          the parameters to render
   */
  public MathRenderableParameters(final IMathRenderable... parameters) {
    super(null, true, parameters);
  }
}
