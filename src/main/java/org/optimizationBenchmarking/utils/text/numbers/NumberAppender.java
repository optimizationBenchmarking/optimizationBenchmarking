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
   */
  public void appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(v);
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
   */
  public void appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    this.appendTo((long) v, textCase, textOut);
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
   */
  public void appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    final int types;

    types = NumericalTypes.getTypes(v);
    if ((types & NumericalTypes.IS_INT) != 0) {
      this.appendTo(((int) v), textCase, textOut);
    } else {
      if ((types & NumericalTypes.IS_LONG) != 0) {
        this.appendTo(((long) v), textCase, textOut);
      } else {
        textOut.append(v);
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
