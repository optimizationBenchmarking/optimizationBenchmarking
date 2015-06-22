package org.optimizationBenchmarking.experimentation.data.spec;

import org.optimizationBenchmarking.utils.document.spec.ISemanticMathComponent;

/**
 * This interface is common to all elements which have a name.
 */
public interface INamedElement extends IDataElement,
    ISemanticMathComponent {

  /**
   * Obtain the name of this object.
   *
   * @return the name of this object.
   */
  public abstract String getName();

  /**
   * Obtain the description of this object.
   *
   * @return the description of this object, or {@code null} if none is
   *         specified
   */
  public abstract String getDescription();
}
