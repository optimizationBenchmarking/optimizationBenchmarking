package org.optimizationBenchmarking.utils.math.text;

/**
 * This is a bridge which allows us to use a set of
 * {@link org.optimizationBenchmarking.utils.math.text.IMathRenderable}s as
 * parameters whose parameters, in turn, are rendered from another instance
 * of
 * {@link org.optimizationBenchmarking.utils.math.text.IParameterRenderer}.
 */
public final class ParameterRendererBridge extends
    _ArrayBasedParameterRenderer {

  /**
   * Create the parameter renderer bridge
   *
   * @param renderer
   *          the renderer to be used for the parameters of the parameters
   * @param parameters
   *          the immediate parameters to render
   */
  public ParameterRendererBridge(final IParameterRenderer renderer,
      final IMathRenderable... parameters) {
    super(renderer, false, parameters);
  }
}
