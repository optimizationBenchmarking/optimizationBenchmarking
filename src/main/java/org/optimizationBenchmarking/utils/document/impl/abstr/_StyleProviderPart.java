package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.IStyleProvider;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

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
  @SuppressWarnings("resource")
  _StyleProviderPart(final DocumentElement owner) {
    super(owner);

    StyleSet ss;
    HierarchicalFSM x;

    x = owner;
    finder: {
      while (x != null) {
        if (x instanceof DocumentElement) {
          if (x instanceof _StyleProviderPart) {
            ss = ((_StyleProviderPart) x).m_styles;
            break finder;
          }
          x = ((DocumentElement) x)._owner();
        }
      }
      ss = this.m_doc.m_styles;
    }

    this.m_styles = new StyleSet(ss);
  }

  /** {@inheritDoc} */
  @Override
  public final StyleSet getStyles() {
    return this.m_styles;
  }

}
