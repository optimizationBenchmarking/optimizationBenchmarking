package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * The character transformation class that translates unicode characters to
 * their <a href="http://en.wikipedia.org/wiki/LaTeX">LaTeX</a>
 * representation.
 * </p>
 * <p>
 * Warning: There is a big problem with BibTeX and characters with dot
 * accents (like "c" with a "." above). The correct way to get that would
 * be <code>{\.{c}}</code>. However, some BibTex styles&nbsp;[<a href=
 * "http://compgroups.net/comp.text.tex/problem-with-i-accent-in-bibtex/1921379"
 * >1</a>] eat away all dots, leaving us with <code>{\{c}}</code>, which
 * explodes. We therefore <em>require</em> that
 * </p>
 * <ol>
 * <li>package {@code textcomp} is loaded <em>and</em></li>
 * <li>the following definition is added to the LaTeX document header:
 * <code>\gdef\lowerdotaccent#1{\.{#1}}%</code></li>
 * </ol>
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
