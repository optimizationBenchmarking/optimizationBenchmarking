package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IProperty;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertySet;
import org.optimizationBenchmarking.experimentation.data.spec.IPropertyValue;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IProperty}
 * interface.
 */
public abstract class AbstractProperty extends AbstractNamedElement
implements IProperty {

  /** Create the property. */
  protected AbstractProperty() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public abstract IPropertySet getOwner();

  /** {@inheritDoc} */
  @Override
  public EPrimitiveType getPrimitiveType() {
    return null;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<? extends IPropertyValue> getData() {
    return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public IPropertyValue findValue(final Object value) {
    IPropertyValue xval;

    for (final IPropertyValue val : this.getData()) {
      if (EComparison.equals(val.getValue(), value)) {
        return val;
      }
    }

    xval = this.getGeneralized();
    if ((xval != null) && (EComparison.equals(xval.getValue(), value))) {
      return xval;
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IPropertyValue getGeneralized() {
    return null;
  }

}
