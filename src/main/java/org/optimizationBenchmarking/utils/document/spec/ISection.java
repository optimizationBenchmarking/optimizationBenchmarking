package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.graphics.style.IStyleProvider;

/** A section. */
public interface ISection extends IDocumentElement, IStyleProvider,
ILabeledObject {

  /**
   * Create and write the section title
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
