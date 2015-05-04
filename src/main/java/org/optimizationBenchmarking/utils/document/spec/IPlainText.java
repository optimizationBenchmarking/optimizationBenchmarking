package org.optimizationBenchmarking.utils.document.spec;

/**
 * A document element for writing text without any formatting capabilities
 */
public interface IPlainText extends IText {

  /**
   * Write some text in quotes. The underlying implementation will select
   * the right quotation marks and may deal with nested quotations. It will
   * return an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IPlainText} to
   * which output can be written. This output will appear surrounded by the
   * correct quotation characters in the underlying stream.
   *
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IPlainText}
   *         whose text will appear in quotation marks in the underlying
   *         stream
   */
  public IPlainText inQuotes();

  /**
   * Write some text in braces. The underlying implementation will select
   * the right brace marks and may deal with nested braces. It will return
   * an instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IPlainText} to
   * which output can be written. This output will appear surrounded by the
   * correct brace characters in the underlying stream.
   *
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IPlainText}
   *         whose text will appear in braces in the underlying stream
   */
  public IPlainText inBraces();

}
