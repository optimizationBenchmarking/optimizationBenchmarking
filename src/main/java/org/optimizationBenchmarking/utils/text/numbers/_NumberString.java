package org.optimizationBenchmarking.utils.text.numbers;

/**
 * An internal class to compare numbers represented as strings and
 * comparing them according to some visual features.
 */
final class _NumberString implements Comparable<_NumberString> {

  /** the string */
  final String m_string;
  /** do we have an {@code E} ? */
  final boolean m_hasE;
  /** the amount of different numbers after the dot */
  private final int m_numbersAfterDot;
  /** the length */
  private final int m_length;

  /**
   * create the number string
   *
   * @param string
   *          the string
   */
  _NumberString(final String string) {
    super();
    this.m_string = string;

    final boolean[] numbers;
    final int length;
    boolean hasE, hasDot;
    int numbersAfterDot, i, ch;

    hasE = hasDot = false;
    numbers = new boolean[10];
    numbersAfterDot = 0;

    this.m_length = length = string.length();
    for (i = 0; i < length; i++) {
      switch (ch = string.charAt(i)) {
        case '.': {
          hasDot = true;
          break;
        }
        case 'e':
        case 'E': {
          hasE = true;
          break;
        }
        default: {
          if (hasDot && (!(hasE))) {
            ch -= '0';
            if (!(numbers[ch])) {
              numbers[ch] = true;
              numbersAfterDot++;
            }
          }
        }
      }
    }

    this.m_hasE = hasE;
    this.m_numbersAfterDot = numbersAfterDot;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _NumberString o) {
    int r;

    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }

    r = Integer.compare(this.m_length, o.m_length);
    if (r != 0) {
      return r;
    }

    if (this.m_hasE) {
      if (!(o.m_hasE)) {
        return 1;
      }
    } else {
      if (o.m_hasE) {
        return (-1);
      }
    }

    return Integer.compare(this.m_numbersAfterDot, o.m_numbersAfterDot);
  }
}