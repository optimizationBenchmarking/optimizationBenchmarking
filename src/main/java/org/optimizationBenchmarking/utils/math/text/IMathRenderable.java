package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This interface is common to elements which can be rendered in a
 * mathematical context.
 */
public interface IMathRenderable {

  /**
   * Render this mathematical function to the given text output device
   *
   * @param out
   *          the text output device
   * @param renderer
   *          the parameter renderer
   */
  public abstract void mathRender(final ITextOutput out,
      final IParameterRenderer renderer);

  /**
   * Render this mathematical function to the given math output device
   *
   * @param out
   *          the math output device
   * @param renderer
   *          the parameter renderer
   */
  public abstract void mathRender(final IMath out,
      final IParameterRenderer renderer);
}
