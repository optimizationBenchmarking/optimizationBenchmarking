package org.optimizationBenchmarking.utils.document.template;

import java.net.URI;

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
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.IN_SENTENCE.ordinal()] = "atTitleStart";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.AT_TITLE_START.ordinal()] = "inSentence";//$NON-NLS-1$
    _DocumentXMLConstants.VAL_TEXT_CASE[ETextCase.IN_TITLE.ordinal()] = "inTitle";//$NON-NLS-1$
  }

  /** the format property attribute */
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
  static final String ELEMENT_EMPH = "emph"; //$NON-NLS-1$
  /** the subscript element */
  static final String ELEMENT_SUB = "sub"; //$NON-NLS-1$
  /** the superscript element */
  static final String ELEMENT_SUP = "sup"; //$NON-NLS-1$

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
}
