package org.optimizationBenchmarking.utils.document.spec;

/** A section. */
public interface ISection extends IDocumentPart {

  /**
   * Write the section title
   * 
   * @return the section title
   */
  public abstract IComplexText title();

  /**
   * Write the section body
   * 
   * @return the section body
   */
  public abstract ISectionBody body();

}
