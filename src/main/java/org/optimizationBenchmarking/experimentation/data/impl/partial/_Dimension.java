package org.optimizationBenchmarking.experimentation.data.impl.partial;

import org.optimizationBenchmarking.experimentation.data.impl.abstr.AbstractDimension;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionDirection;
import org.optimizationBenchmarking.experimentation.data.spec.EDimensionType;
import org.optimizationBenchmarking.utils.parsers.NumberParser;
import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * An internal, modifiable implementation of the
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IDimension}
 * interface.
 */
final class _Dimension extends AbstractDimension {

  /** the name */
  String m_name;
  /** the description */
  String m_description;
  /** the data type */
  EPrimitiveType m_dataType;
  /** the dimension type */
  EDimensionType m_dimensionType;
  /** the dimension direction */
  EDimensionDirection m_dimensionDirection;
  /** the parser */
  NumberParser<Number> m_parser;
  /** the index */
  private final int m_index;

  /**
   * Create the abstract instance.
   *
   * @param owner
   *          the owner
   * @param index
   *          the index
   */
  public _Dimension(final _Dimensions owner, final int index) {
    super(owner);
    AbstractDimension.validateIndex(index);
    this.m_index = index;
  }

  /** {@inheritDoc} */
  @Override
  public final EPrimitiveType getDataType() {
    return this.m_dataType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionType getDimensionType() {
    return this.m_dimensionType;
  }

  /** {@inheritDoc} */
  @Override
  public final EDimensionDirection getDirection() {
    return this.m_dimensionDirection;
  }

  /** {@inheritDoc} */
  @Override
  public final int getIndex() {
    return this.m_index;
  }

  /** {@inheritDoc} */
  @Override
  public final NumberParser<Number> getParser() {
    return this.m_parser;
  }

  /** {@inheritDoc} */
  @Override
  public final String getName() {
    return this.m_name;
  }

  /** {@inheritDoc} */
  @Override
  public final String getDescription() {
    return this.m_description;
  }
}
