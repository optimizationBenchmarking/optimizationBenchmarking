package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.IStyleProvider;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;

/**
 * A document part which can provide a style.
 */
class _StyleProviderPart extends DocumentPart implements IStyleProvider {

  /** the styles */
  final StyleSet m_styles;

  /**
   * Create a document part.
   * 
   * @param owner
   *          the owning FSM
   */
  _StyleProviderPart(final DocumentElement owner) {
    super(owner);
    this.m_styles = _StyleProviderPart._createStyleSet(owner);
  }

  /**
   * Create the style set
   * 
   * @param owner
   *          the owner
   * @return the style set
   */
  @SuppressWarnings("resource")
  static final StyleSet _createStyleSet(final DocumentElement owner) {
    StyleSet ss;
    DocumentElement x;

    finder: {
      for (x = owner; x != null; x = x._owner()) {
        if (x instanceof _StyleProviderPart) {
          ss = ((_StyleProviderPart) x).m_styles;
          break finder;
        }
        if (x instanceof Document) {
          ss = ((Document) x).m_styles;
          break finder;
        }
        if (x instanceof IStyleProvider) {
          ss = ((IStyleProvider) x).getStyles();
          break finder;
        }
      }
      throw new IllegalArgumentException("Could not find style set."); //$NON-NLS-1$
    }

    return new StyleSet(ss);
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_styles;
  }

}
