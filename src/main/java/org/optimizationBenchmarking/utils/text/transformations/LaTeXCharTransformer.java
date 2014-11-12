package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The character transformation class that translates unicode characters to
 * their <a href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a>
 * representation.
 */
public final class LaTeXCharTransformer extends LookupCharTransformer {

  /** instantiate */
  LaTeXCharTransformer() {
    super("LaTeXCharTransformationMap.transform"); //$NON-NLS-1$
  }

  /**
   * Get the instance of the LaTeX char transformer
   * 
   * @return the instance
   */
  public static final LaTeXCharTransformer getInstance() {
    return __LaTeXCharTransformerLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final AbstractTextOutput transform(final ITextOutput out,
      final Normalizer.Form form) {

    if (form == null) {
      return new _LaTeXLookupTransformedTextOutput(out, this.m_data,
          this.m_state);
    }
    return new _LaTeXNormalizingLookupTransformedTextOutput(out,
        this.m_data, this.m_state, form);
  }

  /** the internal loader class */
  private static final class __LaTeXCharTransformerLoader {
    /** the LaTeX character transformer */
    static final LaTeXCharTransformer INSTANCE = new LaTeXCharTransformer();

  }
}
