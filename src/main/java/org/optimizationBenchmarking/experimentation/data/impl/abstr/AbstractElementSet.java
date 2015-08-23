package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.DataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IDataElement;
import org.optimizationBenchmarking.experimentation.data.spec.IElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IElementSet}
 * interface.
 */
public abstract class AbstractElementSet extends DataElement implements
IElementSet {

  /** create */
  protected AbstractElementSet() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public IDataElement getOwner() {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public ArrayListView<?> getData() {
    return ArraySetView.EMPTY_SET_VIEW;
  }
}
