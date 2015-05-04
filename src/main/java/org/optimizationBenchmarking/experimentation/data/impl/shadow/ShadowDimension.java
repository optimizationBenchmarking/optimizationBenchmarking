package org.optimizationBenchmarking.experimentation.data.impl.shadow;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A shadow dimension is basically a shadow of another dimension with a
 * different owner and potentially different attributes. If all associated
 * data of this element is the same, it will delegate attribute-based
 * computations to that dimension.
 */
public class ShadowDimension extends
_ShadowNamedElement<IDimensionSet, IDimension> implements IDimension {

  /**
   * create the shadow dimension
   *
   * @param owner
   *          the owning dimension set
   * @param shadow
   *          the dimension to shadow
   */
  ShadowDimension(final IDimensionSet owner, final IDimension shadow) {
    super(owner, shadow);
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getDataType() {
    return this.m_shadowUnpacked.getDataType();
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionType getDimensionType() {
    return this.m_shadowUnpacked.getDimensionType();
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionDirection getDirection() {
    return this.m_shadowUnpacked.getDirection();
  }

  /** {@inheritDoc} */
  @Override
  public final int getIndex() {
    return this.m_shadowUnpacked.getIndex();
  }

  /** {@inheritDoc} */
  @Override
  public final NumberParser<Number> getParser() {
    return this.m_shadowUnpacked.getParser();
  }

}
