package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.document.spec.ITextMacroInvocation;
import org.optimizationBenchmarking.utils.document.spec.TextMacro;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/** A text class */
public class ComplexText extends Text implements IComplexText {

  /** the style set */
  final StyleSet m_styles;

  /** the style */
  final IStyle m_style;

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   * @param out
   *          the output destination
   * @param style
   *          the style
   */
  protected ComplexText(final HierarchicalFSM owner, final Appendable out,
      final IStyle style) {
    super(owner, out);

    DocumentPart._checkStyle(style);
    this.m_styles = new StyleSet(Document._getStyleSet(owner));
    this.m_style = style;
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createTextStyle() {
    return this.m_styles.createTextStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createGraphicalStyle() {
    return this.m_styles.createGraphicalStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle emphasized() {
    return this.m_styles.emphasized();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle plain() {
    return this.m_styles.plain();
  }

  /**
   * Create a complex text
   * 
   * @param style
   *          the style to use
   * @return the complex text
   */
  protected IComplexText createComplexText(final IStyle style) {
    return new ComplexText(this, null, style);
  }

  /** {@inheritDoc} */
  @Override
  public final IComplexText style(final IStyle style) {
    DocumentPart._checkStyle(style);
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.createComplexText(style);
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle getStyle() {
    return this.m_style;
  }

  @Override
  public IText subscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IText superscript() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMath inlineMath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void cite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode,
      final BibRecord... references) {
    // TODO Auto-generated method stub

  }

  @Override
  public void referece(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels) {
    // TODO Auto-generated method stub

  }

  @Override
  public ITextMacroInvocation invoke(final TextMacro macro) {
    // TODO Auto-generated method stub
    return null;
  }
}
