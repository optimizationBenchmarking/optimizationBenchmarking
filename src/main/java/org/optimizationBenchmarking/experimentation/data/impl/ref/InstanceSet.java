package org.optimizationBenchmarking.experimentation.data.impl.ref;

import java.util.concurrent.atomic.AtomicInteger;

import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;

/** A set of instances. */
public final class InstanceSet extends _IDObjectSet<Instance> implements
    IInstanceSet {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** an atomic id counter */
  private static final AtomicInteger ID_COUNTER = new AtomicInteger();

  /**
   * Create
   *
   * @param data
   *          the data
   */
  InstanceSet(final Instance[] data) {
    super(data, false, true, true);
    this.m_id = InstanceSet.ID_COUNTER.getAndIncrement();
  }

  /**
   * Find the given instance.
   *
   * @param name
   *          the name
   * @return the instance, or {@code null} if it could not be found
   */
  @Override
  public final Instance find(final String name) {
    return super.find(name);
  }

  /** {@inheritDoc} */
  @Override
  public final ExperimentSet getOwner() {
    return ((ExperimentSet) (this.m_owner));
  }
}
