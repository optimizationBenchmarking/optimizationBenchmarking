package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IStyleContext;

/** a style set helps keeping track of styles */
public final class StyleSet implements IStyleContext {

  /** the styles */
  private final Styles m_styles;

  /** the text styles */
  private int m_textSize;

  /** the graphical styles */
  private int m_graphicalSize;

  /**
   * create a new style set
   * 
   * @param owner
   *          the owning style set
   */
  public StyleSet(final StyleSet owner) {
    super();

    if (owner != null) {
      this.m_styles = owner.m_styles;
      this.m_textSize = owner.m_textSize;
      this.m_graphicalSize = owner.m_graphicalSize;
    } else {
      throw new IllegalArgumentException(//
          "Style set requires owner."); //$NON-NLS-1$
    }
  }

  /**
   * create the style set
   * 
   * @param styles
   *          the styles
   */
  public StyleSet(final Styles styles) {
    super();
    if (styles != null) {
      this.m_styles = styles;
      this.m_textSize = styles.m_textSize;
      this.m_graphicalSize = styles.m_graphicalSize;
    } else {
      throw new IllegalArgumentException(//
          "Style set requires owner."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createTextStyle() {
    final int i;
    synchronized (this) {
      i = (this.m_textSize++);
    }
    return this.m_styles.getTextStyle(i);
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createGraphicalStyle() {
    final int i;
    synchronized (this) {
      i = (this.m_graphicalSize++);
    }
    return this.m_styles.getTextStyle(i);
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle getEmphasizedStyle() {
    return this.m_styles.getEmphasizedStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle getPlainStyle() {
    return this.m_styles.getPlainStyle();
  }

}
