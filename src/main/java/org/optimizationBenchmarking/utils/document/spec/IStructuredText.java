package org.optimizationBenchmarking.utils.document.spec;

/**
 * A structured text can contain lists.
 */
public interface IStructuredText extends IComplexText {

  /**
   * A list whose elements are indexed with numbers.
   *
   * @return A list whose elements are indexed with numbers.
   */
  public abstract IList enumeration();

  /**
   * A list whose elements are marked with bullets
   *
   * @return whose elements are marked with bullets
   */
  public abstract IList itemization();

}
