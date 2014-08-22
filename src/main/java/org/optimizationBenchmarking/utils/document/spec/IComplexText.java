package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/**
 * A document element for writing text with formatting capabilities.
 */
public interface IComplexText extends IText, IStyleContext {

  /**
   * Write some text in a given style. A common use case is to emphasize
   * text.
   * 
   * @param style
   *          the style to be used, must have been created via
   *          {@link org.optimizationBenchmarking.utils.document.spec.IStyleContext#createTextStyle()}
   *          ,
   *          {@link org.optimizationBenchmarking.utils.document.spec.IStyleContext#createGraphicalStyle()}
   *          , or obtained via {@link #getStyle()}.
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IText}
   *         whose text will be style.
   */
  public abstract IComplexText style(final IStyle style);

  /**
   * Obtain the style applied to this text.
   * 
   * @return the style applied to this text.
   */
  public abstract IStyle getStyle();

  /**
   * Write some subscript text.
   * 
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IText}
   *         whose text will be subscript.
   */
  public abstract IText subscript();

  /**
   * Write some subscript text.
   * 
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IText}
   *         whose text will be subscript.
   */
  public abstract IText superscript();

  /**
   * Obtain an inline math context
   * 
   * @return the inline math context
   */
  public abstract IMath inlineMath();

  /**
   * Cite a set of bibliographic elements.
   * 
   * @param citationMode
   *          the citation mode
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @param references
   *          the bibliographic records to cite
   */
  public abstract void cite(final ECitationMode citationMode,
      final ETextCase textCase, final ESequenceMode sequenceMode,
      final BibRecord... references);

  /**
   * reference a set of labels
   * 
   * @param textCase
   *          the text case
   * @param sequenceMode
   *          the sequence mode
   * @param labels
   *          the labels
   */
  public abstract void referece(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels);

  /**
   * Invoke a text macro
   * 
   * @param macro
   *          the macro to invoke
   * @return the macro invocation
   */
  public abstract ITextMacroInvocation invoke(final TextMacro macro);

}
