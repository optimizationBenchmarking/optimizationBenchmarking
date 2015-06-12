package org.optimizationBenchmarking.utils.math.functions;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The default parameter renderer. */
public final class DefaultParameterRenderer implements IParameterRenderer {

  /** the internal parameters */
  private static final String[] PARAMETERS = {//
  "#1#", //$NON-NLS-1$
      "#2#", //$NON-NLS-1$
      "#3#", //$NON-NLS-1$
      "#4#", //$NON-NLS-1$
      "#5#", //$NON-NLS-1$
      "#6#", //$NON-NLS-1$
      "#7#", //$NON-NLS-1$
      "#8#", //$NON-NLS-1$
      "#9#", //$NON-NLS-1$
  };

  /** the globally shared instance */
  public static final DefaultParameterRenderer INSTANCE = new DefaultParameterRenderer();

  /** create */
  private DefaultParameterRenderer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index, final ITextOutput out) {
    out.append(DefaultParameterRenderer.getParameterString(index));
  }

  /** {@inheritDoc} */
  @Override
  public final void renderParameter(final int index, final IMath out) {
    try (final IText param = out.name()) {
      this.renderParameter(index, param);
    }
  }

  /**
   * Get a string corresponding to the parameter at the {@code 0}-based
   * index {@code index}
   *
   * @param index
   *          the {@code 0}-based parameter index
   * @return the string representing this parameter
   * @see #getParameterIndex(String)
   */
  public static final String getParameterString(final int index) {
    if (index < 0) {
      throw new IllegalArgumentException(//
          "Parameter index cannot be less than 0, but you provided " //$NON-NLS-1$
              + index);
    }
    if (index >= DefaultParameterRenderer.PARAMETERS.length) {
      return (('#' + Integer.toString(index)) + '#');
    }
    return DefaultParameterRenderer.PARAMETERS[index];
  }

  /**
   * Parse a parameter string to a {@code 0}-based parameter index
   *
   * @param string
   *          the string
   * @return the index
   * @see #getParameterString(int)
   */
  public static final int getParameterIndex(final String string) {
    final int start, end, result;

    if (string == null) {
      throw new IllegalArgumentException(
          "Parameter string cannot be null.");//$NON-NLS-1$
    }
    start = string.indexOf('#');
    end = string.lastIndexOf('#');
    if (start >= (end - 1)) {
      throw new IllegalArgumentException(//
          "Parameter index must be enclosed by '#', but '" + //$NON-NLS-1$
              string + "' does not follow this pattern.");//$NON-NLS-1$
    }

    try {
      result = Integer.parseInt(string.substring((start + 1), end));
    } catch (final Throwable error) {
      throw new IllegalArgumentException((((//
          "Illegal parameter string '" + string) + '\'') + '.'),//$NON-NLS-1$
          error);
    }

    if (result < 0) {
      throw new IllegalArgumentException(((//
          "0-based parameter index cannot be negative, but '"//$NON-NLS-1$
          + string) + "' parses to ") + result);//$NON-NLS-1$
    }
    return result;
  }
}
