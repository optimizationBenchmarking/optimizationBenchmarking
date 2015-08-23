package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.INamedElement;
import org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.INamedElementSet}
 * interface.
 */
public abstract class AbstractNamedElementSet extends AbstractElementSet
    implements INamedElementSet {

  /** create */
  protected AbstractNamedElementSet() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ArrayListView<? extends INamedElement> getData() {
    return ((ArraySetView) (ArraySetView.EMPTY_SET_VIEW));
  }

  /** {@inheritDoc} */
  @Override
  public INamedElement find(final String name) {
    if (name != null) {
      for (final INamedElement element : this.getData()) {
        if (name.equalsIgnoreCase(element.getName())) {
          return element;
        }
      }
    }
    return null;
  }

}
