package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A number appender designed for XMLFileType data.
 */
public final class XMLNumberAppender extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the simple number appender */
  public static final XMLNumberAppender INSTANCE = new XMLNumberAppender();

  /** create */
  private XMLNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final double v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(this.toString(v, textCase));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final double v, final ETextCase textCase) {
    if (v != v) {
      return "NaN"; //$NON-NLS-1$
    }
    if (v <= Double.NEGATIVE_INFINITY) {
      return "-INF"; //$NON-NLS-1$
    }
    if (v >= Double.POSITIVE_INFINITY) {
      return "+INF"; //$NON-NLS-1$
    }

    return SimpleNumberAppender.INSTANCE.toString(v, textCase);
  }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return XMLNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return XMLNumberAppender.INSTANCE;
  }
}
