package org.optimizationBenchmarking.utils.text.numbers;

import java.math.BigDecimal;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender which appends the numbers directly to the output, but
 * tries to simplify {@code double} numbers via the heuristic specified by
 * {@link org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender}
 * and finally removes all exponents.
 */
public final class NoExponentNumberAppender extends
    _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the no exponent number appender */
  public static final NoExponentNumberAppender INSTANCE = new NoExponentNumberAppender();

  /** create */
  private NoExponentNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ETextCase appendTo(final double v,
      final ETextCase textCase, final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
    return textCase.nextCase();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    final long l;
    int compareChoices;
    String s;
    _NumberString numberString1, numberString2;
    BigDecimal choice1, choice2;

    if ((v >= Long.MIN_VALUE) && (v <= Long.MAX_VALUE)) {
      l = ((long) v);
      if (l == v) {
        s = Long.toString(l);
        if (Double.parseDouble(s) == v) {// this should always be true
          return s; // but let's better check it out
        }
      }
    }

    s = Double.toString(v);
    if ((v != v) || (v <= Double.NEGATIVE_INFINITY)
        || (v >= Double.POSITIVE_INFINITY)) {
      return s;
    }

    numberString1 = SimpleNumberAppender._simplify(v, s);
    if (!(numberString1.m_hasE)) {
      return numberString1.m_string;
    }

    choice1 = new BigDecimal(v);
    choice2 = new BigDecimal(numberString1.m_string);

    compareChoices = EComparison.compareDoubles(
        Math.abs(choice1.doubleValue() - v),
        Math.abs(choice2.doubleValue() - v));

    if (compareChoices < 0) {
      return choice1.toPlainString();
    }
    if (compareChoices > 0) {
      return choice2.toPlainString();
    }

    numberString1 = new _NumberString(choice1.toPlainString());
    numberString2 = new _NumberString(choice2.toPlainString());

    if (numberString1.compareTo(numberString2) <= 0) {
      return numberString1.m_string;
    }
    return numberString2.m_string;
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return NoExponentNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return NoExponentNumberAppender.INSTANCE;
  }
}
