package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The basic class for text output, a shared ancestor of the
 * {@link org.optimizationBenchmarking.utils.document.spec.IMath
 * mathematical} and
 * {@link org.optimizationBenchmarking.utils.document.spec.IText textual}
 * text outputs.
 */
public interface IPlainText extends IDocumentPart, ITextOutput {

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
