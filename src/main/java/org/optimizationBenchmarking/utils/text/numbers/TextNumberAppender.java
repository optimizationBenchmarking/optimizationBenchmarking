package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.parsers.DoubleParser;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender which transforms numbers to text.
 */
public final class TextNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** minus */
  private static final char[] MINUS = new char[] { 'm', 'i', 'n', 'u', 's' };

  /** and */
  private static final char[] AND = new char[] { ' ', 'a', 'n', 'd' };

  /** zero */
  private static final char[] C_0 = InTextNumberAppender.SMALL_NUMBERS[0]
      .toCharArray();

  /** ten */
  private static final char[] C_10 = InTextNumberAppender.SMALL_NUMBERS[10]
      .toCharArray();

  /** 100 */
  private static final char[] C_100 = new char[] { 'h', 'u', 'n', 'd',
      'r', 'e', 'd' };

  /** all known scales */
  private static final char[][] SCALES = {
      TextNumberAppender.C_0,//
      { 't', 'h', 'o', 'u', 's', 'a', 'n', 'd' },//
      { 'm', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'b', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 't', 'r', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'q', 'u', 'a', 'd', 'r', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'q', 'u', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 's', 'e', 'x', 't', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 's', 'e', 'p', 't', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'o', 'c', 't', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'n', 'o', 'n', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'u', 'n', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'd', 'u', 'o', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 't', 'r', 'e', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'd', 'e', 'c', 'i', 'l',
          'l', 'i', 'o', 'n' },//
      { 'q', 'u', 'i', 'n', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 's', 'e', 'x', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 's', 'e', 'p', 't', 'e', 'n', 'd', 'e', 'c', 'i', 'l', 'l', 'i',
          'o', 'n' },//
      { 'o', 'c', 't', 'o', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'n', 'o', 'v', 'e', 'm', 'd', 'e', 'c', 'i', 'l', 'l', 'i', 'o',
          'n' },//
      { 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n' }, //
      { 'u', 'n', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o',
          'n', }, //
      { 'd', 'u', 'o', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i',
          'o', 'n', }, //
      { 't', 'r', 'e', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i',
          'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'v', 'i', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i',
          'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 'v', 'i', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'o', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 'v', 'i', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'u', 'n', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i',
          'o', 'n', }, //
      { 'd', 'u', 'o', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 't', 'r', 'e', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 't', 'r', 'i', 'g', 'i',
          'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 't', 'r', 'i', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'o', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 't', 'r', 'i', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'u', 'n', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'q', 'u', 'a', 'd', 'r',
          'a', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 'q', 'u', 'a', 'd', 'r', 'a', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'o', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 'q', 'u', 'a', 'd', 'r', 'a', 'g', 'i',
          'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 'u', 'n', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'q', 'u', 'i', 'n', 'q',
          'u', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i',
          'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 'q', 'u', 'i', 'n', 'q', 'u', 'a',
          'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'o', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g', 'i',
          'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 'q', 'u', 'i', 'n', 'q', 'u', 'a', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o',
          'n', }, //
      { 'u', 'n', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 's', 'e', 'x', 'a', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 's', 'e', 'x', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'a', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 's', 'e', 'x', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'u', 'n', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 's', 'e', 'p', 't', 'u',
          'a', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 's', 'e', 'p', 't', 'u', 'a', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'a', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 's', 'e', 'p', 't', 'u', 'a', 'g', 'i',
          'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o',
          'n', }, //
      { 'u', 'n', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'o', 'c', 't', 'o', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 'o', 'c', 't', 'o', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'a', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 'o', 'c', 't', 'o', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l', 'i', 'o',
          'n', }, //
      { 'u', 'n', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i', 'l', 'l',
          'i', 'o', 'n', }, //
      { 'd', 'u', 'o', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 't', 'r', 'e', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 'n', 'o', 'n', 'a', 'g',
          'i', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'q', 'u', 'i', 'n', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'x', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n', }, //
      { 's', 'e', 'p', 't', 'e', 'n', 'n', 'o', 'n', 'a', 'g', 'i', 'n',
          't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'o', 'c', 't', 'a', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't', 'i',
          'l', 'l', 'i', 'o', 'n', }, //
      { 'n', 'o', 'v', 'e', 'm', 'n', 'o', 'n', 'a', 'g', 'i', 'n', 't',
          'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'c', 'e', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n', }, //
      { 'c', 'e', 'n', 't', 'u', 'n', 't', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'c', 'e', 'n', 't', 'd', 'u', 'o', 't', 'i', 'l', 'l', 'i', 'o',
          'n' },//
      { 'c', 'e', 'n', 't', 't', 'r', 'e', 't', 'i', 'l', 'l', 'i', 'o',
          'n' },//
      { 'c', 'e', 'n', 't', 'q', 'u', 'a', 't', 't', 'u', 'o', 'r', 't',
          'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'c', 'e', 'n', 't', 'q', 'u', 'i', 'n', 't', 'i', 'l', 'l', 'i',
          'o', 'n' },//
      { 'c', 'e', 'n', 't', 's', 'e', 'x', 't', 'i', 'l', 'l', 'i', 'o',
          'n' },//
      { 'c', 'e', 'n', 't', 's', 'e', 'p', 't', 'e', 'n', 't', 'i', 'l',
          'l', 'i', 'o', 'n' },//
      { 'c', 'e', 'n', 't', 'o', 'k', 't', 'o', 't', 'i', 'l', 'l', 'i',
          'o', 'n', },//
      { 'c', 'e', 'n', 't', 'n', 'o', 'v', 'e', 'm', 't', 'i', 'l', 'l',
          'i', 'o', 'n' },//
      { 'c', 'e', 'n', 't', 'd', 'e', 'z', 'i', 'l', 'l', 'i', 'o', 'n' },//
      { 'c', 'e', 'n', 't', 'u', 'n', 'd', 'e', 'z', 'i', 'l', 'l', 'i',
          'o', 'n' },//

  };

  /** the values from 1 to 19 */
  private static final char[][] TOKENS_1 = {//
  TextNumberAppender.C_0, //
      InTextNumberAppender.SMALL_NUMBERS[1].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[2].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[3].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[4].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[5].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[6].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[7].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[8].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[9].toCharArray(),//
      TextNumberAppender.C_10,//
      InTextNumberAppender.SMALL_NUMBERS[11].toCharArray(),//
      InTextNumberAppender.SMALL_NUMBERS[12].toCharArray(),//
      { 't', 'h', 'i', 'r', 't', 'e', 'e', 'n' },//
      { 'f', 'o', 'u', 'r', 't', 'e', 'e', 'n' },//
      { 'f', 'i', 'f', 't', 'e', 'e', 'n' },//
      { 's', 'i', 'x', 't', 'e', 'e', 'n' },//
      { 's', 'e', 'v', 'e', 'n', 't', 'e', 'e', 'n' },//
      { 'e', 'i', 'g', 'h', 't', 'e', 'e', 'n' },//
      { 'n', 'i', 'n', 'e', 't', 'e', 'e', 'n' } };

  /** the values from 20 to 90 */
  private static final char[][] TOKENS_10 = {//
  TextNumberAppender.C_0,//
      TextNumberAppender.C_10, { 't', 'w', 'e', 'n', 't', 'y' },//
      { 't', 'h', 'i', 'r', 't', 'y' },//
      { 'f', 'o', 'u', 'r', 't', 'y' },//
      { 'f', 'i', 'f', 't', 'y' },//
      { 's', 'i', 'x', 't', 'y' },//
      { 's', 'e', 'v', 'e', 'n', 't', 'y' },//
      { 'e', 'i', 'g', 'h', 't', 'y' },//
      { 'n', 'i', 'n', 'e', 't', 'y' } };

  /** the array to use for the minimum long value */
  private static final int[] MIN_LONG_THOUSANDS = { 808, 775, 854, 36,
      372, 223, 9 };

  /** the globally shared instance of the text number appender */
  public static final TextNumberAppender INSTANCE = new TextNumberAppender();

  /** create the instance */
  private TextNumberAppender() {
    super();
  }

  /**
   * append tokens
   * 
   * @param chars
   *          the chars
   * @param textOut
   *          the text output
   * @param status
   *          the status
   */
  private static final void __appendToken(final char[] chars,
      final ITextOutput textOut, final _NumberStatus status) {
    final ETextCase c;

    status.m_needsSpace = true;
    c = ((status.m_case != null) ? status.m_case : ETextCase.IN_SENTENCE);
    c.appendWord(chars, textOut);
    status.m_case = c.nextCase();
  }

  /**
   * append the thousand chars
   * 
   * @param thousand
   *          the thousand to append
   * @param dest
   *          the destination
   * @param status
   *          the number status
   */
  private static final void __appendThousand(final int thousand,
      final ITextOutput dest, final _NumberStatus status) {
    int i;

    if (thousand > 0) {
      i = (thousand / 100);
      if (i > 0) {
        if (status.m_needsSpace) {
          dest.append(' ');
          status.m_needsSpace = false;
        }
        TextNumberAppender.__appendToken(TextNumberAppender.TOKENS_1[i],
            dest, status);
        dest.append(' ');
        TextNumberAppender.__appendToken(TextNumberAppender.C_100, dest,
            status);
        i = (thousand % 100);
      } else {
        i = thousand;
      }

      if (i > 0) {
        if (i < TextNumberAppender.TOKENS_1.length) {
          if (status.m_needsSpace) {
            dest.append(' ');
            status.m_needsSpace = false;
          }
          TextNumberAppender.__appendToken(TextNumberAppender.TOKENS_1[i],
              dest, status);
        } else {
          if (status.m_needsSpace) {
            dest.append(' ');
            status.m_needsSpace = false;
          }
          TextNumberAppender.__appendToken(
              TextNumberAppender.TOKENS_10[i / 10], dest, status);
          i %= 10;
          if (i > 0) {
            dest.append('-');
            status.m_needsSpace = false;
            TextNumberAppender.__appendToken(
                TextNumberAppender.TOKENS_1[i], dest, status);
          }
        }
      }
    }
  }

  /**
   * Append a long to a string builder
   * 
   * @param number
   *          the number
   * @param dest
   *          the destination string builder
   * @param scaleOffset
   *          the scale offset
   * @param status
   *          the number output status
   */
  private static final void __appendLongAsText(final long number,
      final ITextOutput dest, final int scaleOffset,
      final _NumberStatus status) {
    final int[] thousands;
    int start, i, scale, carry, j;
    long n;

    findNumbers: {
      if (number < 0l) {
        TextNumberAppender.__appendToken(TextNumberAppender.MINUS, dest,
            status);
        if (number <= Long.MIN_VALUE) {
          thousands = TextNumberAppender.MIN_LONG_THOUSANDS;
          start = thousands.length;
          break findNumbers;
        }
        n = (-number);
      } else {
        if (number == 0l) {
          TextNumberAppender.__appendToken(TextNumberAppender.C_0, dest,
              status);
          return;
        }
        n = number;
      }

      start = 0;
      thousands = new int[8];// maximum number thousands
      while (n > 0l) {
        thousands[start++] = ((int) (n % 1000l));
        n /= 1000l;
      }
    }

    // in case we have a scale offset, we need to adjust the thousands
    // array we cannot do this before, because this could result in an
    // overflow
    if (scaleOffset > 0) {
      scale = (scaleOffset % 3);
      if (scale > 0) {
        scale = ((scale == 1) ? 10 : 100);
        carry = 0;
        for (i = 0; i < start; i++) {
          j = ((thousands[i] * scale) + carry);
          thousands[i] = (j % 1000);
          carry = (j / 1000);
        }
        if (carry > 0) {
          thousands[start++] = carry;
        }
      }
    }

    // now write the different thousands
    scale = ((scaleOffset / 3) + start);
    for (; (--start) >= 0;) {
      i = thousands[start];
      --scale;
      if (i > 0) {
        TextNumberAppender.__appendThousand(i, dest, status);
        if (scale > 0) {
          if (status.m_needsSpace) {
            dest.append(' ');
            status.m_needsSpace = false;
          }
          TextNumberAppender.__appendToken(
              TextNumberAppender.SCALES[scale], dest, status);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    long num;
    double value, d;
    int power, k;
    String t;
    _NumberStatus status;

    if (v < 0d) {
      if (v <= Double.NEGATIVE_INFINITY) {
        textOut.append(DoubleParser.NEGATIVE_INFINITY);
        return;
      }

      status = new _NumberStatus(textCase);
      if (v == Long.MIN_VALUE) {
        TextNumberAppender.__appendLongAsText(Long.MIN_VALUE, textOut, 0,
            status);
        return;
      }
      TextNumberAppender.__appendToken(TextNumberAppender.MINUS, textOut,
          status);
      value = (-v);
    } else {
      if (v == 0d) {
        textOut.append(TextNumberAppender.C_0);
        return;
      }
      if (v >= Double.POSITIVE_INFINITY) {
        textOut.append(DoubleParser.POSITIVE_INFINITY);
        return;
      }
      if (v != v) {
        textOut.append(DoubleParser.NOT_A_NUMBER);
        return;
      }
      value = v;
      status = new _NumberStatus(textCase);
    }

    if ((value >= Long.MIN_VALUE) && (value <= Long.MAX_VALUE)) {
      // we can directly access the value as a long, but need to take care
      // of the things after the comma

      num = ((long) value);
      if (num > 0l) {// stuff before the comma
        TextNumberAppender.__appendLongAsText(num, textOut, 0, status);
      }

      if (num == value) {
        return;
      }

      // We try to obtain the power by first transforming the fraction into
      // a string. If this works, we can circumvent strange precision
      // errors such as that 3.141592d - 3d = 0.1415920...016 or something,
      // instead of 0.141592. If that does not work, we try to get the
      // fraction part by multiplying with powers of 10.
      t = String.valueOf(value);
      if (t.indexOf('E') >= 0) {
        // ok, no easy fallback
        t = null;

        value -= num;

        // ok, the scale of the double is larger that what fits into a
        // long, so we need to adjust it

        findPower: {
          easy: {
            power = ((int) (-Math.log10(value)));
            for (;;) {
              d = (value * Math.pow(10d, power));
              if ((d != d) || (d > Double.MAX_VALUE)) {
                break easy;
              }
              num = ((long) d);
              if (num == d) {
                break findPower;
              }
              power++;
            }
          }

          power = ((int) (-Math.log10(value)));
          d = value;
          for (k = 1; k <= power; k++) {
            d *= 10d;
          }
          for (;;) {
            num = ((long) d);
            if ((num == d) || (power >= 324)) {
              break findPower;
            }
            power++;
            d *= 10d;
          }

        }

      } else {

        // good, we can use the string!
        power = t.indexOf('.');
        if (power < 0) {
          return;
        }
        t = t.substring(power + 1);
        num = Long.parseLong(t);
        power = t.length();
        t = null;
      }

      if ((power / 3) >= TextNumberAppender.SCALES.length) {
        // This should never happen: The power is too large for our list,
        // fall back to normal v representation
        throw new IllegalArgumentException("The value " + v + //$NON-NLS-1$
            " is out of the range of numbers we can deal with. Odd."); //$NON-NLS-1$
      }

      if (status.m_needsSpace) {
        textOut.append(TextNumberAppender.AND);
      }

      TextNumberAppender.__appendLongAsText(num, textOut, 0, status);
      textOut.append(' ');
      status.m_needsSpace = false;

      switch (power) {
        case 1: {
          TextNumberAppender.__appendToken(TextNumberAppender.C_10,
              textOut, status);
          break;
        }
        case 2: {
          TextNumberAppender.__appendToken(TextNumberAppender.C_100,
              textOut, status);
          break;
        }

        default: {
          if (power <= 0) {
            throw new IllegalStateException();
          }

          switch (power % 3) {
            case 1: {
              TextNumberAppender.__appendToken(TextNumberAppender.C_10,
                  textOut, status);
              textOut.append('-');
              status.m_needsSpace = false;
              break;
            }
            case 2: {
              TextNumberAppender.__appendToken(TextNumberAppender.C_100,
                  textOut, status);
              textOut.append('-');
              status.m_needsSpace = false;
              break;
            }
            default: {
              /** */
            }
          }
          TextNumberAppender.__appendToken(
              TextNumberAppender.SCALES[power / 3], textOut, status);
        }
      }

      textOut.append('t');
      textOut.append('h');
      if (num > 1l) {
        textOut.append('s');
      }

      return;
    }

    // ok, the scale of the double is larger that what fits into a long, so
    // we need to adjust it
    power = ((int) (Math.log10(value)));
    for (;;) {
      d = (value * Math.pow(10d, -power));
      num = ((long) d);
      if (num == d) {
        break;
      }
      power--;
    }

    TextNumberAppender.__appendLongAsText(num, textOut, power, status);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    TextNumberAppender.__appendLongAsText(v, textOut, 0,
        new _NumberStatus(textCase));
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    TextNumberAppender.__appendLongAsText(v, textOut, 0,
        new _NumberStatus(textCase));
  }

  /**
   * read-resolve this object
   * 
   * @return the resolved object
   */
  private final Object readResolve() {
    return TextNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   * 
   * @return the replace object
   */
  private final Object writeReplace() {
    return TextNumberAppender.INSTANCE;
  }
}
