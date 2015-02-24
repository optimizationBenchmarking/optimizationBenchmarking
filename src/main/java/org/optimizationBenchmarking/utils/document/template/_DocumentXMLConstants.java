package org.optimizationBenchmarking.utils.document.template;

import java.net.URI;

import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;

/**
 * the string constants for the document xml format of
 * optimizationBenchmarking
 */
final class _DocumentXMLConstants {

  /** the namespace uri */
  static final URI NAMESPACE_URI = URI.create(//
      "http://www.optimizationBenchmarking.org/formats/documentTemplate/documentTemplate.1.0.xsd"); //$NON-NLS-1$

  /** the namespace uri as string */
  static final String NAMESPACE = _DocumentXMLConstants.NAMESPACE_URI
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
  static final String[] VAL_TEXT_CASE;

  static {
    VAL_TEXT_CASE = new String[ETextCase.INSTANCES.size()];
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.AT_SENTENCE_START
        .ordinal()] = "atSentenceStart";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.IN_SENTENCE.ordinal()] = "inSentence";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.AT_TITLE_START.ordinal()] = "atTitleStart";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.IN_TITLE.ordinal()] = "inTitle";//$NON-NLS-1$
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
  static final String[] VAL_SEQUENCE_MODE;

  static {
    VAL_SEQUENCE_MODE = new String[ESequenceMode.INSTANCES.size()];
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.COMMA.ordinal()] = "comma";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.AND.ordinal()] = "and";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.OR.ordinal()] = "or";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.EITHER_OR
        .ordinal()] = "eitherOr";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.NEITHER_NOR
        .ordinal()] = "neitherNor";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.FROM_TO
        .ordinal()] = "fromTo";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_SEQUENCE_MODE[ESequenceMode.ET_AL.ordinal()] = "etAl";//$NON-NLS-1$
  }

  /** the citation mode attribute */
  static final String ATTR_CITATION_MODE = "mode"; //$NON-NLS-1$$
  /** the citation mode values */
  static final String[] VAL_CITATION_MODE;

  /** the callback element */
  static final String ELEMENT_CALL = "call"; //$NON-NLS-1$

  /** the function attribute */
  static final String ATTR_CALL_F = "f"; //$NON-NLS-1$$

  static {
    VAL_CITATION_MODE = new String[ECitationMode.INSTANCES.size()];
    _DocumentXMLConstants.VAL_CITATION_MODE[ECitationMode.ID.ordinal()] = "id";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_CITATION_MODE[ECitationMode.AUTHORS_AND_ID
        .ordinal()] = "authorsAndID";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_CITATION_MODE[ECitationMode.AUTHORS_AND_YEAR
        .ordinal()] = "authorsAndYear";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_CITATION_MODE[ECitationMode.AUTHORS
        .ordinal()] = "authors";//$NON-NLS-1$         
    _DocumentXMLConstants.VAL_CITATION_MODE[ECitationMode.TITLE.ordinal()] = "title";//$NON-NLS-1$
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
  static final String ELEMENT_REF_LABEL = _DocumentXMLConstants.ATTR_LABEL;

  /** the element for ordered lists (enumerations) */
  static final String ELEMENT_OL = "ol"; //$NON-NLS-1$
  /** the element for unordered lists (itemizations) */
  static final String ELEMENT_UL = "ul"; //$NON-NLS-1$
  /** the element for list items */
  static final String ELEMENT_LI = "li"; //$NON-NLS-1$

  /**
   * Get the text case
   * 
   * @param s
   *          the name of the text
   * @return the parsed text case
   */
  static final ETextCase _parseTextCase(final String s) {
    int i;
    for (i = _DocumentXMLConstants.VAL_TEXT_CASE.length; (--i) >= 0;) {
      if (_DocumentXMLConstants.VAL_TEXT_CASE[i].equalsIgnoreCase(s)) {
        return ETextCase.INSTANCES.get(i);
      }
    }
    return ETextCase.IN_SENTENCE;
  }

  /**
   * Get the sequence mode
   * 
   * @param s
   *          the name of the sequence mode
   * @return the parsed sequence mode
   */
  static final ESequenceMode _parseSequenceMode(final String s) {
    int i;
    for (i = _DocumentXMLConstants.VAL_SEQUENCE_MODE.length; (--i) >= 0;) {
      if (_DocumentXMLConstants.VAL_SEQUENCE_MODE[i].equalsIgnoreCase(s)) {
        return ESequenceMode.INSTANCES.get(i);
      }
    }
    return null;
  }
}
