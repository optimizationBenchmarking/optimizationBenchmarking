package org.optimizationBenchmarking.experimentation.data.impl;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IPlainText;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** Static utilities for semantic components. */
public final class SemanticComponentUtils {

  /** the forbidden constructor */
  private SemanticComponentUtils() {
    ErrorUtils.doNotCall();
  }

  /**
   * <p>
   * Append the {@code name} to the given text output device.
   * </p>
   * <p>
   * {@code textOut} may actually be an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText}
   * or something similar. In this case, the implementation of this
   * function may make use of all the capabilities of this object, foremost
   * including the ability to
   * {@link org.optimizationBenchmarking.utils.document.spec.IComplexText#emphasize()
   * emphasized text}.
   * </p>
   * <p>
   * If {@code name} is a "fixed" or "given" name, {@code textCase} should
   * be ignored and {@code applyTextCase} should be {@code false}. For
   * names which identify common things, it should be applied and hence,
   * {@code applyTextCase=true} should be supplied.
   * </p>
   *
   * @param name
   *          the name to append
   * @param textOut
   *          the text output device
   * @param textCase
   *          the text case to be used
   * @param applyTextCase
   *          {@code true} if {@code textCase} should be applied,
   *          {@code false} if it can be ignored
   * @return the next text case
   */
  public static final ETextCase appendName(final String name,
      final ITextOutput textOut, final ETextCase textCase,
      final boolean applyTextCase) {
    final ETextCase use;

    use = ETextCase.ensure(textCase);

    if (textOut instanceof IComplexText) {
      try (final IPlainText emph = ((IComplexText) textOut).emphasize()) {
        if (applyTextCase) {
          return use.appendWord(name, emph);
        }
        emph.append(name);
      }
    } else {
      if (applyTextCase) {
        return use.appendWord(name, textOut);
      }
      textOut.append(name);
    }
    return use.nextCase();
  }
}
