package org.optimizationBenchmarking.utils.text.numbers;

/**
 * A number appender which, well, directly appends the numbers to the
 * output.
 */
public final class DirectNumberAppender extends _PlainIntNumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the direct number appender */
  public static final DirectNumberAppender INSTANCE = new DirectNumberAppender();

  /** create */
  private DirectNumberAppender() {
    super();
  }

  // /** {@inheritDoc} */
  // @Override
  // public final void appendTo(final double v, final ETextCase textCase,
  // final ITextOutput textOut) {
  // textOut.append(v);
  // }
  //
  // /** {@inheritDoc} */
  // @Override
  // public final String toString(final double v, final ETextCase textCase)
  // {
  // return Double.toString(v);
  // }

  /**
   * read-resolve this object
   *
   * @return the resolved object
   */
  private final Object readResolve() {
    return DirectNumberAppender.INSTANCE;
  }

  /**
   * write-replace this object
   *
   * @return the replace object
   */
  private final Object writeReplace() {
    return DirectNumberAppender.INSTANCE;
  }
}
