package org.optimizationBenchmarking.utils.document.template;

import java.net.URI;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/**
 * the string constants for the document xml format of
 * optimizationBenchmarking
 */
public enum DocumentXML implements IFileType {

  /** the document XML file type */
  DOCUMENT_XML;

  /** the namespace uri */
  public static final URI NAMESPACE_URI = URI.create(//
      "http://www.optimizationBenchmarking.org/formats/documentTemplate/documentTemplate.1.0.xsd"); //$NON-NLS-1$

  /** the namespace uri as string */
  public static final String NAMESPACE = DocumentXML.NAMESPACE_URI
      .toString();

  /** the template element */
  static final String ELEMENT_TEMPLATE = "template"; //$NON-NLS-1$

  /** the value element */
  static final String ELEMENT_VALUE = "value"; //$NON-NLS-1$
  /** the value-of attribute */
  static final String ATTR_VALUE_OF = "of"; //$NON-NLS-1$

  /** the nbsp element */
  static final String ELEMENT_NBSP = "nbsp"; //$NON-NLS-1$

  /** the br element */
  static final String ELEMENT_BR = "br"; //$NON-NLS-1$

  /** the byte element */
  static final String ELEMENT_BYTE = "byte"; //$NON-NLS-1$  
  /** the short element */
  static final String ELEMENT_SHORT = "short"; //$NON-NLS-1$  
  /** the int element */
  static final String ELEMENT_INT = "int"; //$NON-NLS-1$  
  /** the long element */
  static final String ELEMENT_LONG = "long"; //$NON-NLS-1$
  /** the float element */
  static final String ELEMENT_FLOAT = "float"; //$NON-NLS-1$
  /** the double element */
  static final String ELEMENT_DOUBLE = "double"; //$NON-NLS-1$

  /** the textCase attribute */
  static final String ATTR_TEXT_CASE = "textCase"; //$NON-NLS-1$

  /** the text case values */
  private static final String[] VAL_TEXT_CASE;

  static {
    VAL_TEXT_CASE = new String[ETextCase.INSTANCES.size()];
    DocumentXML.VAL_TEXT_CASE[ETextCase.AT_SENTENCE_START.ordinal()] = "atSentenceStart";//$NON-NLS-1$
    DocumentXML.VAL_TEXT_CASE[ETextCase.IN_SENTENCE.ordinal()] = "inSentence";//$NON-NLS-1$
    DocumentXML.VAL_TEXT_CASE[ETextCase.AT_TITLE_START.ordinal()] = "atTitleStart";//$NON-NLS-1$
    DocumentXML.VAL_TEXT_CASE[ETextCase.IN_TITLE.ordinal()] = "inTitle";//$NON-NLS-1$
  }

  /**
   * the format property attribute: If this attribute is specified, the
   * template will take a format from the provided properties map
   */
  static final String ATTR_FORMAT_PROPERTY = "formatProperty"; //$NON-NLS-1$
  /** the format instance attribute */
  static final String ATTR_FORMAT_INSTANCE = "formatInstance"; //$NON-NLS-1$
  /** the format pattern attribute */
  static final String ATTR_FORMAT_PATTERN = "formatPattern"; //$NON-NLS-1$$

  /** the quote element */
  static final String ELEMENT_QUOTE = "quote"; //$NON-NLS-1$
  /** the brace element */
  static final String ELEMENT_BRACE = "brace"; //$NON-NLS-1$

  /** the code element */
  static final String ELEMENT_CODE = "code"; //$NON-NLS-1$
  /** the emph element */
  static final String ELEMENT_EMPH = "em"; //$NON-NLS-1$
  /** the subscript element */
  static final String ELEMENT_SUB = "sub"; //$NON-NLS-1$
  /** the superscript element */
  static final String ELEMENT_SUP = "sup"; //$NON-NLS-1$

  /** the citation element */
  static final String ELEMENT_CITE = "cite"; //$NON-NLS-1$

  /** the sequence mode attribute */
  static final String ATTR_SEQUENCE_MODE = "sequenceMode"; //$NON-NLS-1$$
  /** the sequence mode values */
  private static final String[] VAL_SEQUENCE_MODE;

  static {
    VAL_SEQUENCE_MODE = new String[ESequenceMode.INSTANCES.size()];
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.COMMA.ordinal()] = "comma";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.AND.ordinal()] = "and";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.OR.ordinal()] = "or";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.EITHER_OR.ordinal()] = "eitherOr";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.NEITHER_NOR.ordinal()] = "neitherNor";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.FROM_TO.ordinal()] = "fromTo";//$NON-NLS-1$
    DocumentXML.VAL_SEQUENCE_MODE[ESequenceMode.ET_AL.ordinal()] = "etAl";//$NON-NLS-1$
  }

  /** the citation mode attribute */
  static final String ATTR_CITATION_MODE = "mode"; //$NON-NLS-1$$
  /** the citation mode values */
  private static final String[] VAL_CITATION_MODE;

  /** the callback element */
  static final String ELEMENT_CALL = "call"; //$NON-NLS-1$

  /** the function attribute */
  static final String ATTR_CALL_F = "f"; //$NON-NLS-1$$

  static {
    VAL_CITATION_MODE = new String[ECitationMode.INSTANCES.size()];
    DocumentXML.VAL_CITATION_MODE[ECitationMode.ID.ordinal()] = "id";//$NON-NLS-1$
    DocumentXML.VAL_CITATION_MODE[ECitationMode.AUTHORS_AND_ID.ordinal()] = "authorsAndID";//$NON-NLS-1$
    DocumentXML.VAL_CITATION_MODE[ECitationMode.AUTHORS_AND_YEAR.ordinal()] = "authorsAndYear";//$NON-NLS-1$
    DocumentXML.VAL_CITATION_MODE[ECitationMode.AUTHORS.ordinal()] = "authors";//$NON-NLS-1$         
    DocumentXML.VAL_CITATION_MODE[ECitationMode.TITLE.ordinal()] = "title";//$NON-NLS-1$
  }

  /** the section element */
  static final String ELEMENT_SECTION = "section"; //$NON-NLS-1$
  /** the section title element */
  static final String ELEMENT_SECTION_TITLE = "title"; //$NON-NLS-1$$
  /** the section body element */
  static final String ELEMENT_SECTION_BODY = "body"; //$NON-NLS-1$$
  /** the section label input attribute */
  static final String ATTR_LABEL = "label"; //$NON-NLS-1$$

  /** the reference element */
  static final String ELEMENT_REF = "ref"; //$NON-NLS-1$
  /** the label */
  static final String ELEMENT_REF_LABEL = DocumentXML.ATTR_LABEL;

  /** the element for ordered lists (enumerations) */
  static final String ELEMENT_OL = "ol"; //$NON-NLS-1$
  /** the element for unordered lists (itemizations) */
  static final String ELEMENT_UL = "ul"; //$NON-NLS-1$
  /** the element for list items */
  static final String ELEMENT_LI = "li"; //$NON-NLS-1$

  /** the element for inlineMath */
  static final String ELEMENT_INLINE_MATH = "inlineMath"; //$NON-NLS-1$
  /** the element for math equation */
  static final String ELEMENT_EQUATION = "equation"; //$NON-NLS-1$

  /** the element for math text */
  static final String ELEMENT_MATH_TEXT = "text"; //$NON-NLS-1$
  /** the element for math names */
  static final String ELEMENT_MATH_NAME = "name"; //$NON-NLS-1$
  /** the element for math number */
  static final String ELEMENT_MATH_NUMBER = "number"; //$NON-NLS-1$
  /** the element for math abs */
  static final String ELEMENT_MATH_ABS = "abs"; //$NON-NLS-1$
  /** the element for math add */
  static final String ELEMENT_MATH_ADD = "add"; //$NON-NLS-1$
  /** the element for math cos */
  static final String ELEMENT_MATH_COS = "cos"; //$NON-NLS-1$
  /** the element for math div */
  static final String ELEMENT_MATH_DIV = "div"; //$NON-NLS-1$
  /** the element for math div-inline */
  static final String ELEMENT_MATH_DIV_INLINE = "divInline"; //$NON-NLS-1$
  /** the element for math factorial */
  static final String ELEMENT_MATH_FACTORIAL = "factorial"; //$NON-NLS-1$
  /** the element for math ld */
  static final String ELEMENT_MATH_LD = "ld"; //$NON-NLS-1$
  /** the element for math lg */
  static final String ELEMENT_MATH_LG = "lg"; //$NON-NLS-1$
  /** the element for math ln */
  static final String ELEMENT_MATH_LN = "ln"; //$NON-NLS-1$
  /** the element for math log */
  static final String ELEMENT_MATH_LOG = "log"; //$NON-NLS-1$
  /** the element for math mod */
  static final String ELEMENT_MATH_MOD = "mod"; //$NON-NLS-1$
  /** the element for math mul */
  static final String ELEMENT_MATH_MUL = "mul"; //$NON-NLS-1$
  /** the element for math negate */
  static final String ELEMENT_MATH_NEGATE = "negate"; //$NON-NLS-1$
  /** the element for math pow */
  static final String ELEMENT_MATH_POW = "pow"; //$NON-NLS-1$
  /** the element for math root */
  static final String ELEMENT_MATH_ROOT = "root"; //$NON-NLS-1$
  /** the element for math sin */
  static final String ELEMENT_MATH_SIN = "sin"; //$NON-NLS-1$
  /** the element for math sqrt */
  static final String ELEMENT_MATH_SQRT = "sqrt"; //$NON-NLS-1$
  /** the element for math sub */
  static final String ELEMENT_MATH_SUB = "subt"; //$NON-NLS-1$
  /** the element for math tan */
  static final String ELEMENT_MATH_TAN = "tan"; //$NON-NLS-1$

  /**
   * Get the text case
   * 
   * @param string
   *          the name of the text
   * @param defaultCase
   *          the default text case, to be returned if {@code string==null}
   * @return the parsed text case
   */
  static final ETextCase _parseTextCase(final String string,
      final ETextCase defaultCase) {
    int i;

    if (string == null) {
      return defaultCase;
    }

    for (i = DocumentXML.VAL_TEXT_CASE.length; (--i) >= 0;) {
      if (DocumentXML.VAL_TEXT_CASE[i].equalsIgnoreCase(string)) {
        return ETextCase.INSTANCES.get(i);
      }
    }

    throw new IllegalArgumentException(//
        "Text case '" + string + //$NON-NLS-1$
            "' is unknown. Valid values are " + //$NON-NLS-1$
            Arrays.toString(DocumentXML.VAL_TEXT_CASE) + '.');
  }

  /**
   * Get the sequence mode
   * 
   * @param string
   *          the name of the sequence mode
   * @param defaultMode
   *          the default mode, to be returned if {@code string==null}
   * @return the parsed sequence mode
   */
  static final ESequenceMode _parseSequenceMode(final String string,
      final ESequenceMode defaultMode) {
    int i;

    if (string == null) {
      return defaultMode;
    }

    for (i = DocumentXML.VAL_SEQUENCE_MODE.length; (--i) >= 0;) {
      if (DocumentXML.VAL_SEQUENCE_MODE[i].equalsIgnoreCase(string)) {
        return ESequenceMode.INSTANCES.get(i);
      }
    }

    throw new IllegalArgumentException(//
        "Sequence mode '" + string + //$NON-NLS-1$
            "' is unknown. Valid values are " + //$NON-NLS-1$
            Arrays.toString(DocumentXML.VAL_SEQUENCE_MODE) + '.');
  }

  /**
   * Get the citation mode
   * 
   * @param string
   *          the name of the citation mode
   * @param defaultMode
   *          the default mode, to be returned if {@code string==null}
   * @return the parsed sequence mode
   */
  static final ECitationMode _parseCitationMode(final String string,
      final ECitationMode defaultMode) {
    int i;

    if (string == null) {
      return defaultMode;
    }

    for (i = DocumentXML.VAL_CITATION_MODE.length; (--i) >= 0;) {
      if (DocumentXML.VAL_CITATION_MODE[i].equalsIgnoreCase(string)) {
        return ECitationMode.INSTANCES.get(i);
      }
    }

    throw new IllegalArgumentException(//
        "Citation mode '" + string + //$NON-NLS-1$
            "' is unknown. Valid values are " + //$NON-NLS-1$
            Arrays.toString(DocumentXML.VAL_CITATION_MODE) + '.');
  }

  /** {@inheritDoc} */
  @Override
  public final String getDefaultSuffix() {
    return "template"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final String getMIMEType() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return "Document (Part) Template";//$NON-NLS-1$
  }
}
