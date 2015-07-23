package org.optimizationBenchmarking.utils.text.numbers;

import java.io.Serializable;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A class that can format numbers.
 */
public abstract class NumberAppender implements Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the number formatter */
  protected NumberAppender() {
    super();
  }

  /**
   * Append a {@code long} number to a text output device
   *
   * @param v
   *          the {@code long} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(v);
    return textCase.nextCase();
  }

  /**
   * Convert a {@code long} number to a string
   *
   * @param v
   *          the {@code long} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final long v, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(v, textCase, m);
    return m.toString();
  }

  /**
   * Append an {@code int} number to a text output device
   *
   * @param v
   *          the {@code int} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    return this.appendTo((long) v, textCase, textOut);
  }

  /**
   * Convert a {@code int} number to a string
   *
   * @param v
   *          the {@code int} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final int v, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(v, textCase, m);
    return m.toString();
  }

  /**
   * Append a {@code double} number to a text output device
   *
   * @param v
   *          the {@code double} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public ETextCase appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    final int types;

    types = NumericalTypes.getTypes(v);
    if ((types & NumericalTypes.IS_INT) != 0) {
      return this.appendTo(((int) v), textCase, textOut);
    }

    if ((types & NumericalTypes.IS_LONG) != 0) {
      return this.appendTo(((long) v), textCase, textOut);
    }

    textOut.append(v);
    return textCase.nextCase();
  }

  /**
   * Append a {link java.lang.Number number} {@code v} to a text output
   * device
   *
   * @param v
   *          the {link java.lang.Number number} value to append
   * @param textCase
   *          the text case
   * @param textOut
   *          the text output to append to
   * @return the next text case
   */
  public final ETextCase appendTo(final Number v,
      final ETextCase textCase, final ITextOutput textOut) {

    if ((v instanceof Byte) || (v instanceof Short)
        || (v instanceof Integer)) {
      return this.appendTo(v.intValue(), textCase, textOut);
    }

    if (v instanceof Long) {
      return this.appendTo(v.longValue(), textCase, textOut);
    }

    if ((v instanceof Float) || (v instanceof Double)) {
      return this.appendTo(v.doubleValue(), textCase, textOut);
    }

    switch (NumericalTypes.getMinType(v)) {
      case NumericalTypes.IS_BYTE:
      case NumericalTypes.IS_SHORT:
      case NumericalTypes.IS_INT: {
        return this.appendTo(v.intValue(), textCase, textOut);
      }
      case NumericalTypes.IS_LONG: {
        return this.appendTo(v.longValue(), textCase, textOut);
      }
      case NumericalTypes.IS_FLOAT:
      case NumericalTypes.IS_DOUBLE: {
        return this.appendTo(v.doubleValue(), textCase, textOut);
      }
      default: {
        throw new IllegalArgumentException("Number " + v + //$NON-NLS-1$
            " cannot be dealt with."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Convert a {@code double} number to a string
   *
   * @param v
   *          the {@code double} value to append
   * @param textCase
   *          the text case
   * @return the string
   */
  public String toString(final double v, final ETextCase textCase) {
    final MemoryTextOutput m;

    m = new MemoryTextOutput();
    this.appendTo(v, textCase, m);
    return m.toString();
  }
}
