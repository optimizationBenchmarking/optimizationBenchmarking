package org.optimizationBenchmarking.experimentation.data.impl.abstr;

import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.experimentation.data.spec.IDimension;
import org.optimizationBenchmarking.experimentation.data.spec.IDimensionSet;
import org.optimizationBenchmarking.utils.parsers.LooseDoubleParser;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * An abstract implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimension}
 * interface.
 */
public abstract class AbstractDimension extends AbstractNamedElement
    implements IDimension {

  /** the owner */
  IDimensionSet m_owner;

  /**
   * Create the abstract instance. If {@code owner==null}, you must later
   * set it via
   * {@link org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimensionSet#own(AbstractDimension)}
   * .
   *
   * @param owner
   *          the owner
   */
  protected AbstractDimension(final IDimensionSet owner) {
    super();
    this.m_owner = owner;
  }

  /** {@inheritDoc} */
  @Override
  public final IDimensionSet getOwner() {
    return this.m_owner;
  }

  /** {@inheritDoc} */
  @Override
  public EPrimitiveType getDataType() {
    return EPrimitiveType.DOUBLE;
  }

  /** {@inheritDoc} */
  @Override
  public EDimensionType getDimensionType() {
    return EDimensionType.QUALITY_PROBLEM_INDEPENDENT;
  }

  /** {@inheritDoc} */
  @Override
  public EDimensionDirection getDirection() {
    return EDimensionDirection.DECREASING;
  }

  /** {@inheritDoc} */
  @Override
  public int getIndex() {
    return 0;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public NumberParser<Number> getParser() {
    return ((NumberParser) (LooseDoubleParser.INSTANCE));
  }
}
