package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IFeatureSetting;
import org.optimizationBenchmarking.experimentation.data.spec.IInstance;
import org.optimizationBenchmarking.experimentation.data.spec.IInstanceSet;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstance}
 * interface.
 */
public abstract class AbstractInstance extends AbstractNamedElement
    implements IInstance {

  /** the owner */
  IInstanceSet m_owner;

  /**
   * Create the abstract instance. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractInstanceSet#own(AbstractInstance)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractInstance(final IInstanceSet owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public IInstanceSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public IFeatureSetting getFeatureSetting() {
    return new AbstractFeatureSetting();
  }

  /** {@inheritDoc} */
  @Override
  public Number getUpperBound(final IDimension dim) {
    return Double.valueOf(Double.POSITIVE_INFINITY);
  }

  /** {@inheritDoc} */
  @Override
  public Number getLowerBound(final IDimension dim) {
    return Double.valueOf(Double.NEGATIVE_INFINITY);
  }

}
