package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This interface is common to elements which can be rendered in a
 * mathematical context.
 */
public interface IMathRenderable {

  /**
   * Render this mathematical function to the given text output device. A
   * parameter {@code renderer} is provided which is used for those
   * parameters of this renderable for which it does not know a better
   * method to render them.
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer to be used for all parameters, which
   *          otherwise cannot be rendered
   */
  public abstract void mathRender(final ITextOutput out,
      final IParameterRenderer renderer);

  /**
   * Render this mathematical function to the given math output device. A
   * parameter {@code renderer} is provided which is used for those
   * parameters of this renderable for which it does not know a better
   * method to render them.
   *
   * @param out
   *          the math output device
   * @param renderer
   *          the parameter renderer to be used for all parameters, which
   *          otherwise cannot be rendered
   */
  public abstract void mathRender(final IMath out,
      final IParameterRenderer renderer);
}
