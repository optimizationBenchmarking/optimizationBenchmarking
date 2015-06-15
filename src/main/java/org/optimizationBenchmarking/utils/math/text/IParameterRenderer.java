package org.optimizationBenchmarking.utils.math.text;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An interface providing support to render a parameter.
 */
public interface IParameterRenderer {

  /**
   * Render the {@code index}th parameter to the given text output device
   *
   * @param index
   *          the {@code 0}-based index of parameter
   * @param out
   *          the text output device
   */
  public abstract void renderParameter(final int index,
      final ITextOutput out);

  /**
   * Render the {@code index}th parameter to the given math output device
   *
   * @param index
   *          the {@code 0}-based index of parameter
   * @param out
   *          the math output device
   */
  public abstract void renderParameter(final int index, final IMath out);

}
