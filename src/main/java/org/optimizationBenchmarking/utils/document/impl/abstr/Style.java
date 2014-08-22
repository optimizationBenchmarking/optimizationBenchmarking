package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a style */
public class Style implements IStyle {

  /** create */
  protected Style() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void describe(final ETextCase textCase,
      final ITextOutput out) {
    if (out instanceof IComplexText) {
      try (final IText t = ((IComplexText) out).style(this)) {
        this.writeDescription(textCase, t);
      }
    } else {
      this.writeDescription(textCase, out);
    }
  }

  /**
   * write the description
   * 
   * @param textCase
   *          the text case
   * @param out
   *          the text output
   */
  protected void writeDescription(final ETextCase textCase,
      final ITextOutput out) {
    out.append(this.getClass().getSimpleName());
  }

}
