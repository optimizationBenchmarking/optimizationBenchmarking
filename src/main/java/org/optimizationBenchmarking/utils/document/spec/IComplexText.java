package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/**
 * A document element for writing text with formatting capabilities.
 */
public interface IComplexText extends IText {

  /**
   * Write some text in a given style. A common use case is to emphasize
   * text.
   * 
   * @param style
   *          the style to be used, must have been obtained from the owning
   *          section
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IText}
   *         whose text will be style.
   */
  public abstract IComplexText style(final IStyle style);

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
   * Obtain an in-line math context
   * 
   * @return the in-line math context
   */
  public abstract IMath inlineMath();

  /**
   * Obtain an in-line code context
   * 
   * @return the in-line code context
   */
  public abstract IText inlineCode();

  /**
   * Obtain an in-line emphasize text destination
   * 
   * @return the in-line emphasize text destination
   */
  public abstract IText emphasize();

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
  public abstract void reference(final ETextCase textCase,
      final ESequenceMode sequenceMode, final ILabel... labels);

}
