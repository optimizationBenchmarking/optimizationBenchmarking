package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * Appends numbers in text according to some rule.
 */
public final class InTextNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the small numbers */
  static final String[] SMALL_NUMBERS = { "zero", //$NON-NLS-1$
    "one",//$NON-NLS-1$
    "two",//$NON-NLS-1$
    "three",//$NON-NLS-1$
    "four",//$NON-NLS-1$
    "five",//$NON-NLS-1$
    "six",//$NON-NLS-1$
    "seven",//$NON-NLS-1$
    "eight",//$NON-NLS-1$
    "nine",//$NON-NLS-1$
    "ten",//$NON-NLS-1$
    "eleven",//$NON-NLS-1$
    "twelve"//$NON-NLS-1$
  };

  /** the globally shared instance of the in-text number appender */
  public static final InTextNumberAppender INSTANCE = new InTextNumberAppender();

  /** create */
  private InTextNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    if ((v >= 0) && (v < InTextNumberAppender.SMALL_NUMBERS.length)) {
      ETextCase.ensure(textCase).appendWord(
          InTextNumberAppender.SMALL_NUMBERS[(int) v], textOut);
    } else {
      textOut.append(v);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long v, final ETextCase textCase) {
    if ((v < 0) || (v >= InTextNumberAppender.SMALL_NUMBERS.length)
        || ((textCase != null) && (textCase != ETextCase.IN_SENTENCE))) {
      return super.toString(v, textCase);
    }
    return Long.toString(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    if ((v >= 0) && (v < InTextNumberAppender.SMALL_NUMBERS.length)) {
      ETextCase.ensure(textCase).appendWord(
          InTextNumberAppender.SMALL_NUMBERS[v], textOut);
    } else {
      textOut.append(v);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final int v, final ETextCase textCase) {
    if ((v < 0) || (v >= InTextNumberAppender.SMALL_NUMBERS.length)
        || ((textCase != null) && (textCase != ETextCase.IN_SENTENCE))) {
      return super.toString(v, textCase);
    }
    return Integer.toString(v);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    final int l;

    if ((v >= 0) && (v < InTextNumberAppender.SMALL_NUMBERS.length)) {
      l = ((int) v);
      if (l == v) {
        return this.toString(l, textCase);
      }
    }

    return SimpleNumberAppender.INSTANCE.toString(v, textCase);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    final int l;

    if ((v >= 0) && (v < InTextNumberAppender.SMALL_NUMBERS.length)) {
      l = ((int) v);
      if (l == v) {
        this.appendTo(l, textCase, textOut);
        return;
      }
    }

    SimpleNumberAppender.INSTANCE.appendTo(v, textCase, textOut);
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return InTextNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return InTextNumberAppender.INSTANCE;
  }
}