package org.optimizationBenchmarking.utils.document.spec;

/** An object which can be referenced */
public interface ILabeledObject {

  /**
   * Get the label of this object
   * 
   * @return the label of this object, or {@code null} if it has none
   */
  public abstract ILabel getLabel();
}
