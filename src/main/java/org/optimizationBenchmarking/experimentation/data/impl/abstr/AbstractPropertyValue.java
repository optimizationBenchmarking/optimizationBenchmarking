package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue}
 * interface.
 */
public abstract class AbstractPropertyValue extends AbstractNamedElement
implements IPropertyValue {

  /** Create the property. */
  protected AbstractPropertyValue() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public abstract IProperty getOwner();

  /** {@inheritDoc} */
  @Override
  public Object getValue() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isGeneralized() {
    return false;
  }
}
