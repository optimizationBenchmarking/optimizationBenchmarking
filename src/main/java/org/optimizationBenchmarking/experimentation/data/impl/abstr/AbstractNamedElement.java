package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.INamedElement}
 * interface.
 */
public class AbstractNamedElement extends DataElement implements
    INamedElement {

  /** Create the abstract named element. */
  protected AbstractNamedElement() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public String getName() {
    return this.getClass().getSimpleName();
  }

  /** {@inheritDoc} */
  @Override
  public String getDescription() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IDataElement getOwner() {
    return null;
  }

}
