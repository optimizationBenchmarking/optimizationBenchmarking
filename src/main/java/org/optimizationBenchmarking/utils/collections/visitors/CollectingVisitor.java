package org.optimizationBenchmarking.utils.collections.visitors;

import java.util.ArrayList;

/**
 * A visitor which collects everything he sees.
 *
 * @param <T>
 *          the type
 */
public class CollectingVisitor<T> extends ArrayList<T> implements
    IVisitor<T> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  public CollectingVisitor() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean visit(final T object) {
    return this.add(object);
  }

}
