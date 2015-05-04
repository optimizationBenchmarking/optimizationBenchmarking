package org.optimizationBenchmarking.utils.document.spec;

/**
 * A document element for writing text, which supports the functionality
 * needed to write text in a Maths context.
 */
public interface IMathName extends IText {

  /**
   * Write some subscript text.
   *
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IPlainText}
   *         whose text will be subscript.
   */
  public abstract IText subscript();

  /**
   * Write some subscript text.
   *
   * @return an instance of
   *         {@link org.optimizationBenchmarking.utils.document.spec.IPlainText}
   *         whose text will be subscript.
   */
  public abstract IText superscript();

}
