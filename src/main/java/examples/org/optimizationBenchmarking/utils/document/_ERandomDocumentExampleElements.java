package examples.org.optimizationBenchmarking.utils.document;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;

/** the elements which may occur in a random document example */
enum _ERandomDocumentExampleElements {

  /** the normal text */
  NORMAL_TEXT,
  /** in braces */
  IN_BRACES,
  /** in quotes */
  IN_QUOTES,
  /** with font */
  WITH_FONT,
  /** with color */
  WITH_COLOR,
  /** section */
  SECTION(ELabelType.SECTION),
  /** enum */
  ENUM,
  /** itemize */
  ITEMIZE,
  /** figure */
  FIGURE(ELabelType.FIGURE),
  /** figure series */
  FIGURE_SERIES(ELabelType.FIGURE),
  /** table */
  TABLE(ELabelType.TABLE),
  /** equation */
  EQUATION(ELabelType.EQUATION),
  /** inline math */
  INLINE_MATH,
  /** citation */
  CITATION,
  /** code */
  CODE(ELabelType.CODE),
  /** inline code */
  INLINE_CODE,
  /** emph */
  EMPH,
  /** sub-script */
  SUBSCRIPT,
  /** super-script */
  SUPERSCRIPT,
  /** ref */
  REFERENCE;

  /** the number of elements */
  static final _ERandomDocumentExampleElements[] ELEMENTS = _ERandomDocumentExampleElements
      .values();

  /** the number of elements */
  static final int ELEMENT_COUNT = _ERandomDocumentExampleElements.ELEMENTS.length;

  /** the label type */
  final ELabelType m_label;

  /**
   * create with label type
   *
   * @param label
   *          the label type
   */
  _ERandomDocumentExampleElements(final ELabelType label) {
    this.m_label = label;
  }

  /** create */
  _ERandomDocumentExampleElements() {
    this(null);
  }
}
